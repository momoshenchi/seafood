package control;

import model.customer.BeanDetailOrder;
import model.customer.BeanOrder;
import model.customer.BeanUser;
import model.promote.BeanCoupon;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManager
{
    public BeanUser reg(String username, String sex, String pwd, String pwd2, String city, String mail, String phonenum) throws BaseException
    {

        if (username == null || "".equals(username))
        {
            throw new BaseException("please input username ");
        }
        if (pwd == null || pwd2 == null || "".equals(pwd) || "".equals(pwd2))
        {
            throw new BaseException("password is null");
        }
        if (!pwd.equals(pwd2))
        {
            throw new BaseException("password is wrong");
        }
        if (sex == null || "".equals(sex))
        {
            throw new BaseException("please input sex ");
        }
        java.sql.Connection con = null;
        java.sql.PreparedStatement pst = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select * from users where username = ? ";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                throw new BaseException("userid is already exist");
            }
            pst.close();
            sql = "select max(userid) from users  ";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            int userid = 1;
            if (rs.next())
            {
                userid = rs.getInt(1) + 1;
            }
            rs.close();
            pst.close();
            sql = "insert into users(userid,username,sex,pwd,phonenum,mail,city,register_time,isvip) values(?,?, ? ,? ,?," +
                    " ?, ?, ?, ?) ";
            pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setString(2, username);
            pst.setString(3, sex);
            pst.setString(4, pwd);
            pst.setString(5, phonenum);
            pst.setString(6, mail);
            pst.setString(7, city);
            long register_time = System.currentTimeMillis();
            pst.setTimestamp(8, new Timestamp(register_time));
            pst.setString(9, "false");
            pst.executeUpdate();
            BeanUser u = new BeanUser();
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
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (pst != null)
            {
                try
                {
                    pst.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
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


    public BeanUser login(String username, String pwd) throws BaseException
    {

        if (username == null || "".equals(username))
        {
            throw new BaseException("username is null");
        }
        if (pwd == null || "".equals(pwd))
        {
            throw new BaseException("pwd is null");
        }
        java.sql.Connection con = null;
        java.sql.PreparedStatement pst = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select pwd from users where username = ? ";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BaseException("user is not exist");
            }
            String p = rs.getString(1);
            if (!p.equals(pwd))
            {
                throw new BaseException("pwd is wrong");
            }
            rs.close();
            pst.close();
            sql = " select * from users where username = ? ";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            if (rs.next())
            {
                BeanUser u = new BeanUser();
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
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (pst != null)
            {
                try
                {
                    pst.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
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


    public void changePwd(BeanUser user, String oldPwd, String newPwd,
                          String newPwd2) throws BaseException
    {
        if (user == null)
        {
            throw new BaseException("user is null");
        }
        if (newPwd == null || newPwd2 == null || "".equals(newPwd) || "".equals(newPwd2) || !newPwd.equals(newPwd2))
        {
            throw new BaseException("password is null");
        }
        String pwd = user.getPwd();
        int userid = user.getUserid();
        if (!oldPwd.equals(pwd))
        {
            throw new BaseException("password is wrong");
        }
        java.sql.Connection con = null;
        java.sql.PreparedStatement pst = null;

        try
        {
            con = DBUtil.getConnection();
            String sql = "select pwd from users where userid = ? ";
            pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BaseException("userid is not exist");
            }
            String p = rs.getString(1);
            if (!p.equals(oldPwd))
            {
                throw new BaseException("pwd is wrong");
            }
            rs.close();
            pst.close();
            sql = "update  users set pwd = ? where userid = ? ";
            pst = con.prepareStatement(sql);
            pst.setString(1, newPwd);
            pst.setInt(2, userid);
            pst.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (pst != null)
            {
                try
                {
                    pst.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
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

    public void getCoupon(int conponid)
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "insert  into user_coupon(userid,couponid)values(? ,? )";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setInt(2, conponid);
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

    public BeanUser loadUserSelf()
    {
        Connection con = null;
        int userid = BeanUser.currentLoginUser.getUserid();
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from users where userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                BeanUser bu=new BeanUser();
                bu.setUserid(rs.getInt(1));
                bu.setUsername(rs.getString(2));
                bu.setSex(rs.getString(3));
                bu.setPwd(rs.getString(4));
                bu.setPhonenumber(rs.getString(5));
                bu.setMail(rs.getString(6));
                bu.setCity(rs.getString(7));
                bu.setRegister_time(rs.getTimestamp(8));
                bu.setIsvip(rs.getString(9));
                bu.setVipendtime(rs.getTimestamp(10));
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
        return  null;
    }

    public void addUserVIP(int num)
    {
        Connection con = null;
        int userid = BeanUser.currentLoginUser.getUserid();
        try
        {
            con = DBUtil.getConnection();
            String sql = "update users set isvip = ? ,vipendtime = vipendtime + ? " +
                    "where userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "true");
            long now = num * 30 * 24 * 3600 * 1000;
            pst.setTimestamp(2, new Timestamp(now));
            pst.setInt(3, userid);
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

    public List<BeanCoupon> loadAllUserCoupon()
    {
        List<BeanCoupon> l = new ArrayList<>();
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  c.couponid , detail, start_price, sub_price, start_date, end_date from user_coupon u, coupon c " +
                    "where  c.conponid = u.conponid" +
                    "and userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
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
    public List<BeanDetailOrder>loadUserDetailOrders()
    {
        Connection con = null;
        List<BeanDetailOrder>l=new ArrayList<>();
        int userid = BeanUser.currentLoginUser.getUserid();
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from order_detail where userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            ResultSet rs=pst.executeQuery();
            while(rs.next())
            {
                BeanDetailOrder bu=new BeanDetailOrder();
                bu.setOrderid(rs.getInt(1));
                bu.setCommodityid(rs.getInt(2));
                bu.setNumber(rs.getInt(3));
                bu.setPrice(rs.getDouble(4));
                bu.setDiscount(rs.getDouble(5));
                bu.setDiscountid(rs.getInt(6));
                bu.setUserid(rs.getInt(7));
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
        return  l;
    }
    public List<BeanOrder> loadUserOrders()
    {
        Connection con = null;
        List<BeanOrder>l=new ArrayList<>();
        int userid = BeanUser.currentLoginUser.getUserid();
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from orders where userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            ResultSet rs=pst.executeQuery();
            while(rs.next())
            {
                BeanOrder bu=new BeanOrder();
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
        return  l;
    }
    // 消费流程
    /*1.判断是否为vip
    2.找到促销号,折扣号


     */
    //加入购物车,为初始价
    public  void userShop (int commodityid , int number ,double price )
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  max(orderid) from orders where userid = ? and orderstatus = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setString(2,"购物车");
            ResultSet rs = pst.executeQuery();
            int orderid=1;
            if(rs.next())
            {
                orderid=rs.getInt(1);
            }
            else
            {
                sql="insert into orders(userid,ori_amount,set_amount,couponid,order_time,addressid,orderstatus) " +
                        "values (?,0,0,0,null,0,?)";
                pst = con.prepareStatement(sql);
                pst.setInt(1,userid);
                pst.setString(2,"购物车");
                pst.executeUpdate();
                pst.close();
                sql="select max(orderid) from orders where userid = ? and orderstatus = ?";
                pst = con.prepareStatement(sql);
                pst.setInt(1,userid);
                pst.setString(2,"购物车");
                rs=pst.executeQuery();
                if(rs.next())
                {
                    orderid=rs.getInt(1);
                }
            }
            rs.close();
            pst.close();
            sql="select  c.discountid ,discount from commodity_discount c,discount_rule d where " +
                    " c.discountid = d.discountid and commodityid = ? ";
            pst = con.prepareStatement(sql);
            pst.setInt(1,commodityid);
            rs=pst.executeQuery();
            double discount =0;
            int discountid =0 ;
            if(rs.next())
            {
                discountid=rs.getInt(1);
                discount=rs.getDouble(2);
            }
            rs.close();
            pst.close();
            double saleprice =0;
            int saleid=0;
            sql="select saleid , saleprice from sale where commodityid = ? ";
            pst = con.prepareStatement(sql);
            pst.setInt(1,commodityid);
            rs=pst.executeQuery();
            if(rs.next())
            {
                saleid=rs.getInt(1);
                saleprice=rs.getDouble(2);
            }
            sql="insert  into order_detail(orderid,commodityid,number,price,discount,discountid," +
                    "userid,saleid , saleprice) values(" +
                "?,?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1,orderid);
            pst.setInt(2,commodityid);
            pst.setInt(3,number);
            pst.setDouble(4,price);
            pst.setDouble(5,discount);
            pst.setInt(6,discountid);
            pst.setInt(7, userid);
            pst.setInt(8, saleid);
            pst.setDouble(9,saleprice);
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
    public void settleAccounts() throws BusinessException
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  orderid,number ,price  , discount , saleprice from order_detail where userid =? and ostatus = ?  ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setString(2,"购物车");
            ResultSet rs = pst.executeQuery();
            int number=0,orderid =0;
            double price =0.0,saleprice =0.0,discount=0.0;
            if(rs.next())
            {
                number=rs.getInt(1);
                price=rs.getDouble(2);
                discount=rs.getDouble(3);
                saleprice=rs.getDouble(4);
            }
            else
            {
                throw new BusinessException("no orders");
            }
            rs.close();
            pst.close();
            int addressid=0;
            sql="select addressid from address where userid = ? ";
            pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            rs = pst.executeQuery();
            if(rs.next())
            {
                addressid=rs.getInt(1);
            }
            double ori_amount=number*price;
            double m1=0.0,m2=0.0;
            if(discount!=0.0)
            {
                m1=number*price*discount;
            }
            if(saleprice!=0.0)
            {
                m2=number*saleprice;
            }
            double set_amount =Math.min(m1,m2);
            long now =System.currentTimeMillis();
            sql="update orders set ori_amount = ?,set_amount = ? ,order_time = ? ,addressid = ? ";
            pst = con.prepareStatement(sql);
            pst.setDouble(1,ori_amount);
            pst.setDouble(2,set_amount);
            pst.setTimestamp(3,new Timestamp(now));
            pst.setInt(4,addressid);

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
    public static void main(String[] args)
    {
        UserManager em = new UserManager();

//		BeanUser u=new BeanUser();
//		u.setUser_id("1233");
//		u.setUser_pwd("dggg");
//		try {
//			em.changePwd(u,"dggg","123","123");
//		} catch (BaseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        try
        {
            BeanUser.currentLoginUser = em.reg("1", "男", "1", "1",
                    "hangzhou", "", "");
            System.out.println(BeanUser.currentLoginUser.getUserid());
        }
        catch (BaseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
