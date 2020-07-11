package ui.user;

import control.AddressManager;
import control.AdminManager;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAddressManager_add  extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labeldetail_address = new JLabel("detail_address");
    private JLabel labelcontactname = new JLabel("contactname ");
    private JLabel labelphonenumber = new JLabel("phonenumber");

    private JTextField edtdetail_address = new JTextField(20);
    private JTextField edtcontactname = new JTextField(20);
    private JTextField edtphonenumber = new JTextField(20);



    public FrmAddressManager_add(JDialog f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labeldetail_address);
        workPane.add(edtdetail_address);
        workPane.add(labelcontactname);
        workPane.add(edtcontactname);
        workPane.add(labelphonenumber);
        workPane.add(edtphonenumber);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(280, 280);
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
            try
            {
                (new AddressManager()).addAddr(edtdetail_address.getText(),edtcontactname.getText(),edtphonenumber.getText());
                this.setVisible(false);
            }
            catch (BaseException e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getDetail()
    {
        return edtdetail_address.getText();
    }
}
