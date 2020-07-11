package ui.user;

import control.OrderManager;
import model.customer.BeanOrder;
import model.food.BeanUserRecommend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmHistory extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();

    private JButton btndetail = new JButton("查看详情");
    private JButton btnpay = new JButton("去支付");

    private Object tblTitle[] = {"original amount", "settle amount ", "coupon", "ordertime", "orderstatus"};
    private Object tblData[][];
    List<BeanOrder> pubs;

    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy--MM--dd");
        pubs = (new OrderManager()).loadUserOrders();
        tblData = new Object[pubs.size()][5];
        for (int i = 0; i < pubs.size(); i++)
        {
            tblData[i][0] = pubs.get(i).getOri_amount() + "";
            tblData[i][1] = pubs.get(i).getSet_amount() + "";
            tblData[i][2] = pubs.get(i).getCouponid() + "";
            if(pubs.get(i).getOrder_time()==null||"".equals(pubs.get(i).getOrder_time()))
            {
                tblData[i][3]="";
            }
            else
            {
                tblData[i][3] = sdf.format(pubs.get(i).getOrder_time());
            }
            tblData[i][4] = pubs.get(i).getStatus();
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
    }

    public FrmHistory(Frame f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btndetail);
        toolBar.add(btnpay);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        this.btndetail.addActionListener(this);
        this.btnpay.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btndetail)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择order", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanOrder p = this.pubs.get(i);
            FrmTheDetail_Order mod = new FrmTheDetail_Order(this, "Detail", true, p);
            mod.setVisible(true);
        }
        else if (e.getSource() == this.btnpay)
        {

        }
    }
}
