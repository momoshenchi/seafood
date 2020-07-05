package control;

import model.customer.BeanAddr;
import model.customer.BeanUser;
import model.food.BeanType;
import model.promote.BeanCoupon;
import model.promote.BeanDiscount;
import model.promote.BeanSale;
import model.root.BeanAdmin;
import model.root.BeanPurchase;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminManager
{
    public BeanAdmin login(String adminname, String pwd) throws BaseException
    {
        if (adminname == null || "".equals(adminname))
        {
            throw new BaseException("adminname is null");
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
            String sql = "select pwd from admin where adminname = ? ";
            pst = con.prepareStatement(sql);
            pst.setString(1, adminname);
            ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BaseException("admin is not exist");
            }
            String p = rs.getString(1);
            if (!p.equals(pwd))
            {
                throw new BaseException("pwd is wrong");
            }
            rs.close();
            pst.close();
            sql = " select * from admin where adminname = ? ";
            pst = con.prepareStatement(sql);
            pst.setString(1, adminname);
            rs = pst.executeQuery();
            if (rs.next())
            {
                BeanAdmin a = new BeanAdmin();
                a.setAdminid(rs.getInt(1));
                a.setAdminname(rs.getString(2));
                a.setPwd(rs.getString(3));
                return a;
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public void changePwd(BeanAdmin user, String oldPwd, String newPwd,
                          String newPwd2) throws BaseException {
        if(user==null)
        {
            throw new BaseException("adminuser is null");
        }
        if(newPwd==null||newPwd2==null||"".equals(newPwd)||"".equals(newPwd2)||!newPwd.equals(newPwd2)) {
            throw new BaseException("password is null");
        }
        String pwd=user.getPwd();
        int adminid=user.getAdminid();
        if(!oldPwd.equals(pwd))
        {
            throw new BaseException("password is wrong");
        }
        java.sql.Connection con=null;
        try
        {
            con=DBUtil.getConnection();
            String sql="select pwd from admin where adminid = ? ";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1, adminid);
            ResultSet rs=pst.executeQuery();
            if(!rs.next())
            {
                throw new BaseException("adminid is not exist");
            }
            String p=rs.getString(1);
            if(!p.equals(oldPwd))
            {
                throw new BaseException("pwd is wrong");
            }
            rs.close();
            pst.close();
            sql="update  admin set pwd = ? where adminid = ? ";
            pst=con.prepareStatement(sql);
            pst.setString(1, newPwd);
            pst.setInt(2,adminid);
            pst.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
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
    public void addType(String typename, String description) throws BusinessException
    {
        if (typename == null || "".equals(typename))
        {
            throw new BusinessException("please input typename");
        }
        if(description==null||"".equals(description))
        {
            throw new BusinessException("please input description");
        }
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "insert into  foodtype(typename , description) values" +
                    "(?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, typename);
            pst.setString(2, description);
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
    public  void modifyType(BeanType bt ,String description) throws BusinessException
    {
        if(description==null||"".equals(description))
        {
            throw new BusinessException("please input description");
        }
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "update foodtype set description = ?  where typeid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, description);
            pst.setInt(2, bt.getTypeid());
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
    public  void addCommodity(String commodityname,double price ,double vipprice,int remain_number,String spec,String detail,int typeid) throws BusinessException
    {
        if(commodityname==null||"".equals(commodityname))
        {
            throw  new BusinessException("please input commodityname");
        }
        if(price==0)
        {
            throw  new BusinessException("please input price");
        }
        if(vipprice==0)
        {
            throw  new BusinessException("please input vipprice");
        }
//        if(remain_number==0)
//        {
//            throw  new BusinessException("please input number");
//        }
        if(spec==null||"".equals(spec))
        {
            throw  new BusinessException("please input spec");
        }
        if(typeid==0)
        {
            throw  new BusinessException("please input typeid");
        }
        java.sql.Connection con = null;
        try
        {

            con = DBUtil.getConnection();
            con.setAutoCommit(false);
             String sql = "insert  into commodity(commodityname,price,vipprice,remain_number," +
                    "spec,detail,typeid) values(?,?,?,?,?,?,?) ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, commodityname);
            pst.setDouble(2,price);
            pst.setDouble(3,vipprice);
            pst.setInt(4,remain_number);
            pst.setString(5,spec);
            pst.setString(6,detail);
            pst.setInt(7,typeid);
            pst.executeUpdate();
            pst.close();
            sql="update purchase set status = ? ";
            pst = con.prepareStatement(sql);
            pst.setString(1,"上架");
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
    }
    //采购表---其实就是仓库和消费记录    (所以可以存放商品表里没有的内容)  状态("下单","在库","上架")
    public void addPur(int commodityid, int number) throws BusinessException
    {
        if (commodityid <= 0)
        {
            throw new BusinessException("please input commodityid");
        }
        if (number <= 0)
        {
            throw new BusinessException("please input number");
        }
        java.sql.Connection con = null;
        int adminid = BeanAdmin.currentadmin.getAdminid();
        try
        {
            con = DBUtil.getConnection();
            String sql="select * from commodity where commodityid = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, commodityid);
            ResultSet rs=pst.executeQuery();
            if(!rs.next())
            {
                sql="insert  into commodity() ";
            }
             sql = "insert into  purchase(commodityid,number,patatus,adminid,purchasedate) values" +
                    "(?,?,?,?,?)";
             pst = con.prepareStatement(sql);
            pst.setInt(1, commodityid);
            pst.setInt(2, number);
            pst.setString(3, "下单");
            pst.setInt(4, adminid);
            long register_time = System.currentTimeMillis();
            pst.setTimestamp(5, new Timestamp(register_time));
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

    public void modifyPur(BeanPurchase bp) throws BusinessException
    {
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            String sql = "update purchase set pstatus = ? " +
                    "where purchaseid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "入库");
            pst.setInt(2, bp.getPurchaseid());
            pst.executeUpdate();
            pst.close();
            sql = "select * from commodity where  commodityid = ? ";
            pst = con.prepareStatement(sql);
            pst.setInt(1, bp.getCommodityid());
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                rs.close();
                pst.close();
                sql = "update commodity set remain_number = remain_number + ? " +
                        "where commodityid = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, bp.getNumber());
                pst.setInt(2, bp.getCommodityid());
                pst.executeUpdate();
            }
            else
            {
                throw new BusinessException("please add new commodity");
            }
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
            // TODO 自动生成的 catch 块
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

    public void addDiscount(String detail, int min_number, double discount, String startime, String endtime) throws BusinessException
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
            // TODO 自动生成的 catch 块
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
                    // TODO Auto-generated catch block
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

    public void delDiscount(BeanDiscount bd)
    {
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            String sql = "delete from discount_rule where  discountid = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bd.getDiscount());
            pst.executeUpdate();
            pst.close();
            sql = "delete from commodity_discount where  discountid = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, bd.getDiscount());
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

    public static void main(String[] args)
    {
        AdminManager am = new AdminManager();
        try
        {
            am.login("root", "1");
        }
        catch (BaseException e)
        {
            e.printStackTrace();
        }
    }
}
