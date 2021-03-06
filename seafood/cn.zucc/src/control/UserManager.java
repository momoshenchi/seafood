package control;

import model.customer.BeanCart;
import model.customer.BeanDetailOrder;
import model.customer.BeanOrder;
import model.customer.BeanUser;
import model.food.BeanComment;
import model.food.BeanCommodity;
import model.promote.BeanCoupon;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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
                          String newPwd2) throws BusinessException
    {
        if (user == null)
        {
            throw new BusinessException("user is null");
        }
        if (newPwd == null || newPwd2 == null || "".equals(newPwd) || "".equals(newPwd2) || !newPwd.equals(newPwd2))
        {
            throw new BusinessException("password is null");
        }
        String pwd = user.getPwd();
        int userid = user.getUserid();
        if (!oldPwd.equals(pwd))
        {
            throw new BusinessException("password is wrong");
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
                throw new BusinessException("userid is not exist");
            }
            String p = rs.getString(1);
            if (!p.equals(oldPwd))
            {
                throw new BusinessException("pwd is wrong");
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
    public  void getRandomCoupon()
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        List<Integer>num=new ArrayList<>();
        try
        {
            con = DBUtil.getConnection();
            String sql="select couponid from coupon";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while(rs.next())
            {
                num.add(rs.getInt(1));
            }
            rs.close();
            pst.close();
            Random a =new Random();
            int bound=num.size();
            int ram=a.nextInt(bound);
           int couponid=num.get(ram);
             sql = "insert  into user_coupon(userid,couponid)values(? ,? )";
             pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setInt(2, couponid);
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
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                BeanUser bu = new BeanUser();
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
        return null;
    }

    public void addUserVIP(int num) throws BusinessException
    {
        if(num<=0)
        {
            throw  new BusinessException("please input month");
        }
        Connection con = null;
        int userid = BeanUser.currentLoginUser.getUserid();
        try
        {
            con = DBUtil.getConnection();
            String sql = "select isvip ,vipendtime from users where userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                if("false".equals(rs.getString(1)))
                {
                    sql = "update users set isvip = ? ,vipendtime =  ? " +
                            "where userid = ? ";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, "true");
                    Calendar c=Calendar.getInstance();
                    c.add(Calendar.MONTH,num);
                    pst.setTimestamp(2, new Timestamp(c.getTimeInMillis()));
                    pst.setInt(3, userid);
                    pst.executeUpdate();
                    pst.close();
                }
                else
                {
                    Timestamp now = rs.getTimestamp(2);
                    sql = "update users set  vipendtime =  ? " +
                            "where userid = ? ";
                    pst = con.prepareStatement(sql);
                    Calendar c=Calendar.getInstance();
                    c.setTime(now);
                    c.add(Calendar.MONTH,num);
                    pst.setTimestamp(1, new Timestamp(c.getTimeInMillis()));
                    pst.setInt(2, userid);
                    pst.executeUpdate();
                    pst.close();
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

    public List<BeanCoupon> loadUserCoupon()
    {
        List<BeanCoupon> l = new ArrayList<>();
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  c.couponid , detail, start_price, sub_price, start_date, end_date from user_coupon u, coupon c " +
                    "where  c.couponid = u.couponid " +
                    " and userid = ? ";
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

    public List<BeanCart> loadUserCart()
    {
        Connection con = null;
        List<BeanCart> l = new ArrayList<>();
        int userid = BeanUser.currentLoginUser.getUserid();
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from cart where userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanCart bu = new BeanCart();
                bu.setUserid(rs.getInt(1));
                bu.setCommodityid(rs.getInt(2));
                bu.setCommodityname(rs.getString(3));
                bu.setNumber(rs.getInt(4));
                bu.setPrice(rs.getDouble(5));
                bu.setVipprice(rs.getDouble(6));
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

    // 消费流程
    /*1.判断是否为vip,最后搞
    2.找到促销号,折扣号


     */
    //加入购物车,为初始价
    //这边和按钮相关
    public void addCart(int commodityid, int number, double price, double vipprice) throws BusinessException
    {
        if(number<=0)
        {
            throw  new BusinessException("please input number");
        }
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql="select remain_number from commodity where commodityid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, commodityid);
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                int n=rs.getInt(1);
                if(number>n)
                {
                    throw  new BusinessException("库存不足");
                }
            }
            else
            {
                throw  new BusinessException("commodity is not exist");
            }
            sql="select * from cart where commodityid = ? and userid = ? ";
             pst = con.prepareStatement(sql);
            pst.setInt(1, commodityid);
            pst.setInt(2, userid);
             rs=pst.executeQuery();
            if(rs.next())
            {
                sql="update cart set number = number + ? where commodityid = ? and userid = ?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, number);
                pst.setInt(2, commodityid);
                pst.setInt(3, userid);
                pst.executeUpdate();
                pst.close();
                return;
            }
             sql="select commodityname from commodity where commodityid = ? ";
             pst = con.prepareStatement(sql);
            pst.setInt(1, commodityid);
            rs=pst.executeQuery();
            String commodityname =null ;
            if(rs.next())
            {
                commodityname=rs.getString(1);
            }
             sql = "insert  into cart(userid,commodityid,commodityname,number,price," +
                    "vipprice) values(?,?,?,?,?,?)";
             pst = con.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setInt(2, commodityid);
            pst.setString(3,commodityname);
            pst.setInt(4, number);
            pst.setDouble(5, price);
            pst.setDouble(6, vipprice);
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

    public void modifyCart(BeanCart bc) throws BusinessException
    {
        if(bc.getNumber()<=0)
        {
            throw new BusinessException("please input number");
        }
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "update  cart set number = ? where userid = ? and commodityid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bc.getNumber());
            pst.setInt(2, bc.getUserid());
            pst.setInt(3, bc.getCommodityid());
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

    public void delCart(BeanCart bc)
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "delete  from cart where userid = ? and commodityid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bc.getUserid());
            pst.setInt(2, bc.getCommodityid());
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


    public void modifyUserInfo(BeanUser bu) throws BaseException
    {
        if (bu.getSex() == null || "".equals(bu.getSex()))
        {
            throw new BaseException("please input sex ");
        }
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "update  users set sex = ?  ,phonenum = ? ,mail = ? ,city = ? where userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,bu.getSex());
            pst.setString(2,bu.getPhonenumber());
            pst.setString(3,bu.getMail());
            pst.setString(4,bu.getCity());
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
