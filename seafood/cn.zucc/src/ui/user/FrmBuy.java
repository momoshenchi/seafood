package ui.user;

import control.OrderManager;
import control.PromoteManager;
import control.UserManager;
import model.customer.BeanCart;
import model.customer.BeanDetailOrder;
import util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmBuy  extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel panel = new JPanel();
    private JButton btnpay = new JButton("支付");
    private Box box = Box.createVerticalBox();

    private String tblTitle[]={"Commodityname","Number","Price","Vipprice","Discount","Saleprice"};
    private Object tblData[][];
    List<BeanDetailOrder> pubs;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable()
    {
        try
        {
            pubs = (new OrderManager()).addDetailOrder();
        }
        catch (BusinessException e)
        {
            e.printStackTrace();
        }
        tblData = new Object[pubs.size()][6];
        for (int i = 0; i < pubs.size(); i++)
        {
            tblData[i][0] = pubs.get(i).getCommodityname();
            tblData[i][1] = pubs.get(i).getNumber()+"";
            tblData[i][2] = pubs.get(i).getPrice() + "";
            if(pubs.get(i).getVipprice()==0.0)
                tblData[i][3] =  "非VIP";
            else
                tblData[i][3] =pubs.get(i).getVipprice()+"";

            if(pubs.get(i).getDiscount()==0.0)
                tblData[i][4] =  "无折扣";
            else
                tblData[i][4] = pubs.get(i).getDiscount() + "";

            if(pubs.get(i).getSaleprice()==0.0)
                tblData[i][5] =  "无促销";
            else
                tblData[i][5] = pubs.get(i).getSaleprice() + "";
        }
        tablmod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public FrmBuy(JDialog f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnpay);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(600, 400);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        this.setResizable(false);
        this.btnpay.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {

        if(e.getSource()==this.btnpay)
        {
            FrmPay fp =new FrmPay(this,"选择支付",true);
            fp.setVisible(true);
        }

    }

}
