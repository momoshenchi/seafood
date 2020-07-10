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

    public List<BeanDetailOrder> loadUserDetailOrders()
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
                bu.setCommodityid(rs.getInt(1));
                bu.setNumber(rs.getInt(2));
                bu.setPrice(rs.getDouble(3));
                bu.setUserid(rs.getInt(4));
                bu.setVipprice(rs.getDouble(5));
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
    public void addCart(int commodityid, int number, double price, double vipprice)
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "insert  into cart(commodityid,number,price," +
                    "userid,vipprice) values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, commodityid);
            pst.setInt(2, number);
            pst.setDouble(3, price);
            pst.setInt(4, userid);
            pst.setDouble(5, vipprice);
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

    public void modifyCart(BeanCart bc)
    {
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
//点击购买按钮
    public void addDetailOrder() throws BusinessException
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        List<BeanDetailOrder> l = new ArrayList<>();
        List<BeanCart> beancart = loadUserCart();
        if (beancart == null)
        {
            throw new BusinessException("there are no commodities");
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
            l.add(bc);
        }
        searchAll(l);
        try
        {
            con = DBUtil.getConnection();
            int orderid=1;
            String sql="select max(orderid) from order_detail";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                orderid=rs.getInt(1);
            }
            rs.close();
            pst.close();

            double sum=0;
            double set_amount = 0;
            for (int i = 0; i < l.size(); ++i)
            {
                BeanDetailOrder bo=l.get(i);
                sql = "insert  into order_detail(orderid,commodityid,userid,number,price,vipprice,discountid,discount," +
                        "saleid,saleprice,orderstatus) values(?,?,?,?,?,?,?,?,?,?,?)";
                pst = con.prepareStatement(sql);
                pst.setInt(1, orderid);
                pst.setInt(2,bo.getCommodityid());
                pst.setInt(3,bo.getUserid());
                int number=bo.getNumber();
                double price =bo.getPrice();
                sum+=number*price;
                pst.setInt(4,number);
                pst.setDouble(5,price);
                double discount =bo.getDiscount();
                double vip =bo.getVipprice();
                double saleprice=bo.getSaleprice();
                double m1=0x7ffff,m2=0x7ffff,m3=0x7ffff;
                if (discount != 0.0)
                {
                     m1 = number * price * discount;
                }
                if (saleprice != 0.0)
                {
                    m2 = number * saleprice;
                }
                if(vip!=0.0)
                {
                    m3=number*vip;
                }
                set_amount += Math.min(Math.min(m1, m2),m3);
                pst.setDouble(6,vip);
                pst.setInt(7,bo.getDiscountid());
                pst.setDouble(8,discount);
                pst.setInt(9,bo.getSaleid());
                pst.setDouble(10,saleprice);
                pst.setString(11,"待支付");
            }

            sql = "insert  into orders(orderid,userid,ori_amount,set_amount,couponid,order_time,addressid,orderstatus) " +
                    "values(?,?,?,?,0,null,0,?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, orderid);
            pst.setInt(2, userid);
            pst.setDouble(3,sum);
            pst.setDouble(4,set_amount);
            pst.setString(8,"待支付");
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
    public void pay(BeanOrder bo ,int couponid ,String ordertime,int addressid)
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date order_time = null;
        try
        {
            order_time = sdf.parse(ordertime);
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
        }
        try
        {
            con = DBUtil.getConnection();
            String sql = "update orders  set couponid = ? ,order_time = ? ,addressid = ?, " +
                    "orderstatus = ? where orderid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, couponid);
            pst.setTimestamp(2,new Timestamp(order_time.getTime()));
            pst.setInt(3, addressid);
            pst.setString(4,"已支付");
            pst.setInt(5, bo.getOrderid());
            pst.executeUpdate();
            pst.close();
            sql="update order_detail set orderstatus = ? where orderid = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1,"已支付");
            pst.setInt(2, bo.getOrderid());
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
                sql="select isvip from users where userid = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, s.getUserid());
                rs = pst.executeQuery();
                if(rs.next())
                {
                     String str=rs.getString(1);
                     if("false".equals(str))
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
    public  void addComment(BeanCommodity bc,String comments, String levels, String picture)
    {
        int userid = BeanUser.currentLoginUser.getUserid();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "insert into customer_comment(commodityid,userid,comments,commentdate,levels,picture) " +
                    "values(?,?,?,?,?,?)";
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
    public void modifyUserInfo(BeanUser bu)
    {
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
