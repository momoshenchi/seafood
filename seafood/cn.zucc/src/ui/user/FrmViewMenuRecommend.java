package ui.user;

import control.AdminManager;
import model.food.BeanMenu;
import model.food.BeanUserRecommend;
import ui.root.FrmMenuManager_add;
import ui.root.FrmMenuManager_mod;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmViewMenuRecommend extends JDialog
{
    private JPanel toolBar = new JPanel();
    private JLabel j = new JLabel("Just Look and Collect ");
    private Object tblTitle[] = {"menuname", "ingredient", "commodityname", "price"};
    private Object tblData[][];
    List<BeanUserRecommend> pubs;

    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable()
    {
        pubs = (new AdminManager()).loadUserRecommend();
        tblData = new Object[pubs.size()][4];
        for (int i = 0; i < pubs.size(); i++)
        {
            tblData[i][0] = pubs.get(i).getMenuname();
            tblData[i][1] = pubs.get(i).getIngredient();
            tblData[i][2] = pubs.get(i).getCommodityname();
            tblData[i][3] = pubs.get(i).getPrice() + "";
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
    }

    public FrmViewMenuRecommend(Frame f, String s, boolean b)
    {
        super(f, s, b);
        this.reloadTable();
        j.setFont(new Font("微软雅黑", Font.ITALIC, 24));
        toolBar.add(j);
        this.getContentPane().add(j, BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        this.setResizable(false);
    }
}
