package ui.user;

import control.UserManager;
import model.customer.BeanCart;
import model.food.BeanCommodity;
import util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmShop_add  extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelcommodityname = new JLabel("Name");
    private JLabel labelnumber = new JLabel("Number");
    private JLabel labelprice = new JLabel("Price");
    private JLabel labelvipprice = new JLabel("Vipprice");

    private JTextField edtcommodityname = new JTextField(20);
    private JTextField edtnumber = new JTextField(20);
    private JTextField edtprice = new JTextField(20);
    private JTextField edtvipprice = new JTextField(20);
    private BeanCommodity bc = null;

    public FrmShop_add(JFrame f, String s, boolean b, BeanCommodity bc)
    {
        super(f, s, b);
        this.bc = bc;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelcommodityname);
        edtcommodityname.setText(bc.getCommodityname());
        edtcommodityname.setEnabled(false);
        workPane.add(edtcommodityname);

        workPane.add(labelnumber);
        workPane.add(edtnumber);

        workPane.add(labelprice);
        edtprice.setText(bc.getPrice()+"");
        edtprice.setEnabled(false);
        workPane.add(edtprice);
        workPane.add(labelvipprice);
        edtvipprice.setText(bc.getVipprice()+"");
        edtvipprice.setEnabled(false);
        workPane.add(edtvipprice);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 200);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.setResizable(false);
        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnCancel)
        {
            this.setVisible(false);
            return;
        }
        else if (e.getSource() == this.btnOk)
        {
            if (JOptionPane.showConfirmDialog(this, "确定加入购物车吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                if(edtnumber.getText() ==null ||"".equals(edtnumber.getText()))
                {
                    JOptionPane.showMessageDialog(null, "please input number", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try
                {
                    (new UserManager()).addCart(bc.getCommodityid(),Integer.parseInt(edtnumber.getText()),
                            bc.getPrice(),bc.getPrice());
                }
                catch (BusinessException be)
                {
                    JOptionPane.showMessageDialog(null, be.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
                }
                this.setVisible(false);

            }
        }
    }
}
