package ui.user;

import control.AdminManager;
import control.PromoteManager;
import control.UserManager;
import model.customer.BeanUser;
import model.food.BeanCommodity;
import model.promote.BeanCoupon;
import model.root.BeanAdmin;
import ui.promote.FrmCouManager_add;
import ui.promote.FrmCouManager_mod;
import ui.root.FrmComManager_mod;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmInfo extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnModify = new JButton("修改个人信息");
    private Box box = Box.createVerticalBox();
    BeanUser pubs;
    private void add()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy--MM--dd");
        pubs = (new UserManager()).loadUserSelf();
        loop("用户名 :", pubs.getUsername());
        loop("性别 :", pubs.getSex());
        loop("电话号码 :", pubs.getPhonenumber());
        loop("电子邮箱 :", pubs.getMail());
        loop("所在城市 :", pubs.getCity());
        loop("注册时间 :", sdf.format(pubs.getRegister_time()));
        loop("是否为VIP :", pubs.getIsvip());
        if ("true".equals(pubs.getIsvip()))
            loop("VIP到期时间 :",sdf.format(pubs.getVipendtime()));
        else
            loop("VIP到期时间 :","无");
    }

    public void loop(String s1, String s2)
    {
        JLabel tem = new JLabel(s1);
        JPanel jp1 = new JPanel();
        tem.setFont(new Font("微软雅黑", Font.BOLD, 18));
        jp1.add(tem);
        tem = new JLabel(s2);
        tem.setFont(new Font("微软雅黑", Font.BOLD, 18));
        jp1.add(tem);
        box.add(jp1);
    }

    public FrmInfo(Frame f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnModify);
        btnModify.setFont(new Font("微软雅黑", Font.BOLD, 16));
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.add();
        this.getContentPane().add(box, BorderLayout.CENTER);
        this.setSize(400, 500);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.btnModify.addActionListener(this);
        this.validate();
        this.setResizable(false);

    }

    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == this.btnModify)
        {
            BeanUser p = this.pubs;
            FrmInfo_mod mod = new FrmInfo_mod(this, "modify", true, p);
            mod.setVisible(true);
            if (mod.getSex() != null)
            {

            }
        }
    }
}
