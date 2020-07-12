package ui.user;

import control.AdminManager;
import control.UserManager;
import model.customer.BeanAddr;
import model.customer.BeanCart;
import model.food.BeanRecommend;
import model.food.BeanUserRecommend;
import ui.root.FrmMenuCommodity_add;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmCart extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnMod = new JButton("修改商品数量");
    private JButton btnDelete = new JButton("删除商品");
    private JButton btnbuy = new JButton("购买");


    private Object tblTitle[]={"Commodityname","number","price","vipprice"};
    private Object tblData[][];
    List<BeanCart> pubs;

    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable()
    {
        pubs = (new UserManager()).loadUserCart();
        tblData = new Object[pubs.size()][4];
        for (int i = 0; i < pubs.size(); i++)
        {
            tblData[i][0] = pubs.get(i).getCommodityname();
            tblData[i][1] = pubs.get(i).getNumber() + "";
            tblData[i][2] = pubs.get(i).getPrice()+"";
            tblData[i][3] = pubs.get(i).getVipprice()+"";
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
    }
    public FrmCart(JFrame f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnMod);
        btnMod.setFont(new Font("微软雅黑", Font.BOLD, 18));
        toolBar.add(btnDelete);
        btnDelete.setFont(new Font("微软雅黑", Font.BOLD, 18));
        toolBar.add(btnbuy);
       btnbuy .setFont(new Font("微软雅黑", Font.BOLD, 18));
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.setResizable(false);
        this.validate();
        this.btnMod.addActionListener(this);
        this.btnDelete.addActionListener(this);
        this.btnbuy.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnMod)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择commodity", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanCart p = this.pubs.get(i);
            FrmCart_mod mod = new FrmCart_mod(this, "modify", true, p);
            mod.setVisible(true);
            if (mod.getNum() != 0)
            {
                reloadTable();
            }
        }
        else if (e.getSource() == this.btnDelete)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择commodity", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanCart p = this.pubs.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定删除吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new UserManager()).delCart(p);
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnbuy)
        {
            FrmBuy fb=new FrmBuy(this, "订单详情", true);
            fb.setVisible(true);
        }
    }
}
