package ui.promote;

import control.PromoteManager;
import model.promote.BeanCoupon;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmCouManager extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnAdd = new JButton("添加优惠券");
    private JButton btnModify = new JButton("修改优惠券");
    private JButton btnDelete = new JButton("删除优惠券");

    private Object tblTitle[] = {"Couponid", "Detail", "start_price", "sub_price", "start_date", "end_date"};
    private Object tblData[][];
    List<BeanCoupon> pubs;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy--MM--dd");
        pubs = (new PromoteManager()).loadAllCoupon();
        tblData = new Object[pubs.size()][6];
        for (int i = 0; i < pubs.size(); i++)
        {
            tblData[i][0] = pubs.get(i).getCouponid() + "";
            tblData[i][1] = pubs.get(i).getDetail();
            tblData[i][2] = pubs.get(i).getStart_price() + "";
            tblData[i][3] = pubs.get(i).getSub_price() + "";
            tblData[i][4] = sdf.format(pubs.get(i).getStart_date());
            tblData[i][5] = sdf.format(pubs.get(i).getEnd_date());
        }
        tablmod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public FrmCouManager(Frame f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnModify);
        toolBar.add(btnDelete);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnAdd.addActionListener(this);
        this.btnModify.addActionListener(this);
        this.btnDelete.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAdd)
        {
            FrmCouManager_add add = new FrmCouManager_add(this, "add", true);
            add.setVisible(true);
            if (add.getDetail() != null)
            {
                reloadTable();
            }
        }
        else if (e.getSource() == this.btnModify)
        {
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择coupon","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanCoupon p=this.pubs.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定修改吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new PromoteManager()).modifyCoupon(p);
                this.reloadTable();
            }
        }
        else if (e.getSource() == this.btnDelete)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择coupon", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanCoupon p = this.pubs.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定删除吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new PromoteManager()).delCoupon(p);
                this.reloadTable();
            }
        }
    }
}
