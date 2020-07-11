package ui.root;

import control.AdminManager;
import control.PromoteManager;
import model.food.BeanCommodity;
import model.food.BeanMenu;
import model.promote.BeanCoupon;
import model.root.BeanPurchase;
import ui.promote.FrmCouManager_mod;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmMenuManager extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnAdd = new JButton("增加菜谱");
    private JButton btnModify = new JButton("修改菜谱");
    private JButton btnDelete = new JButton("删除菜谱");

    private Object tblTitle[]={"menuid","menuname","ingredient","step","picture"};
    private Object tblData[][];
    List<BeanMenu> pubs;


    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable()
    {
        pubs = (new AdminManager()).loadAllMenu();
        tblData = new Object[pubs.size()][5];
        for (int i = 0; i < pubs.size(); i++)
        {
            tblData[i][0] = pubs.get(i).getMenuid() + "";
            tblData[i][1] = pubs.get(i).getMenuname();
            tblData[i][2] = pubs.get(i).getIngredient();
            tblData[i][3] = pubs.get(i).getStep();
            tblData[i][4] = pubs.get(i).getPicture();
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
    }
    public  FrmMenuManager(Frame f, String s,boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnModify);
        toolBar.add(btnDelete);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.setResizable(false);
        this.btnAdd.addActionListener(this);
        this.btnModify.addActionListener(this);
        this.btnDelete.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
    	if(e.getSource()==this.btnAdd)
    	{
            FrmMenuManager_add add=new FrmMenuManager_add(this,"add",true);
             add.setVisible(true);
             if(add.getMenuname()!=null)
             {
                 reloadTable();
             }
    	}
        else if(e.getSource()==this.btnModify)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择menu", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanMenu p = this.pubs.get(i);
            FrmMenuManager_mod mod = new FrmMenuManager_mod(this, "modify", true, p);
            mod.setVisible(true);
            if (mod.getIngredient() != null)
            {
                reloadTable();
            }
        }
        else if(e.getSource()==this.btnDelete)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择menu", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanMenu p = this.pubs.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定删除吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new AdminManager()).delMenu(p);
                this.reloadTable();
            }
        }
    }
}
