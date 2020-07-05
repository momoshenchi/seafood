package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAdmin extends JFrame implements ActionListener {
    private JMenu menu_pur=new JMenu("采购管理");
    private JMenu menu_step=new JMenu("优惠管理");
//    private JMenu menu_static=new JMenu("查询统计");
//    private JMenu menu_more=new JMenu("更多");

    private JMenuItem menuItem_AddPlan=new JMenuItem("新建采购");
    private JMenuItem  menuItem_DeletePlan=new JMenuItem("删除采购");
    private JMenuItem  menuItem_AddStep=new JMenuItem("新建优惠");
    private JMenuItem  menuItem_DeleteStep=new JMenuItem("删除优惠");

    private JMenuBar menubar=new JMenuBar();

    public  FrmAdmin()
    {
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("admin");
        this.menu_pur.add(this.menuItem_AddPlan); this.menuItem_AddPlan.addActionListener(this);
	    this.menu_pur.add(this.menuItem_DeletePlan); this.menuItem_DeletePlan.addActionListener(this);
	    this.menu_step.add(this.menuItem_AddStep); this.menuItem_AddStep.addActionListener(this);
	    this.menu_step.add(this.menuItem_DeleteStep); this.menuItem_DeleteStep.addActionListener(this);
        menubar.add(menu_pur);
	    menubar.add(menu_step);
        this.setJMenuBar(menubar);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.menu_pur)
        {

        }
    }

//    public static void main(String[] args) {
//        new FrmAdmin();
//    }
}
