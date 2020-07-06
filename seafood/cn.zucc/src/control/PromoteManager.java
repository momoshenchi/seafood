package control;

import model.promote.BeanCoupon;
import model.promote.BeanDiscount;
import model.promote.BeanSale;
import util.BusinessException;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PromoteManager
{
    public List<BeanDiscount> loadAllDiscountRule()
    {
        List<BeanDiscount>l=new ArrayList<>();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from discount_rule ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanDiscount bd = new BeanDiscount();
                bd.setDiscountid(rs.getInt(1));
                bd.setDetail(rs.getString(2));
                bd.setMin_number(rs.getInt(3));
                bd.setDiscount(rs.getDouble(4));
                bd.setStart_date(rs.getTimestamp(5));
                bd.setEnd_date(rs.getTimestamp(6));
                l.add(bd);
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
        return l;
    }
    public void delDiscount(BeanDiscount bd)
    {
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            String sql = "delete from discount_rule where  discountid = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDouble(1, bd.getDiscount());
            pst.executeUpdate();
            pst.close();
            sql = "delete from commodity_discount where  discountid = ?";
            pst = con.prepareStatement(sql);
            pst.setDouble(1, bd.getDiscount());
            pst.executeUpdate();
            pst.close();
            con.commit();
        }
        catch (SQLException e)
        {
            try
            {
                con.rollback();
            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    public void delCoupon(BeanCoupon bc)
    {
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "delete from coupon where  couponid = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bc.getCouponid());
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
    public void addSale(int commodityid, double saleprice, int maxnumber, String startime, String endtime) throws BusinessException
    {
        if (commodityid == 0)
        {
            throw new BusinessException("please input commodityid");
        }
        if (saleprice == 0)
        {
            throw new BusinessException("please input saleprice");
        }
        if (startime == null || "".equals(startime))
        {
            throw new BusinessException("please input startime");
        }
        if (endtime == null || "".equals(endtime))
        {
            throw new BusinessException("please input endtime");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //formatter is unknown
        java.sql.Connection con = null;
        Date start_time = null, finish = null;
        try
        {
            start_time = sdf.parse(startime);
            finish = sdf.parse(endtime);
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
        }
        if (start_time.after(finish))
        {
            throw new BusinessException("start time is not allow to after finish ");
        }
        try
        {
            con = DBUtil.getConnection();
            String sql = "insert  into sale(commodityid,saleprice,maxnumber,start_date,end_date) values " +
                    "(?,?,?,?,?) ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, commodityid);
            pst.setDouble(2, saleprice);
            pst.setInt(3, maxnumber);
            pst.setTimestamp(4, new java.sql.Timestamp(start_time.getTime()));
            pst.setTimestamp(5, new java.sql.Timestamp(finish.getTime()));
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void delSale(BeanSale bs)
    {
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "delete from sale where  saleid = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bs.getSaleid());
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
    public List<BeanSale> loadAllSale()
    {
        List<BeanSale>l=new ArrayList<>();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from sale ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanSale bs = new BeanSale();
                bs.setSaleid(rs.getInt(1));
                bs.setCommodityid(rs.getInt(2));
                bs.setSaleprice(rs.getDouble(3));
                bs.setMaxnumber(rs.getInt(4));
                bs.setStart_date(rs.getTimestamp(5));
                bs.setEnd_date(rs.getTimestamp(6));
                l.add(bs);
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
        return l;
    }
    public  List<BeanCoupon>loadAllCoupon()
    {
        List<BeanCoupon>l=new ArrayList<>();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from coupon ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanCoupon bc = new BeanCoupon();
                bc.setCouponid(rs.getInt(1));
                bc.setDetail(rs.getString(2));
                bc.setStart_price(rs.getDouble(3));
                bc.setSub_price(rs.getDouble(4));
                bc.setStart_date(rs.getTimestamp(5));
                bc.setEnd_date(rs.getTimestamp(6));
                l.add(bc);
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
        return l;
    }
    public void addCoupon(String detail, double start, double sub, String startime, String endtime) throws BusinessException
    {
        if (start == 0)
        {
            throw new BusinessException("please input start_price");
        }
        if (sub == 0)
        {
            throw new BusinessException("please input sub_price");
        }
        if (startime == null || "".equals(startime))
        {
            throw new BusinessException("please input startime");
        }
        if (endtime == null || "".equals(endtime))
        {
            throw new BusinessException("please input endtime");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //formatter is unknown
        java.sql.Connection con = null;
        Date start_time = null, finish = null;
        try
        {
            start_time = sdf.parse(startime);
            finish = sdf.parse(endtime);
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
        }
        if (start_time.after(finish))
        {
            throw new BusinessException("start time is not allow to after finish ");
        }
        try
        {
            con = DBUtil.getConnection();
            String sql = "insert  into coupon(deteil,start_price,sub_price,start_date,end_date) values " +
                    "(?,?,?,?,?) ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, detail);
            pst.setDouble(2, start);
            pst.setDouble(3, sub);
            pst.setTimestamp(4, new java.sql.Timestamp(start_time.getTime()));
            pst.setTimestamp(5, new java.sql.Timestamp(finish.getTime()));
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void addDiscountRule(String detail, int min_number, double discount, String startime, String endtime) throws BusinessException
    {
        if (min_number == 0)
        {
            throw new BusinessException("please input min_number");
        }
        if (discount == 0)
        {
            throw new BusinessException("please input discount");
        }
        if (startime == null || "".equals(startime))
        {
            throw new BusinessException("please input startime");
        }
        if (endtime == null || "".equals(endtime))
        {
            throw new BusinessException("please input endtime");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //formatter is unknown
        java.sql.Connection con = null;
        Date start_time = null, finish = null;
        try
        {
            start_time = sdf.parse(startime);
            finish = sdf.parse(endtime);
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
        }
        if (start_time.after(finish))
        {
            throw new BusinessException("start time is not allow to after finish ");
        }
        try
        {
            con = DBUtil.getConnection();
            String sql = "insert  into discount_rule(deteil,min_number,discount,start_date,end_date) values " +
                    "(?,?,?,?,?) ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, detail);
            pst.setInt(2, min_number);
            pst.setDouble(3, discount);
            pst.setTimestamp(4, new java.sql.Timestamp(start_time.getTime()));
            pst.setTimestamp(5, new java.sql.Timestamp(finish.getTime()));
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


}
