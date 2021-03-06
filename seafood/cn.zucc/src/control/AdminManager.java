package control;

import model.customer.BeanAddr;
import model.customer.BeanUser;
import model.food.*;
import model.promote.BeanCoupon;
import model.promote.BeanDiscount;
import model.promote.BeanSale;
import model.root.BeanAdmin;
import model.root.BeanPurchase;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                          String newPwd2) throws BaseException
    {
        if (user == null)
        {
            throw new BaseException("adminuser is null");
        }
        if (newPwd == null || newPwd2 == null || "".equals(newPwd) || "".equals(newPwd2) || !newPwd.equals(newPwd2))
        {
            throw new BaseException("password is null");
        }
        String pwd = user.getPwd();
        int adminid = user.getAdminid();
        if (!oldPwd.equals(pwd))
        {
            throw new BaseException("password is wrong");
        }
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select pwd from admin where adminid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, adminid);
            ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BaseException("adminid is not exist");
            }
            String p = rs.getString(1);
            if (!p.equals(oldPwd))
            {
                throw new BaseException("pwd is wrong");
            }
            rs.close();
            pst.close();
            sql = "update  admin set pwd = ? where adminid = ? ";
            pst = con.prepareStatement(sql);
            pst.setString(1, newPwd);
            pst.setInt(2, adminid);
            pst.executeUpdate();
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

    public void addType(String typename, String description) throws BusinessException
    {
        if (typename == null || "".equals(typename))
        {
            throw new BusinessException("please input typename");
        }
        if (description == null || "".equals(description))
        {
            throw new BusinessException("please input description");
        }
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql="select * from foodtype where typename = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, typename);
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                throw new BusinessException("typename is already exist");
            }
            rs.close();
            pst.close();
            sql = "insert into  foodtype(typename , description) values" +
                    "(?,?)";
             pst = con.prepareStatement(sql);
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

    public void modifyType(BeanType bt) throws BusinessException
    {
        if (bt.getDescription() == null || "".equals(bt.getDescription()))
        {
            throw new BusinessException("please input description");
        }
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "update foodtype set description = ?  where typeid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, bt.getDescription());
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

    public void delType(BeanType bt) throws BusinessException
    {
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            String sql = "delete from foodtype where  typeid = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bt.getTypeid());
            pst.executeUpdate();
            pst.close();
            sql = "delete from commodity where typeid = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, bt.getTypeid());
            pst.executeUpdate();
            pst.close();
            con.commit();
        }
        catch (SQLException e)
        {
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
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

    public List<BeanType> loadAllType()
    {
        List<BeanType> l = new ArrayList<>();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from foodtype ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanType bt = new BeanType();
                bt.setTypeid(rs.getInt(1));
                bt.setTypename(rs.getString(2));
                bt.setDescription(rs.getString(3));
                l.add(bt);
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

    public List<BeanCommodity> loadAllCommodity()
    {
        List<BeanCommodity> l = new ArrayList<>();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from commodity ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanCommodity bc = new BeanCommodity();
                bc.setCommodityid(rs.getInt(1));
                bc.setCommodityname(rs.getString(2));
                bc.setPrice(rs.getDouble(3));
                bc.setVipprice(rs.getDouble(4));
                bc.setRemain_number(rs.getInt(5));
                bc.setSpec(rs.getString(6));
                bc.setDetail(rs.getString(7));
                bc.setPicture(rs.getString(8));
                bc.setTypeid(rs.getInt(9));
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

    public void addCommodity(String commodityname, double price, double vipprice, String spec,
                             String detail, String picture, int typeid) throws BusinessException
    {
        if (commodityname == null || "".equals(commodityname))
        {
            throw new BusinessException("please input commodityname");
        }
        if (price <= 0)
        {
            throw new BusinessException("please input price");
        }
        if (vipprice <= 0)
        {
            throw new BusinessException("please input vipprice");
        }
        if (vipprice > price)
        {
            throw new BusinessException("vipprice is not allowed to be high than price");
        }
        if (spec == null || "".equals(spec))
        {
            throw new BusinessException("please input spec");
        }
        if (typeid <= 0)
        {
            throw new BusinessException("please input typeid");
        }
        if(picture== null || "".equals(picture))
        {
            throw new BusinessException("please input picture");
        }
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  number from purchase where pstatus = ? and commodityname = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "新货入库");
            pst.setString(2, commodityname);
            ResultSet rs = pst.executeQuery();
            int remain_number = 0;
            if (rs.next())
            {
                remain_number = rs.getInt(1);
            }
            else
            {
                throw new BusinessException("commodity is not exist");
            }
            sql = "select * from foodtype where typeid = ? ";
            pst = con.prepareStatement(sql);
            pst.setInt(1, typeid);
            rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("type is not exist");
            }
            rs.close();
            pst.close();
            con.setAutoCommit(false);
            sql = "insert  into commodity(commodityname,price,vipprice,remain_number," +
                    "spec,detail,picture,typeid) values(?,?,?,?,?,?,?,?) ";
            pst = con.prepareStatement(sql);
            pst.setString(1, commodityname);
            pst.setDouble(2, price);
            pst.setDouble(3, vipprice);
            pst.setInt(4, remain_number);
            pst.setString(5, spec);
            pst.setString(6, detail);
            pst.setString(7, picture);
            pst.setInt(8, typeid);
            pst.executeUpdate();
            pst.close();
            sql = "update purchase set pstatus = ? where commodityname = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, "上架");
            pst.setString(2, commodityname);
            pst.executeUpdate();
            pst.close();
            con.commit();
        }
        catch (SQLException e)
        {
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
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

    public void modifyCommodity(BeanCommodity bc) throws BusinessException
    {
        if (bc.getSpec() == null || "".equals(bc.getSpec()))
        {
            throw new BusinessException("please input spec");
        }
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "update  commodity  set  price = ? , vipprice = ?, remain_number = ? , spec =? ," +
                    "detail = ? ,picture = ? where  commodityid = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDouble(1, bc.getPrice());
            pst.setDouble(2, bc.getVipprice());
            pst.setInt(3, bc.getRemain_number());
            pst.setString(4, bc.getSpec());
            pst.setString(5, bc.getDetail());
            pst.setString(6, bc.getPicture());
            pst.setInt(7, bc.getCommodityid());
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

    public void delCommodity(BeanCommodity bc)
    {
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            String sql = "delete from commodity where  commodityid = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bc.getCommodityid());
            pst.executeUpdate();
            pst.close();
            sql = "delete from commodity_discount where  commodityid = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, bc.getCommodityid());
            pst.executeUpdate();
            pst.close();
            sql = "delete from commodity_menu where  commodityid = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, bc.getCommodityid());
            pst.executeUpdate();
            pst.close();
            sql = "delete from sale where  commodityid = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, bc.getCommodityid());
            pst.executeUpdate();
            pst.close();
            sql = "delete from order_detail where  commodityid = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, bc.getCommodityid());
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

    public void modifyComNum(BeanPurchase bp) throws BusinessException
    {
        if (!bp.getStatus().equals("入库"))
        {
            throw new BusinessException("can't update");
        }
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            String sql = "update commodity set remain_number = remain_number + ? " +
                    "where commodityid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bp.getNumber());
            pst.setInt(2, bp.getCommodityid());
            pst.executeUpdate();
            pst.close();
            sql = "update purchase set pstatus = ? where commodityname = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, "上架");
            pst.setString(2, bp.getCommodityname());
            pst.executeUpdate();
            pst.close();
            con.commit();
        }
        catch (SQLException e)
        {
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
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

    //采购表---其实就是仓库和消费记录    (所以可以存放商品表里没有的内容)  状态("新货","下单","入库","上架")
    public void addPurchase(String commodityname, int number) throws BusinessException
    {
        if (commodityname == null || "".equals(commodityname))
        {
            throw new BusinessException("please input commodityname");
        }
        if (number <= 0)
        {
            throw new BusinessException("please input number");
        }
        Connection con = null;
        int adminid = BeanAdmin.currentadmin.getAdminid();
        try
        {
            con = DBUtil.getConnection();
            String sql = "select * from commodity where commodityname = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, commodityname);
            ResultSet rs = pst.executeQuery();
            int commodityid = 1;
            if (rs.next())
            {
                commodityid = rs.getInt(1);
                sql = "insert into  purchase(commodityid,number,pstatus,adminid,purchasedate,commodityname) values" +
                        "(?,?,?,?,?,?)";
                pst = con.prepareStatement(sql);
                pst.setInt(1, commodityid);
                pst.setInt(2, number);
                pst.setString(3, "下单");
                pst.setInt(4, adminid);
                long register_time = System.currentTimeMillis();
                pst.setTimestamp(5, new Timestamp(register_time));
                pst.setString(6, commodityname);
                pst.executeUpdate();
                pst.close();
            }
            else
            {
                sql = "insert into  purchase(number,pstatus,adminid,purchasedate,commodityname) values" +
                        "(?,?,?,?,?)";
                pst = con.prepareStatement(sql);
                pst.setInt(1, number);
                pst.setString(2, "新货");
                pst.setInt(3, adminid);
                long register_time = System.currentTimeMillis();
                pst.setTimestamp(4, new Timestamp(register_time));
                pst.setString(5, commodityname);
                pst.executeUpdate();
                pst.close();
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
    public  void  addMenuCommodity(int commodityid , int menuid,String description) throws BusinessException
    {
        if(menuid<=0)
        {
            throw new BusinessException("please input menuname");
        }
        if(commodityid<=0)
        {
            throw new BusinessException("please input commidityid");
        }
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select * from  menu where menuid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, menuid);
            ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("menuid is not exist");
            }
            sql="select  * from commodity where commodityid =? ";
            pst = con.prepareStatement(sql);
            pst.setInt(1, commodityid);
            rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("commodityid is not exist");
            }
            sql="select  * from commodity_menu where commodityid = ?  and menuid = ? ";
            pst = con.prepareStatement(sql);
            pst.setInt(1, commodityid);
            pst.setInt(2, menuid);
            rs = pst.executeQuery();
            if (rs.next())
            {
                throw new BusinessException("Recommend is already exist");
            }
            sql = "insert into  commodity_menu(commodityid,menuid,description) values(" +
                    "?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, commodityid);
            pst.setInt(2, menuid);
            pst.setString(3, description);
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
    public  void  delMenuCommodity(BeanRecommend br)
    {
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "delete from commodity_menu where commodityid = ? and menuid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, br.getCommodityid());
            pst.setInt(2, br.getMenuid());
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
    public  List<BeanRecommend> loadAllRecommend()
    {
        List<BeanRecommend> l = new ArrayList<>();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from commodity_menu ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanRecommend bm = new BeanRecommend();
                bm.setCommodityid(rs.getInt(1));
                bm.setMenuid(rs.getInt(2));
                bm.setDescription(rs.getString(3));
                l.add(bm);
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
    public List<BeanUserRecommend>loadUserRecommend()
    {
        List<BeanUserRecommend> l = new ArrayList<>();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select c.commodityname ,c.price ,m.menuname ,m.ingredient  from commodity_menu h, menu m ,commodity c where m.menuid = h.menuid and " +
                    "c.commodityid = h.commodityid ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanUserRecommend bm = new BeanUserRecommend();
                bm.setCommodityname(rs.getString(1));
                bm.setPrice(rs.getDouble(2));
                bm.setMenuname(rs.getString(3));
                bm.setIngredient(rs.getString(4));
                l.add(bm);
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
    public void addMenu(String menuname, String ingredient, String step, String picture) throws BusinessException
    {
        if (menuname == null || "".equals(menuname))
        {
            throw new BusinessException("please input menuname");
        }
        if (ingredient == null || "".equals(ingredient))
        {
            throw new BusinessException("please input ingredient");
        }
        Connection con = null;
        int adminid = BeanAdmin.currentadmin.getAdminid();
        try
        {
            con = DBUtil.getConnection();
            String sql = "select * from  menu where menuname = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, menuname);
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                throw new BusinessException("menuname is already exist");
            }
            sql = "insert into  menu(menuname,ingredient,step,picture) values(" +
                    "?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, menuname);
            pst.setString(2, ingredient);
            pst.setString(3, step);
            pst.setString(4, picture);
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

    public void modifyMenu(BeanMenu bm) throws BusinessException
    {
        if (bm.getIngredient() == null || "".equals(bm.getIngredient()))
        {
            throw new BusinessException("please input ingredient");
        }
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "update  menu  set  ingredient = ? , step = ?, picture = ? " +
                    " where  menuid = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, bm.getIngredient());
            pst.setString(2, bm.getStep());
            pst.setString(3, bm.getPicture());
            pst.setInt(4,bm.getMenuid());
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
    public void delMenu(BeanMenu bm)
    {
        java.sql.Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            String sql = "delete from menu where  menuid = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bm.getMenuid());
            pst.executeUpdate();
            pst.close();
            sql = "delete from commodity_menu where menuid = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, bm.getMenuid());
            pst.executeUpdate();
            pst.close();
            con.commit();
        }
        catch (SQLException e)
        {
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
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
    public List<BeanMenu> loadAllMenu()
    {
        List<BeanMenu> l = new ArrayList<>();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from menu ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanMenu bm = new BeanMenu();
                bm.setMenuid(rs.getInt(1));
                bm.setMenuname(rs.getString(2));
                bm.setIngredient(rs.getString(3));
                bm.setStep(rs.getString(4));
                bm.setPicture(rs.getString(5));
                l.add(bm);
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

    //入库
    public void modifyPurchase(BeanPurchase bp) throws BusinessException
    {
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "update purchase set pstatus = ? " +
                    "where purchaseid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            if (bp.getStatus().equals("下单"))
            {
                pst.setString(1, "入库");
            }
            else if (bp.getStatus().equals("新货"))
            {
                pst.setString(1, "新货入库");
            }
            pst.setInt(2, bp.getPurchaseid());
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

    public List<BeanPurchase> loadAllPurchase()
    {
        List<BeanPurchase> l = new ArrayList<>();
        Connection con = null;
        try
        {
            con = DBUtil.getConnection();
            String sql = "select  * from purchase ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanPurchase bp = new BeanPurchase();
                bp.setPurchaseid(rs.getInt(1));
                bp.setCommodityid(rs.getInt(2));
                bp.setNumber(rs.getInt(3));
                bp.setStatus(rs.getString(4));
                bp.setAdminid(rs.getInt(5));
                bp.setPurchasedate(rs.getTimestamp(6));
                bp.setCommodityname(rs.getString(7));
                l.add(bp);
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
