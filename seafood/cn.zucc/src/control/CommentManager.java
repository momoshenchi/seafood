package control;

import model.customer.BeanUser;
import model.food.BeanComment;
import model.food.BeanCommodity;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CommentManager
{
    public  void addComment(BeanCommodity bc, String comments, String levels, String picture)
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "insert into customer_comment(commodityid,userid,comments,commentdate,levels,picture) " +
                    "values(?,?,?,?,?,?) ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bc.getCommodityid());
            pst.setInt(2,userid);
            pst.setString(3,comments);
            pst.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
            pst.setString(5,levels);
            pst.setString(6,picture);
            pst.executeUpdate();
            pst.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    public  void modifyComment(BeanComment bc)
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "update  customer_comment set comments = ? ,commentdate = ? ,levels = ? ,picture = ? where userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,bc.getComments());
            pst.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
            pst.setString(3,bc.getLevels());
            pst.setString(4,bc.getPicture());
            pst.setInt(5,userid);
            pst.executeUpdate();
            pst.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    public  void delComment(BeanComment bc)
    {

    }
}
