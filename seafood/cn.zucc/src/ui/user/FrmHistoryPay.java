package ui.user;

import control.AddressManager;
import control.OrderManager;
import control.UserManager;
import model.customer.BeanAddr;
import model.customer.BeanOrder;
import model.promote.BeanCoupon;
import util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


//此类和FrmPay几乎一样
public class FrmHistoryPay extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnDelete = new JButton("放弃支付");
    private JButton btnpay = new JButton("付款");
    private Box box = Box.createVerticalBox();
    private JPanel jmain = new JPanel();
    private JPanel fin = new JPanel();
    JComboBox coupon = new JComboBox();
    JComboBox address = new JComboBox();
    private JLabel labeldate = new JLabel("请输入要求送达时间:");
    private JTextField edtdate = new JTextField(15);

    BeanOrder bo;
    List<BeanAddr> ba;
    List<BeanCoupon> bc;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    public FrmHistoryPay(JDialog f, String s, boolean b, BeanOrder bo)
    {
        super(f, s, b);
        this.bo = bo;
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnDelete);
        btnDelete.setFont(new Font("微软雅黑", Font.BOLD, 16));
        toolBar.add(btnpay);
        btnpay.setFont(new Font("微软雅黑", Font.BOLD, 16));
        coupon.addItem("--请选择优惠券--");
        address.addItem("--请选择详细地址--");
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        bc = (new UserManager()).loadUserCoupon();
        for (int i = 0; i < bc.size(); ++i)
        {
            coupon.addItem(bc.get(i).getDetail() + " 起始金额: " + bc.get(i).getStart_price() + "元  减 " + bc.get(i).getSub_price() + "元");
        }
        ba = (new AddressManager()).loadAllUserAddr();
        fin.add(coupon);
        for (int i = 0; i < ba.size(); ++i)
        {
            address.addItem(ba.get(i).getDetail_address() + " 联系人 " + ba.get(i).getContactname() +
                    " 号码 " + ba.get(i).getPhonenumber());
        }
        box.add(fin);
        fin = new JPanel();
        fin.add(address);
        box.add(fin);
        addT(bo);
        fin = new JPanel();
        labeldate.setFont(new Font("微软雅黑", Font.BOLD, 18));
        fin.add(labeldate);
        fin.add(edtdate);
        box.add(fin);

        this.getContentPane().add(box, BorderLayout.CENTER);
        this.setSize(500, 340);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.setResizable(false);
        this.validate();
        this.btnDelete.addActionListener(this);
        this.btnpay.addActionListener(this);
    }

    public void addT(BeanOrder bo)
    {
        fin = new JPanel();
        JLabel m1 = new JLabel("预期金额 :");
        m1.setFont(new Font("微软雅黑", Font.BOLD, 18));
        JLabel m2 = new JLabel("实际金额 :");
        m2.setFont(new Font("微软雅黑", Font.BOLD, 18));
        JLabel jLabel = new JLabel(String.format("%.2f", bo.getOri_amount()) + " 元");
        jLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        JLabel jLabel2 = new JLabel(String.format("%.2f", bo.getSet_amount())+ " 元");
        jLabel2.setFont(new Font("微软雅黑", Font.BOLD, 18));
        fin.add(m1);
        fin.add(jLabel);
        box.add(fin);
        fin = new JPanel();
        fin.add(m2);
        fin.add(jLabel2);
        box.add(fin);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnDelete)
        {
            if (JOptionPane.showConfirmDialog(this, "确定放弃吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new OrderManager()).delPay(bo);
                this.setVisible(false);
            }
        }
        else if (e.getSource() == this.btnpay)
        {
            if (JOptionPane.showConfirmDialog(this, "确定支付吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                int couponid = 0;
                int addressid = 0;
                if (coupon.getSelectedIndex() != 0)
                {
                    couponid = bc.get(coupon.getSelectedIndex() - 1).getCouponid();
                }
                if (address.getSelectedIndex() != 0)
                {
                    addressid = ba.get(address.getSelectedIndex() - 1).getAddressid();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "请在主页输入地址", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try
                {
                    (new OrderManager()).pay(bo, couponid, edtdate.getText(), addressid);
                    JOptionPane.showMessageDialog(null, "支付成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (BusinessException e2)
                {
                    JOptionPane.showMessageDialog(null, e2.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }

                this.setVisible(false);
            }
        }
    }
}
