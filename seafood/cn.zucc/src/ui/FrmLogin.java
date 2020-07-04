package ui;
import model.customer.BeanUser;
import starter.SeaFoodUtil;
import util.BaseException;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class FrmLogin extends JDialog implements ActionListener{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnLogin = new JButton("Log in");
    private JButton btnCancel = new JButton("cancel");
    private JButton btnRegister = new JButton("Sign up");
    private JButton btnAdmin = new JButton("Admin");
    private JLabel labelUser = new JLabel("username");

    private JLabel labelPwd = new JLabel("password");
    private JTextField edtUserId = new JTextField(20);
    private JPasswordField edtPwd = new JPasswordField(20);

    public FrmLogin(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnAdmin);
        toolBar.add(this.btnRegister);
        toolBar.add(btnLogin);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 140);
        // ��Ļ������ʾ
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        btnLogin.addActionListener(this);
        btnCancel.addActionListener(this);
        this.btnRegister.addActionListener(this);
        this.btnAdmin.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnLogin) {
            String userid=this.edtUserId.getText();
            String pwd=new String(this.edtPwd.getPassword());
            try {
                BeanUser.currentLoginUser= SeaFoodUtil.userManager.login(userid, pwd);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.setVisible(false);

        } else if (e.getSource() == this.btnCancel) {
            System.exit(0);
        } else if(e.getSource()==this.btnRegister){
            FrmRegister dlg=new FrmRegister(this,"Register",true);
            dlg.setVisible(true);
        }

        else if(e.getSource()==this.btnAdmin)
        {
            FrmAd ad=new FrmAd(this,"Admin",true);
            ad.setVisible(true);
        }
    }

}
