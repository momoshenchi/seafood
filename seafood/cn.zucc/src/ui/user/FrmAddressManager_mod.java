package ui.user;

import control.AddressManager;
import control.AdminManager;
import model.customer.BeanAddr;
import model.food.BeanMenu;
import util.BaseException;
import util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAddressManager_mod extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labeldetail_address = new JLabel("请输入详细地址");
    private JLabel labelcontactname = new JLabel("请输入联系人姓名");
    private JLabel labelphonenumber = new JLabel("请输入电话号码");

    private JTextField edtdetail_address = new JTextField(20);
    private JTextField edtcontactname = new JTextField(20);
    private JTextField edtphonenumber = new JTextField(20);
    private BeanAddr ba = null;

    public FrmAddressManager_mod(JDialog f, String s, boolean b, BeanAddr ba)
    {
        super(f, s, b);
        this.ba = ba;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labeldetail_address);
        edtdetail_address.setText(ba.getDetail_address());
        workPane.add(edtdetail_address);

        workPane.add(labelcontactname);
        edtcontactname.setText(ba.getContactname());
        workPane.add(edtcontactname);

        workPane.add(labelphonenumber);
        edtphonenumber.setText(ba.getPhonenumber());
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
            if (JOptionPane.showConfirmDialog(this, "确定修改吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                ba.setDetail_address(edtdetail_address.getText());
                ba.setPhonenumber(edtphonenumber.getText());
                ba.setContactname(edtcontactname.getText());
                try
                {
                    (new AddressManager()).modifyAddr(ba);
                }
                catch (BusinessException be)
                {
                    JOptionPane.showMessageDialog(null, be.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
                this.setVisible(false);

            }
        }
    }

    public String getDetail()
    {
        return edtdetail_address.getText();
    }
}
