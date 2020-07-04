package ui;

import model.customer.BeanUser;
import starter.SeaFoodUtil;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmRegister  extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("OK");
    private Button btnCancel = new Button("Cancel");

    private JLabel labelUser = new JLabel("username");
    private JLabel labelPwd = new JLabel("input  pwd");
    private JLabel labelPwd2 = new JLabel("repeat  pwd");
    private JLabel labelphonenum = new JLabel("phonenum ");
    private JLabel labelmail = new JLabel("input  mail");
    private JLabel labelcity = new JLabel("input  city");
    private JLabel labelsex = new JLabel("input  sex");
    private JTextField edtUsername = new JTextField(20);
    private JTextField edtsex = new JTextField(20);
    private JTextField edtcity = new JTextField(20);
    private JTextField edtmail = new JTextField(20);
    private JTextField edtphone = new JTextField(20);
    private JPasswordField edtPwd = new JPasswordField(20);
    private JPasswordField edtPwd2 = new JPasswordField(20);
    public FrmRegister(Dialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUsername);
        workPane.add(labelsex);
        workPane.add(edtsex);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        workPane.add(labelPwd2);
        workPane.add(edtPwd2);
        workPane.add(labelphonenum);
        workPane.add(edtphone);
        workPane.add(labelmail);
        workPane.add(edtmail);
        workPane.add(labelcity);
        workPane.add(edtcity);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 300);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            String username=new String(this.edtUsername.getText());
            String sex=new String(this.edtsex.getText());

            String pwd1=new String(this.edtPwd.getPassword());
            String pwd2=new String(this.edtPwd2.getPassword());
            String city=new String(this.edtcity.getText());
            String mail=new String(this.edtmail.getText());
            String phone =new String(this.edtphone.getText());
            try {
                BeanUser user= SeaFoodUtil.userManager.reg(username,sex,pwd1,pwd2,city,mail,phone);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
                return;
            }

        }


    }
}
