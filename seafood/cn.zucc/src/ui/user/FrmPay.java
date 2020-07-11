package ui.user;

import control.AddressManager;
import control.OrderManager;
import control.UserManager;
import model.customer.BeanAddr;
import model.customer.BeanCart;
import model.customer.BeanDetailOrder;
import model.customer.BeanOrder;
import model.promote.BeanCoupon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmPay extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnDelete = new JButton("放弃支付");
    private JButton btnpay = new JButton("付款");

    private JPanel jmain = new JPanel();
    private JPanel fin = new JPanel();
    private Object tblTitle[] = {"Commodityname", "number", "price", "vipprice"};
    private Object tblData[][];
    JComboBox coupon=new JComboBox();
    JComboBox address=new JComboBox();

    private JLabel labeldate = new JLabel("Input Order time");
    private JTextField edtdate = new JTextField(20);

    BeanOrder bo;
    List<BeanAddr> ba;
    List<BeanCoupon> bc;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    public FrmPay(JDialog f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnDelete);
        toolBar.add(btnpay);
        coupon.addItem("--请选择优惠券--");
        address.addItem("--请选择详细地址--");
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        bc=(new UserManager()).loadUserCoupon();
        for(int i=0;i<bc.size();++i)
        {
            coupon.addItem(bc.get(i).getDetail()+" 减 "+bc.get(i).getSub_price()+ "元");
        }
        bo = (new OrderManager()).loadCurrentOrder();
        ba = (new AddressManager()).loadAllUserAddr();
        fin.add(coupon);
        for(int i=0;i<ba.size();++i)
        {
            address.addItem(ba.get(i).getDetail_address()+" 联系人 "+ba.get(i).getContactname()+
                    " 号码"+ba.get(i).getPhonenumber());
        }
        fin.add(address);
        addT(bo);
        fin.add(labeldate);
        fin.add(edtdate);

//        this.add(jmain,BorderLayout.NORTH);
        this.add(fin,BorderLayout.CENTER);


        this.setSize(500, 300);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.setResizable(false);
        this.validate();
        this.btnDelete.addActionListener(this);
        this.btnpay.addActionListener(this);
    }
    public  void addT(BeanOrder bo)
    {
        JLabel m1=new JLabel("预期金额");
        JLabel m2=new JLabel("实际金额");

        JLabel jLabel=new JLabel(bo.getOri_amount()+"");
        JLabel jLabel2=new JLabel(bo.getSet_amount()+"");
        fin.add(m1);
        fin.add(jLabel);
        fin.add(m2);
        fin.add(jLabel2);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnDelete)
        {
            if (JOptionPane.showConfirmDialog(this, "确定放弃吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new OrderManager()).delPay(bo);
            }
        }
        else if (e.getSource() == this.btnpay)
        {
            if (JOptionPane.showConfirmDialog(this, "确定支付吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                int couponid=0;
                int addressid=0;
                if(coupon.getSelectedIndex()!=0)
                {
                    couponid=bc.get(coupon.getSelectedIndex()-1).getCouponid();
                }
                if(address.getSelectedIndex()!=0)
                {
                    addressid=ba.get(address.getSelectedIndex()-1).getAddressid();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "请选择在主页输入地址", "提示", JOptionPane.ERROR_MESSAGE);
                    this.setVisible(false);
                }
                (new OrderManager()).pay(bo,couponid,edtdate.getText(),addressid);
                this.setVisible(false);
            }

        }
    }
}
