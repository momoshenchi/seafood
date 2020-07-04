package control;

import model.customer.BeanUser;
import util.BaseException;
import util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class UserManager {
    public BeanUser reg(String username,String sex,String pwd,String pwd2,String city,String mail,String phonenum) throws BaseException {

        if(username==null||"".equals(username))
        {
            throw new BaseException("please input username ");
        }
        if(pwd==null||pwd2==null||"".equals(pwd)||"".equals(pwd2)) {
            throw new BaseException("password is null");
        }
        if(!pwd.equals(pwd2))
        {
            throw new BaseException("password is wrong");
        }
        if(sex==null||"".equals(sex))
        {
            throw new BaseException("please input sex ");
        }
        java.sql.Connection con=null;
        java.sql.PreparedStatement pst=null;
        try
        {
            con= DBUtil.getConnection();
            String sql="select * from users where username = ? ";
            pst=con.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                throw new BaseException("userid is already exist");
            }
            pst.close();
            sql="select max(userid) from users  ";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            int userid=1;
            if(rs.next())
            {
                userid=rs.getInt(1)+1;
            }
            rs.close();
            pst.close();
            sql="insert into users(userid,username,sex,pwd,phonenum,mail,city,register_time) values(?,?, ? ,? ,?," +
                    " ?, ?, ?) ";
            pst=con.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setString(2, username);
            pst.setString(3, sex);
            pst.setString(4, pwd);
            pst.setString(5, phonenum);
            pst.setString(6, mail);
            pst.setString(7, city);
            long register_time=System.currentTimeMillis();
            pst.setTimestamp(8, new Timestamp(register_time));
            pst.executeUpdate();
            BeanUser u=new BeanUser();
            u.setUserid(userid);
            u.setPhonenumber(phonenum);
            u.setCity(city);
            u.setMail(mail);
            u.setSex(sex);
            u.setRegister_time(new Date(register_time));
            u.setUsername(username);
            u.setPwd(pwd);
            return u;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(pst!=null)
            {
                try {
                    pst.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(con!=null)
            {
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }



    public BeanUser login(String username, String pwd) throws BaseException {

        if(username==null||"".equals(username))
        {
            throw new BaseException("username is null");
        }
        java.sql.Connection con=null;
        java.sql.PreparedStatement pst=null;
        try
        {
            con=DBUtil.getConnection();
            String sql="select pwd from users where username = ? ";
            pst=con.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs=pst.executeQuery();
            if(!rs.next())
            {
                throw new BaseException("userid is not exist");
            }
            String p=rs.getString(1);
            if(!p.equals(pwd))
            {
                throw new BaseException("pwd is wrong");
            }
            rs.close();
            pst.close();
            sql=" select * from users where username = ? ";
            pst=con.prepareStatement(sql);
            pst.setString(1, username);
            rs=pst.executeQuery();
            if(rs.next())
            {
                BeanUser u=new BeanUser();
                u.setUserid(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setSex(rs.getString(3));
                u.setPwd(rs.getString(4));
                u.setPhonenumber(rs.getString(5));
                u.setMail(rs.getString(6));
                u.setCity(rs.getString(7));
                u.setRegister_time(rs.getTimestamp(8));
                return u;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(pst!=null)
            {
                try {
                    pst.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(con!=null)
            {
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }



    public void changePwd(BeanUser user, String oldPwd, String newPwd,
                          String newPwd2) throws BaseException {
        if(user==null)
        {
            throw new BaseException("user is null");
        }
        if(newPwd==null||newPwd2==null||"".equals(newPwd)||"".equals(newPwd2)||!newPwd.equals(newPwd2)) {
            throw new BaseException("password is null");
        }
        String pwd=user.getPwd();
        int userid=user.getUserid();
        if(!oldPwd.equals(pwd))
        {
            throw new BaseException("password is wrong");
        }
        java.sql.Connection con=null;
        java.sql.PreparedStatement pst=null;

        try
        {
            con=DBUtil.getConnection();
            String sql="select pwd from users where userid = ? ";
            pst=con.prepareStatement(sql);
            pst.setInt(1, userid);
            ResultSet rs=pst.executeQuery();
            if(!rs.next())
            {
                throw new BaseException("userid is not exist");
            }
            String p=rs.getString(1);
            if(!p.equals(oldPwd))
            {
                throw new BaseException("pwd is wrong");
            }
            rs.close();
            pst.close();
            sql="update  users set pwd = ? where userid = ? ";
            pst=con.prepareStatement(sql);
            pst.setString(1, newPwd);
            pst.setInt(2,userid);
            pst.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(pst!=null)
            {
                try {
                    pst.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(con!=null)
            {
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
    public static void main(String[] args) {
        UserManager em=new UserManager();

//		BeanUser u=new BeanUser();
//		u.setUser_id("1233");
//		u.setUser_pwd("dggg");
//		try {
//			em.changePwd(u,"dggg","123","123");
//		} catch (BaseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        try {
            BeanUser.currentLoginUser=em.reg("1","男","1","1",
                    "hangzhou","","");
            System.out.println(BeanUser.currentLoginUser.getUserid());
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



}
