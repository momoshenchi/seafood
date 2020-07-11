package ui.Norm;

import model.customer.BeanUser;
import starter.SeaFoodUtil;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmModifyPassword extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("OK");
    private Button btnCancel = new Button("Cancel");

    private JLabel labelPwdOld = new JLabel("original pwd");
    private JLabel labelPwd = new JLabel("new pwd   ");
    private JLabel labelPwd2 = new JLabel("new pwd  ");
    private JPasswordField edtPwdOld = new JPasswordField(20);
    private JPasswordField edtPwd = new JPasswordField(20);
    private JPasswordField edtPwd2 = new JPasswordField(20);

    public FrmModifyPassword(Frame f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelPwdOld);
        workPane.add(edtPwdOld);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        workPane.add(labelPwd2);
        workPane.add(edtPwd2);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 180);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnCancel)
            this.setVisible(false);
        else if (e.getSource() == this.btnOk)
        {
            try
            {
                SeaFoodUtil.userManager.changePwd(BeanUser.currentLoginUser, new String(edtPwdOld.getPassword()), new String(edtPwd.getPassword()), new String(edtPwd2.getPassword()));
                this.setVisible(false);
            }
            catch (BaseException e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
