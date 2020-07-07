package control;

import model.customer.BeanAddr;
import model.customer.BeanUser;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressManager {
    public BeanAddr addAddr(String detail_add,String name,String phone)throws BusinessException
    {
        if(detail_add==null||"".equals(detail_add))
        {
            throw new BusinessException("detail is null");
        }
        if(name==null||"".equals(name))
        {
            throw new BusinessException("name is null");
        }
        if(phone==null||"".equals(phone))
        {
            throw new BusinessException("phone is null");
        }
        java.sql.Connection con=null;
        int userid = BeanUser.currentLoginUser.getUserid();
        try {
            con = DBUtil.getConnection();
            String sql = "select * from address where detail_add = ? and userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, detail_add);
            pst.setInt(2, userid);
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                throw new BusinessException("detail_address is already exist");
            }
            pst.close();
            sql="select max(addressid) from address  ";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            int addressid=1;
            if(rs.next())
            {
                addressid=rs.getInt(1)+1;
            }
            rs.close();
            pst.close();
            sql="insert into address(addressid,userid,detail_add,contactname,phonenum) values(?,?, ? ,? ,?)";
            pst=con.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setInt(2, addressid);
            pst.setString(3, detail_add);
            pst.setString(4, name);
            pst.setString(5, phone);
            pst.executeUpdate();
            BeanAddr ba=new BeanAddr();
            ba.setAddressid(addressid);
            ba.setContactname(name);
            ba.setDetail_address(detail_add);
            ba.setUserid(userid);
            ba.setPhonenumber(phone);
            return ba;
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
        return  null;

    }

    public List<BeanAddr> loadAllUserAddr(){
        List<BeanAddr> result=new ArrayList<>();
        java.sql.Connection con=null;
        int userid = BeanUser.currentLoginUser.getUserid();
        try {
            con = DBUtil.getConnection();
            String sql = "select  * from address where userid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1,userid);
            ResultSet rs=pst.executeQuery();
            while(rs.next())
            {
                BeanAddr ba=new BeanAddr();
                ba.setAddressid(rs.getInt(1));
                ba.setUserid(rs.getInt(2));
                ba.setContactname(rs.getString(3));
                ba.setDetail_address(rs.getString(4));
                ba.setPhonenumber(rs.getString(5));
                result.add(ba);
            }
            rs.close();
            pst.close();
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

        return  result;
    }
    public  void modifyAddr(BeanAddr br)
    {
        java.sql.Connection con=null;
        int userid = BeanUser.currentLoginUser.getUserid();
        try {
            con = DBUtil.getConnection();
            String sql = "update address set detail_add =? and contactname =? and  phonenum = ? " +
                    "where addressid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, br.getDetail_address());
            pst.setString(2, br.getContactname());
            pst.setString(3, br.getPhonenumber());
            pst.setInt(4, br.getAddressid());
            pst.executeUpdate();
            pst.close();
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
    public  void delAddr(BeanAddr br)
    {
        java.sql.Connection con=null;
        try {
            con = DBUtil.getConnection();
            String sql = "delete from address  where addressid = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, br.getAddressid());
            pst.executeUpdate();
            pst.close();
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
                    e.printStackTrace();
                }
            }
        }
    }


}
