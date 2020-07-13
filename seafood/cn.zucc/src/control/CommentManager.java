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
    public List<BeanDetailOrder> loadTheDetail(BeanOrder bo) throws BusinessException
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        List<BeanDetailOrder> result = new ArrayList<>();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select orderid , o.commodityid, userid, o.number , o.price, o.vipprice, " +
                    "discountid,discount, saleid, saleprice , orderstatus, commodityname from order_detail o ," +
                    "commodity c where c.commodityid = o.commodityid and " +
                    " userid = ? and orderid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setInt(2, bo.getOrderid());
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanDetailOrder bd = new BeanDetailOrder();
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
                bd.setCommodityname(rs.getString(12));
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
        return result;
    }

    public void addComment(BeanDetailOrder bo, String comments, String levels, String picture) throws BusinessException
    {
        if (comments == null || "".equals(comments))
        {
            throw new BusinessException("please input comment");
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
            pst.setInt(2, userid);
            pst.setString(3, comments);
            pst.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pst.setString(5, levels);
            pst.setString(6, picture);
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

    public void modifyComment(BeanComment bc) throws BusinessException
    {
        if (bc.getComments() == null || "".equals(bc.getComments()))
        {
            throw new BusinessException("please input comment");
        }
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "update  customer_comment set comments = ? ,commentdate = ? ,levels = ? ,picture = ? where userid = ? " +
                    "and commodityid = ?  and commentdate = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, bc.getComments());
            pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pst.setString(3, bc.getLevels());
            pst.setString(4, bc.getPicture());
            pst.setInt(5, userid);
            pst.setInt(6,bc.getCommodityid());
            pst.setTimestamp(7,new Timestamp(bc.getCommentdate().getTime()));
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

    public void delComment(BeanComment bc)
    {
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "delete from customer_comment where  commodityid = ? and userid = ?  and commentdate = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bc.getCommodityid());
            pst.setInt(2,bc.getUserid());
            pst.setTimestamp(3,new Timestamp(bc.getCommentdate().getTime()));
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

    public List<BeanComment> loadTheComment(BeanDetailOrder bdo)
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        List<BeanComment> result = new ArrayList<>();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from  customer_comment  where userid = ? and commodityid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setInt(2, bdo.getCommodityid());
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanComment bc = new BeanComment();
                bc.setCommodityid(rs.getInt(1));
                bc.setUserid(rs.getInt(2));
                bc.setComments(rs.getString(3));
                bc.setCommentdate(rs.getTimestamp(4));
                bc.setLevels(rs.getString(5));
                bc.setPicture(rs.getString(6));
                result.add(bc);
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
        return result;
    }
}
