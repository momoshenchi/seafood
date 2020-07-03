package util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.impl.C3P0PooledConnection;
public class DBUtil {
    private static   ComboPooledDataSource cp=null;
    private static final String jdbcUrl="jdbc:mysql://localhost:3306/seafood?serverTimezone=UTC";
    private static final String dbUser="root";
    private static final String dbPwd="junliang2244";
//    private static final String driverClass="com.mysql.cj.jdbc.Driver";
    static{
        cp=new ComboPooledDataSource();
        cp.setMaxPoolSize(15);
        cp.setUser(dbUser);
        cp.setPassword(dbPwd);
        cp.setJdbcUrl(jdbcUrl);

    }
    public static Connection getConnection() throws java.sql.SQLException{
        return cp.getConnection();
    }
    public static void main(String[] args)
    {
        try
        {
            System.out.println(getConnection());
        } catch (SQLException e)
        {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}
