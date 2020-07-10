package ui;

import ui.promote.FrmCouManager;
import ui.promote.FrmDisRuleManager;
import ui.promote.FrmDiscountManager;
import ui.promote.FrmSaleManager;
import ui.root.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAdmin extends JFrame implements ActionListener {
    private JMenu menu_pur=new JMenu("商品管理");
    private JMenu menu_step=new JMenu("优惠管理");
//    private JMenu menu_static=new JMenu("查询统计");
//    private JMenu menu_more=new JMenu("更多");

    private JMenuItem menuItem_purManager=new JMenuItem("采购管理");
    private JMenuItem menuItem_comManager=new JMenuItem("商品管理");
    private JMenuItem menuItem_typeManager=new JMenuItem("类别管理");
    private JMenuItem menuItem_menuManager=new JMenuItem("菜谱管理");
    private JMenuItem menuItem_menucom=new JMenuItem("菜谱推荐");

    private JMenuItem  menuItem_coupon=new JMenuItem("优惠券管理");
    private JMenuItem  menuItem_sale=new JMenuItem("促销管理");
    private JMenuItem  menuItem_discountrule=new JMenuItem("折扣规则管理");
    private JMenuItem  menuItem_discount=new JMenuItem("折扣管理");
    private JMenuBar menubar=new JMenuBar();

    public  FrmAdmin()
    {
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("admin");
        menuItem_purManager.setFont(new Font("微软雅黑", Font.BOLD, 16));
        menuItem_comManager .setFont(new Font("微软雅黑", Font.BOLD, 16));
        menuItem_typeManager.setFont(new Font("微软雅黑", Font.BOLD, 16));
        menuItem_discountrule.setFont(new Font("微软雅黑", Font.BOLD, 16));
        menuItem_menucom.setFont(new Font("微软雅黑", Font.BOLD, 16));
        menuItem_menuManager.setFont(new Font("微软雅黑", Font.BOLD, 16));
        menuItem_discount.setFont(new Font("微软雅黑", Font.BOLD, 16));
        menuItem_coupon.setFont(new Font("微软雅黑", Font.BOLD, 16));
        menuItem_sale.setFont(new Font("微软雅黑", Font.BOLD, 16));
        this.menu_pur.add(this.menuItem_purManager); this.menuItem_purManager.addActionListener(this);
        this.menu_pur.add(this.menuItem_comManager);this.menuItem_comManager.addActionListener(this);
        this.menu_pur.add(this.menuItem_typeManager);this.menuItem_typeManager.addActionListener(this);
        this.menu_pur.add(this.menuItem_menuManager);this.menuItem_menuManager.addActionListener(this);
        this.menu_pur.add(this.menuItem_menucom);this.menuItem_menucom.addActionListener(this);
	    this.menu_step.add(this.menuItem_discountrule); this.menuItem_discountrule.addActionListener(this);
        this.menu_step.add(this.menuItem_discount); this.menuItem_discount.addActionListener(this);
	    this.menu_step.add(this.menuItem_coupon); this.menuItem_coupon.addActionListener(this);
	    this.menu_step.add(this.menuItem_sale); this.menuItem_sale.addActionListener(this);
	    menu_pur.setFont(new Font("微软雅黑", Font.BOLD, 16));
	    menu_step.setFont(new Font("微软雅黑", Font.BOLD, 16));
        menubar.add(menu_pur);
	    menubar.add(menu_step);
        this.setJMenuBar(menubar);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        this.setResizable(true);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.menuItem_purManager)
        {
            FrmPurManager fpm = new FrmPurManager(this,"采购管理",true);
            fpm.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_comManager)
        {
            FrmComManager cm= new FrmComManager(this,"商品管理",true);
            cm.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_typeManager)
        {
            FrmTypeManager ftp=new FrmTypeManager(this,"类别管理",true);
            ftp.setVisible(true);

        }
        else if(e.getSource()==this.menuItem_coupon)
        {
            FrmCouManager fcm =new FrmCouManager(this,"优惠券管理",true);
            fcm.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_sale)
        {
            FrmSaleManager fsm =new FrmSaleManager(this,"促销管理",true);
            fsm.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_discountrule)
        {
            FrmDisRuleManager fdr=new FrmDisRuleManager(this,"折扣规则管理",true);
            fdr.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_discount)
        {
            FrmDiscountManager fdm=new FrmDiscountManager(this,"折扣管理",true);
            fdm.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_menuManager)
        {
            FrmMenuManager fmm=new FrmMenuManager(this,"菜谱管理",true);
            fmm.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_menucom)
        {
            FrmMenuCommodity fmc=new FrmMenuCommodity(this,"推荐管理",true);
            fmc.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new FrmAdmin();
    }
}
