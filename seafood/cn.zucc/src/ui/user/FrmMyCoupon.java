package ui.user;

import control.PromoteManager;
import control.UserManager;
import model.promote.BeanCoupon;
import ui.promote.FrmCouManager_add;
import ui.promote.FrmCouManager_mod;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmMyCoupon extends JDialog
{
    private Object tblTitle[] = {"Detail", "start_price", "sub_price", "start_date", "end_date"};
    private Object tblData[][];
    List<BeanCoupon> pubs;
    private  JLabel t=new JLabel("优惠券");
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy--MM--dd");
        pubs = (new UserManager()).loadUserCoupon();
        tblData = new Object[pubs.size()][6];
        for (int i = 0; i < pubs.size(); i++)
        {
            tblData[i][0] = pubs.get(i).getDetail();
            tblData[i][1] = pubs.get(i).getStart_price() + "";
            tblData[i][2] = pubs.get(i).getSub_price() + "";
            tblData[i][3] = sdf.format(pubs.get(i).getStart_date());
            tblData[i][4] = sdf.format(pubs.get(i).getEnd_date());
        }
        tablmod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public FrmMyCoupon(JDialog f, String s, boolean b)
    {
        super(f, s, b);
        t.setFont(new Font("微软雅黑", Font.ITALIC, 24));
        this.getContentPane().add(t,BorderLayout.NORTH);
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.setResizable(false);

    }

}
