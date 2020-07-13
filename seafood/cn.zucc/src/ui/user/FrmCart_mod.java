package ui.user;

import control.AddressManager;
import control.UserManager;
import model.customer.BeanAddr;
import model.customer.BeanCart;
import util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmCart_mod  extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelnumber = new JLabel("number");
    private JTextField edtnumber = new JTextField(20);
    private BeanCart bc = null;

    public FrmCart_mod(JDialog f, String s, boolean b, BeanCart bc)
    {
        super(f, s, b);
        this.bc = bc;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        btnOk.setFont(new Font("微软雅黑", Font.ITALIC, 12));
        toolBar.add(btnCancel);
       btnCancel .setFont(new Font("微软雅黑", Font.ITALIC, 12));
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelnumber);
        edtnumber.setText(bc.getNumber()+"");
        workPane.add(edtnumber);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 160);
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
            if (JOptionPane.showConfirmDialog(this, "确定修改吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                if(edtnumber.getText() ==null ||"".equals(edtnumber.getText()))
                {
                    JOptionPane.showMessageDialog(null, "please input number", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                bc.setNumber(Integer.parseInt(edtnumber.getText()));
                try
                {
                    (new UserManager()).modifyCart(bc);
                }
                catch (BusinessException be)
                {
                    JOptionPane.showMessageDialog(null, be.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
                this.setVisible(false);
            }
        }
    }

    public int getNum()
    {
        if(edtnumber.getText() ==null ||"".equals(edtnumber.getText()))
        {
            return  0;
        }
        return Integer.parseInt(edtnumber.getText());
    }
}
