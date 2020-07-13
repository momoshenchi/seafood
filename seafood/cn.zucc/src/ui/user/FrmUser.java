package ui.user;


import control.ReadImage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FrmUser extends JFrame implements ActionListener
{
    private Dimension frameSize = new Dimension(1600, 840);
    private ImageIcon imageIcon = ReadImage.read("12.jpg");

    private JMenu menu_info=new JMenu("个人信息");
    private JMenu menu_vip=new JMenu("充值VIP");
    private JMenu menu_coupon=new JMenu("商城活动");
    private JMenuItem menuItem_changer=new JMenuItem("更改密码");
    private JMenuItem menuItem_address=new JMenuItem("地址管理");
    private JMenuItem menuItem_info=new JMenuItem("个人信息");

    private JMenuItem menuItem_coupon=new JMenuItem("获得优惠券");
    private JMenuItem menuItem_vip=new JMenuItem("充值VIP");
    private JMenuBar menubar=new JMenuBar();


    private JButton Buttonmenu = new JButton("浏览菜谱");
    private JButton Buttonshop = new JButton("生鲜购买");
    private JButton Buttonhistory = new JButton("查看历史");
    private JButton buttoncart = new JButton("逛购物车");
    private JButton Buttonexit = new JButton("退出菜单");

    private JPanel panel = new JPanel();
    private JPanel panel2 = new JPanel();
    private Box b = Box.createVerticalBox();

    private Font font = new Font("宋体", Font.PLAIN, 40);

    class ImagePanel extends JPanel
    {
        Dimension d;
        Image image;

        public ImagePanel(Dimension d, Image image)
        {
            super();
            this.d = d;
            this.image = image;
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, d.width, d.height, this);
            this.repaint();
        }
    }

    public FrmUser()
    {
        // 设置窗体属性
//    setSize(frameSize);
//    setLocationRelativeTo(null);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("生鲜商超");
        setLayout(null);
        menuItem_changer.setFont(new Font("微软雅黑", Font.BOLD, 10));
        menuItem_address.setFont(new Font("微软雅黑", Font.BOLD, 10));
        menuItem_info .setFont(new Font("微软雅黑", Font.BOLD, 10));
        menuItem_vip.setFont(new Font("微软雅黑", Font.BOLD, 10));
        menuItem_coupon.setFont(new Font("微软雅黑", Font.BOLD, 10));

        menu_info.add(menuItem_address);menuItem_address.addActionListener(this);
        menu_info.add(menuItem_info);menuItem_info.addActionListener(this);
        menu_info.setFont(new Font("微软雅黑", Font.BOLD, 20));
        menu_vip.add(menuItem_vip);menuItem_vip.addActionListener(this);
        menu_vip.setFont(new Font("微软雅黑", Font.BOLD, 20));
        menu_coupon.add(menuItem_coupon);menuItem_coupon.addActionListener(this);
        menu_coupon.setFont(new Font("微软雅黑", Font.BOLD, 20));
        menubar.add(menu_info);
        menubar.add(menu_vip);
        menubar.add(menu_coupon);
        this.setJMenuBar(menubar);

        JLabel lbBg = new JLabel(imageIcon);
        lbBg.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.getContentPane().add(lbBg);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(imageIcon.getImage());
    }

    public void addImageByRepaint()
    {
        ImagePanel imagePanel = new ImagePanel(frameSize, imageIcon.getImage());
        setContentPane(imagePanel);
        addComponents();
        setVisible(true);
    }

    private void addComponents()
    {
//	b.setSize(800, 200);
// 怎么设置按钮大小
        b.add(Box.createVerticalStrut(320));
        Buttonmenu.setFont(new Font("微软雅黑", Font.BOLD, 25));
        b.add(Buttonmenu);
        this.Buttonmenu.addActionListener(this);
        b.add(Box.createVerticalStrut(50));
        Buttonshop.setFont(new Font("微软雅黑", Font.BOLD, 25));
        b.add(Buttonshop);
        this.Buttonshop.addActionListener(this);
        b.add(Box.createVerticalStrut(50));
        Buttonhistory.setFont(new Font("微软雅黑", Font.BOLD, 25));
        b.add(Buttonhistory);
        this.Buttonhistory.addActionListener(this);
        b.add(Box.createVerticalStrut(50));
        buttoncart.setFont(new Font("微软雅黑", Font.BOLD, 25));
        b.add(buttoncart);
        this.buttoncart.addActionListener(this);
        b.add(Box.createVerticalStrut(50));
        Buttonexit.setFont(new Font("微软雅黑", Font.BOLD, 25));
        b.add(Buttonexit);
        this.Buttonexit.addActionListener(this);
        b.setFont(font);
        this.getContentPane().add(b);
//    this.getContentPane().setFont(font);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.Buttonmenu)
        {
            FrmViewMenuRecommend fvm=new FrmViewMenuRecommend(this, "商品菜谱", true);
            fvm.setVisible(true);
        }
        else if (e.getSource() == this.Buttonshop)
        {
            new FrmShop();
        }
        else if (e.getSource() == this.Buttonhistory)
        {
            FrmHistory fh=new FrmHistory(this, "购物历史", true);
            fh.setVisible(true);
        }
        else if(e.getSource()==buttoncart)
        {
            FrmCart fc=new FrmCart(this, "购物车", true);
            fc.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_address)
        {
            FrmAddressManager fam=new FrmAddressManager(this, "个人地址", true);
            fam.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_info)
        {
            FrmInfo fi = new FrmInfo(this, "个人信息", true);
            fi.setVisible(true);
        }
        else if (e.getSource() == this.Buttonexit)
        {
            System.exit(0);
        }
        else if(e.getSource()==menuItem_vip)
        {
            FrmVIP fv=new FrmVIP(this, "VIP", true);
            fv.setVisible(true);
        }
        else if(e.getSource()==menuItem_coupon)
        {
            FrmGetCoupon fgc=new FrmGetCoupon(this, "Coupon", true);
            fgc.setVisible(true);
        }
    }

    public static void main(String[] args)
    {
        FrmUser imageFrame = new FrmUser();
        imageFrame.addImageByRepaint();
    }
}