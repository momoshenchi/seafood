package control;

import model.customer.BeanCart;
import model.customer.BeanDetailOrder;
import model.customer.BeanOrder;
import model.customer.BeanUser;
import util.BusinessException;
import util.DBUtil;
import control.UserManager;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderManager
{
    public List<BeanDetailOrder> loadAllUserDetailOrders()
    {
        Connection con = null;
        List<BeanDetailOrder> l = new ArrayList<>();
        int userid = BeanUser.currentLoginUser.getUserid();
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from order_detail where userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanDetailOrder bu = new BeanDetailOrder();
                bu.setOrderid(rs.getInt(1));
                bu.setCommodityid(rs.getInt(2));
                bu.setUserid(rs.getInt(3));
                bu.setNumber(rs.getInt(4));
                bu.setPrice(rs.getDouble(5));
                bu.setVipprice(rs.getDouble(6));
                bu.setDiscountid(rs.getInt(7));
                bu.setDiscount(rs.getDouble(8));
                bu.setSaleid(rs.getInt(9));
                bu.setSaleprice(rs.getDouble(10));
                bu.setOrderstatus(rs.getString(11));
                l.add(bu);
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

    public List<BeanOrder> loadUserOrders()
    {
        Connection con = null;
        List<BeanOrder> l = new ArrayList<>();
        int userid = BeanUser.currentLoginUser.getUserid();
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from orders where userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanOrder bu = new BeanOrder();
                bu.setOrderid(rs.getInt(1));
                bu.setUserid(rs.getInt(2));
                bu.setOri_amount(rs.getDouble(3));
                bu.setSet_amount(rs.getDouble(4));
                bu.setCouponid(rs.getInt(5));
                bu.setOrder_time(rs.getTimestamp(6));
                bu.setAddressid(rs.getInt(7));
                bu.setStatus(rs.getString(8));
                l.add(bu);
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

    //在购物车点击购买按钮
    public List<BeanDetailOrder> addDetailOrder() throws BusinessException
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        List<BeanDetailOrder> l = new ArrayList<>();
        List<BeanCart> beancart = new UserManager().loadUserCart();
        List<BeanDetailOrder> result = new ArrayList<>();
        if (beancart == null)
        {
            throw new BusinessException("There are no commodities");
        }
        for (int i = 0; i < beancart.size(); ++i)
        {
            BeanCart tem = beancart.get(i);
            BeanDetailOrder bc = new BeanDetailOrder();
            bc.setCommodityid(tem.getCommodityid());
            bc.setNumber(tem.getNumber());
            bc.setPrice(tem.getPrice());
            bc.setVipprice(tem.getVipprice());
            bc.setUserid(userid);
            bc.setCommodityname(tem.getCommodityname());
            l.add(bc);
        }
        searchAll(l);
        try
        {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            int orderid = 1;
            String sql = "select max(orderid) from order_detail";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                orderid = rs.getInt(1) + 1;
            }
            rs.close();
            pst.close();

            double sum = 0;
            double set_amount = 0;
            for (int i = 0; i < l.size(); ++i)
            {
                BeanDetailOrder tem = new BeanDetailOrder();
                BeanDetailOrder bo = l.get(i);
                sql = "insert  into order_detail(orderid,commodityid,userid,number,price,vipprice,discountid,discount," +
                        "saleid,saleprice,orderstatus) values(?,?,?,?,?,?,?,?,?,?,?)";
                pst = con.prepareStatement(sql);
                pst.setInt(1, orderid);
                pst.setInt(2, bo.getCommodityid());
                pst.setInt(3, bo.getUserid());
                int number = bo.getNumber();
                double price = bo.getPrice();
                sum += number * price;
                pst.setInt(4, number);
                pst.setDouble(5, price);
                double discount = bo.getDiscount();
                double vip = bo.getVipprice();
                double saleprice = bo.getSaleprice();
                double m1 = 0x7ffffff, m2 = 0x7ffffff, m3 = 0x7ffffff;
                if (discount != 0.0)
                {
                    m1 = number * price * discount;
                }
                if (saleprice != 0.0)
                {
                    m2 = number * saleprice;
                }
                if (vip != 0.0)
                {
                    m3 = number * vip;
                }
                set_amount += Math.min(Math.min(m1, m2), m3);
                set_amount = Math.min(sum, set_amount);
                pst.setDouble(6, vip);
                pst.setInt(7, bo.getDiscountid());
                pst.setDouble(8, discount);
                pst.setInt(9, bo.getSaleid());
                pst.setDouble(10, saleprice);
                pst.setString(11, "待支付");
                pst.executeUpdate();
                pst.close();
                tem.setCommodityname(bo.getCommodityname());
                tem.setCommodityid(bo.getCommodityid());
                tem.setDiscountid(bo.getDiscountid());
                tem.setDiscount(discount);
                tem.setSaleid(bo.getSaleid());
                tem.setOrderstatus("待支付");
                tem.setSaleprice(saleprice);
                tem.setOrderid(orderid);
                tem.setVipprice(vip);
                tem.setUserid(userid);
                tem.setPrice(price);
                tem.setNumber(number);
                result.add(tem);
            }

            sql = "insert  into orders(orderid,userid,ori_amount,set_amount,couponid,order_time,addressid,orderstatus) " +
                    "values(?,?,?,?,0,null,0,?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, orderid);
            pst.setInt(2, userid);
            pst.setDouble(3, sum);
            pst.setDouble(4, set_amount);
            pst.setString(5, "待支付");
            pst.executeUpdate();
            pst.close();
            sql="delete from cart ";
            pst = con.prepareStatement(sql);
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
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
//支付指定订单
    //购物车与历史记录都可
    public void pay(BeanOrder bo, int couponid, String ordertime, int addressid) throws BusinessException
    {
        if(ordertime==null||"".equals(ordertime))
        {
            throw  new BusinessException("请输入日期");
        }
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date order_time = null;
        List<BeanCart>l=new ArrayList<>();
        int flag=0;
        try
        {
            order_time = sdf.parse(ordertime);
        }
        catch (ParseException e1)
        {
            throw  new BusinessException("日期错误");
        }
        try
        {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            String sql = "select start_price ,sub_price,start_date,end_date from coupon where couponid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, couponid);
            ResultSet rs = pst.executeQuery();
            double start_price = 0.0, sub_price = 0.0;
            long now = System.currentTimeMillis();
            if (rs.next())
            {
                start_price = rs.getDouble(1);
                sub_price = rs.getDouble(2);
                if (bo.getOri_amount() > start_price && now > rs.getTimestamp(3).getTime() && now < rs.getTimestamp(4).getTime())
                {
                    sql = "update orders  set set_amount = set_amount - ? ,couponid = ? ,order_time = ? ,addressid = ?, " +
                            " orderstatus = ? where orderid = ? ";
                    pst = con.prepareStatement(sql);
                    pst.setDouble(1, sub_price);
                    pst.setInt(2, couponid);
                    pst.setTimestamp(3, new Timestamp(order_time.getTime()));
                    pst.setInt(4, addressid);
                    pst.setString(5, "已支付");
                    pst.setInt(6, bo.getOrderid());
                    pst.executeUpdate();
                    pst.close();
                    flag=1;
                }
            }
            if(flag==0)
            {
                sql = "update orders  set  order_time = ? ,addressid = ?, " +
                        "orderstatus = ? where orderid = ? ";
                pst = con.prepareStatement(sql);
                pst.setTimestamp(1, new Timestamp(order_time.getTime()));
                pst.setInt(2, addressid);
                pst.setString(3, "已支付");
                pst.setInt(4, bo.getOrderid());
                pst.executeUpdate();
                pst.close();
            }
            sql = "update order_detail set orderstatus = ? where orderid = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, "已支付");
            pst.setInt(2, bo.getOrderid());
            pst.executeUpdate();
            pst.close();
            sql="select commodityid ,number from order_detail where orderid = ? ";
            pst = con.prepareStatement(sql);
            pst.setInt(1, bo.getOrderid());
            rs=pst.executeQuery();
            while(rs.next())
            {
                BeanCart bc=new BeanCart();
                bc.setCommodityid(rs.getInt(1));
                bc.setNumber(rs.getInt(2));
                l.add(bc);
            }
            rs.close();
            pst.close();
            for(int i=0;i<l.size();++i)
            {
                sql = "update commodity set remain_number = remain_number - ? where commodityid = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, l.get(i).getNumber());
                pst.setInt(2, l.get(i).getCommodityid());
                pst.executeUpdate();
                pst.close();
            }
            con.commit();
        }
        catch (SQLException e)
        {
            try
            {
                con.rollback();
            }
            catch (SQLException t)
            {
                t.printStackTrace();
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
                    e.printStackTrace();
                }
            }
        }
    }
    public void delPay(BeanOrder bo)
    {
        Connection con = null;
        int userid = BeanUser.currentLoginUser.getUserid();
        try
        {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            String sql = "update orders set orderstatus = ? where orderid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "已取消");
            pst.setInt(2, bo.getOrderid());
            pst.executeUpdate();
            pst.close();
            sql="update order_detail set orderstatus = ? where orderid = ? ";
            pst = con.prepareStatement(sql);
            pst.setString(1, "已取消");
            pst.setInt(2, bo.getOrderid());
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
            catch (SQLException ee)
            {
                ee.printStackTrace();
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
                    e.printStackTrace();
                }
            }
        }
    }
    public void searchAll(List<BeanDetailOrder> l)
    {
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            for (int i = 0; i < l.size(); ++i)
            {
                BeanDetailOrder s = l.get(i);
                String sql = "select r.discountid, discount ,min_number ,r.start_date,r.end_date from commodity_discount c ," +
                        "discount_rule r where r.discountid = c.discountid and c.commodityid = ? ";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, s.getCommodityid());
                ResultSet rs = pst.executeQuery();
                long now = System.currentTimeMillis();
                if (rs.next())
                {
                    s.setDiscountid(rs.getInt(1));
                    double discount = rs.getDouble(2);
                    if (s.getNumber() >= rs.getInt(3) && now > rs.getTimestamp(4).getTime() && now < rs.getTimestamp(5).getTime())
                    {
                        s.setDiscount(discount);
                    }
                    else
                    {
                        s.setDiscount(0.0);
                    }
                }
                else
                {
                    s.setDiscountid(0);
                    s.setDiscount(0.0);
                }
                rs.close();
                pst.close();
                sql = "select   saleid, saleprice , maxnumber ,start_date,end_date from sale where commodityid = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, s.getCommodityid());
                rs = pst.executeQuery();
                now = System.currentTimeMillis();
                if (rs.next())
                {
                    s.setSaleid(rs.getInt(1));
                    double saleprice = rs.getDouble(2);
                    if (s.getNumber() <= rs.getInt(3) && now > rs.getTimestamp(4).getTime() && now < rs.getTimestamp(5).getTime())
                    {
                        s.setSaleprice(saleprice);
                    }
                    else
                    {
                        s.setSaleprice(0.0);
                    }
                }
                else
                {
                    s.setSaleid(0);
                    s.setSaleprice(0.0);
                }
                rs.close();
                pst.close();
                sql = "select isvip from users where userid = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, s.getUserid());
                rs = pst.executeQuery();
                if (rs.next())
                {
                    String str = rs.getString(1);
                    if ("false".equals(str))
                    {
                        s.setVipprice(0.0);
                    }
                }
            }
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

    public BeanOrder loadCurrentOrder()
    {
        Connection con = null;
        int userid = BeanUser.currentLoginUser.getUserid();
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from orders where userid = ? and orderstatus = ? order by orderid DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setString(2, "待支付");
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                BeanOrder bu = new BeanOrder();
                bu.setOrderid(rs.getInt(1));
                bu.setUserid(rs.getInt(2));
                bu.setOri_amount(rs.getDouble(3));
                bu.setSet_amount(rs.getDouble(4));
                bu.setCouponid(rs.getInt(5));
                bu.setOrder_time(rs.getTimestamp(6));
                bu.setAddressid(rs.getInt(7));
                bu.setStatus(rs.getString(8));
                rs.close();
                pst.close();
                return bu;
            }
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
        return null;
    }

}
