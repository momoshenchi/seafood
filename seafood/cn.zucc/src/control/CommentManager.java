package control;

import model.customer.BeanDetailOrder;
import model.customer.BeanOrder;
import model.customer.BeanUser;
import model.food.BeanComment;
import util.BusinessException;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentManager
{

    //查看指定订单的细节
    public  List<BeanDetailOrder> loadTheDetail(BeanOrder bo) throws BusinessException
    {

        int userid = BeanUser.currentLoginUser.getUserid();
        List<BeanDetailOrder>result=new ArrayList<>();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select * from order_detail where userid = ? and orderid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1,userid);
            pst.setInt(2,bo.getOrderid());
            ResultSet rs=pst.executeQuery();
            while(rs.next())
            {
                BeanDetailOrder bd=new BeanDetailOrder();
                bd.setOrderid(rs.getInt(1));
                bd.setCommodityid(rs.getInt(2));
                bd.setUserid(rs.getInt(3));
                bd.setNumber(rs.getInt(4));
                bd.setPrice(rs.getDouble(5));
                bd.setVipprice(rs.getDouble(6));
                bd.setDiscountid(rs.getInt(7));
                bd.setDiscount(rs.getDouble(8));
                bd.setSaleid(rs.getInt(9));
                bd.setSaleprice(rs.getDouble(10));
                bd.setOrderstatus(rs.getString(11));
                result.add(bd);
            }
            rs.close();
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
        return  result;
    }
    public  void addComment(BeanDetailOrder bo , String comments, String levels, String picture) throws BusinessException
    {
        if(comments==null||"".equals(comments))
        {
            throw  new BusinessException("please input comment");
        }
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "insert into customer_comment(commodityid,userid,comments,commentdate,levels,picture) " +
                    "values(?,?,?,?,?,?) ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bo.getCommodityid());
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
