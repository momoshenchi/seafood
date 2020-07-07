package ui;

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

    private JMenuItem  menuItem_coupon=new JMenuItem("优惠券管理");
    private JMenuItem  menuItem_sale=new JMenuItem("促销管理");
    private JMenuItem  menuItem_discount=new JMenuItem("折扣管理");
    private JMenuBar menubar=new JMenuBar();

    public  FrmAdmin()
    {
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("admin");
        this.menu_pur.add(this.menuItem_purManager); this.menuItem_purManager.addActionListener(this);
	    this.menu_step.add(this.menuItem_discount); this.menuItem_discount.addActionListener(this);
	    this.menu_step.add(this.menuItem_coupon); this.menuItem_coupon.addActionListener(this);
	    this.menu_step.add(this.menuItem_sale); this.menuItem_sale.addActionListener(this);
        menubar.add(menu_pur);
	    menubar.add(menu_step);
        this.setJMenuBar(menubar);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.menuItem_purManager)
        {
            FrmPurManager fpm = new FrmPurManager(this,"采购管理",true);
            fpm.setVisible(true);
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
        else if(e.getSource()==this.menuItem_discount)
        {

        }
    }

//    public static void main(String[] args) {
//        new FrmAdmin();
//    }
}
