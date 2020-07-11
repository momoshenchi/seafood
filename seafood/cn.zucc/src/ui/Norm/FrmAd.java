package ui.Norm;

import model.root.BeanAdmin;
import starter.SeaFoodUtil;
import util.BaseException;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class FrmAd extends JDialog implements ActionListener
{
    private FrmLogin dlgLogin = null;
    private FrmAdmin ad = null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnLogin = new JButton("Log in");
    private JButton btnUser = new JButton("return user");
    private JLabel labelUser = new JLabel("adminname");
    private JLabel labelPwd = new JLabel("password ");

    private JTextField edtUserId = new JTextField(20);
    private JPasswordField edtPwd = new JPasswordField(20);

    public FrmAd(JFrame f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnLogin);
        toolBar.add(btnUser);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        this.getContentPane().add(workPane, BorderLayout.CENTER);


        this.setSize(320, 140);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();

        btnLogin.addActionListener(this);
        btnUser.addActionListener(this);
//        btnCancel.addActionListener(this);
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnLogin)
        {
            String adminname = this.edtUserId.getText();
            String pwd = new String(this.edtPwd.getPassword());
            try
            {
                BeanAdmin.currentadmin = SeaFoodUtil.adminManager.login(adminname, pwd);

            }
            catch (BaseException e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.setVisible(false);
            new FrmAdmin();
        }
        else if (e.getSource() == this.btnUser)
        {
            this.setVisible(false);
        }

    }
}