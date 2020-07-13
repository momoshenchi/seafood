package ui.user;

import control.AdminManager;
import control.UserManager;
import model.food.BeanType;
import ui.root.FrmTypeManager_add;
import ui.root.FrmTypeManager_mod;
import util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FrmVIP extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnAdd = new JButton("支付");

    private JPanel fin = new JPanel();
    private JLabel labeldate = new JLabel("请输入充值月份:");
    private JTextField edtdate = new JTextField(15);


    public FrmVIP(Frame f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnAdd);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        fin.add(labeldate);
        fin.add(edtdate);
        this.setSize(400, 200);
        this.getContentPane().add(fin, BorderLayout.CENTER);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        this.setResizable(false);
        this.btnAdd.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAdd)
        {
            if (edtdate.getText() == null || "".equals(edtdate.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input month", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "确定支付吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                try
                {
                    new UserManager().addUserVIP(Integer.parseInt(edtdate.getText()));
                }
                catch (BusinessException e2)
                {
                    JOptionPane.showMessageDialog(null, e2.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(null, "支付成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            }

        }

    }
}
