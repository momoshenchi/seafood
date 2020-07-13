package ui.user;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import control.ReadImage;
import model.promote.BeanCoupon;
import util.DBUtil;

public class Test extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel rightBar = new JPanel();
    private JButton btnAdd = new JButton("添加购物车");
    private JButton btnModify = new JButton("修改");
    private JButton btnDelete = new JButton("删除");

    private Object tblTitle[] = {"description", "picture"};
    private Object tblData[][];
    List<BeanCoupon> pubs;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy--MM--dd");
//	        pubs = (new PromoteManager()).loadAllCoupon();
        tblData = new Object[2][2];
        for (int i = 0; i < 2; i++)
        {
            tblData[i][0] = i + "";
            tblData[i][1] = i + "";

        }
        tablmod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }


    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnAdd)
            JOptionPane.showMessageDialog(null, "只有待支付才能付款", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args)
    {
        Connection con;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try
        {
            con = DBUtil.getConnection();
            String sql = "insert into sale(commodityid,saleprice,maxnumber,start_date,end_date) " +
                    "values(3,123,10,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            String date = "2020-01-25";
            int month=1;

            Calendar c=Calendar.getInstance();
            c.add(Calendar.MONTH,1);
            try
            {
                pst.setTimestamp(1, new java.sql.Timestamp(sdf.parse(date).getTime()));
                pst.setTimestamp(2, new java.sql.Timestamp(c.getTimeInMillis()));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            pst.executeUpdate();
            pst.close();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

    }
}
