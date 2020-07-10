package ui.user;

import control.AdminManager;
import control.UserManager;
import model.customer.BeanUser;
import model.food.BeanCommodity;
import util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmInfo_mod  extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelusername = new JLabel("username");
    private JLabel labelsex = new JLabel("sex");
    private JLabel labelphonenumber = new JLabel("phone");
    private JLabel labelmail = new JLabel("mail");
    private JLabel labelcity = new JLabel("city");



    private JTextField edtusername = new JTextField(16);
    private JTextField edtsex = new JTextField(16);
    private JTextField edtphonenumber = new JTextField(16);
    private JTextField edtmail = new JTextField(16);
    private JTextField edtcity = new JTextField(16);


    private BeanUser bu=null;
    public FrmInfo_mod(JDialog f, String s, boolean b, BeanUser bu)
    {
        super(f, s, b);
        this.bu=bu;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelusername);
        edtusername.setText(bu.getUsername());
        edtusername.setEnabled(false);
        workPane.add(edtusername);

        workPane.add(labelsex);
        edtsex.setText(bu.getSex());
        workPane.add(edtsex);
        workPane.add(labelphonenumber);
        edtphonenumber.setText(bu.getPhonenumber());
        workPane.add(edtphonenumber);
        workPane.add(labelmail);
        edtmail.setText(bu.getMail());
        workPane.add(edtmail);
        workPane.add(labelcity);
        edtcity.setText(bu.getCity());
        workPane.add(edtcity);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(260, 180);
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

                bu.setPhonenumber(edtphonenumber.getText());
                bu.setMail(edtmail.getText());
                bu.setCity(edtcity.getText());
                bu.setSex(edtsex.getText());

                (new UserManager()).modifyUserInfo(bu);
                this.setVisible(false);
            }
        }

    }

    public String getSex()
    {
        return edtsex.getText();
    }
}
