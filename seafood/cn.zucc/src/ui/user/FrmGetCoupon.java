package ui.user;

import control.UserManager;
import model.customer.BeanUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Random;

public class FrmGetCoupon extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnrom = new JButton("随机抽取一张优惠券");
    private JButton btnlook = new JButton("查看我拥有的优惠券");
    private JLabel tips=new JLabel("Tips: 可以无限次抽取哦");

    public FrmGetCoupon(Frame f, String s, boolean b)
    {
        super(f, s, b);
        tips.setFont(new Font("微软雅黑", Font.BOLD, 26));
        this.getContentPane().add(tips, BorderLayout.NORTH);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnrom);
        toolBar.add(btnlook);
        btnlook.setFont(new Font("微软雅黑", Font.BOLD, 18));
        btnrom.setFont(new Font("微软雅黑", Font.BOLD, 18));
        this.getContentPane().add(toolBar, BorderLayout.CENTER);

        this.setSize(300, 250);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        this.setResizable(false);
        btnrom.addActionListener(this);
        btnlook.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == this.btnrom)
        {
            Random a =new Random();
            if(a.nextInt(199)==92 || a.nextInt(199)==93)
            {
                (new UserManager()).getRandomCoupon();
                JOptionPane.showMessageDialog(null, "获得一张优惠券", "恭喜", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "运气并不是很好", "可惜", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if(e.getSource()==btnlook)
        {
            FrmMyCoupon fmc=new FrmMyCoupon(this,"My Coupon",true);
            fmc.setVisible(true);
        }
    }

}
