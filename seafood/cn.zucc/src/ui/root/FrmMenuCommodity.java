package ui.root;

import control.AdminManager;
import model.food.BeanMenu;
import model.food.BeanRecommend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmMenuCommodity extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnAdd = new JButton("增加推荐");
    private JButton btnDelete = new JButton("删除推荐");

    private Object tblTitle[]={"menuid","commodityid","description"};
    private Object tblData[][];
    List<BeanRecommend> pubs;


    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable()
    {
        pubs = (new AdminManager()).loadAllRecommend();
        tblData = new Object[pubs.size()][3];
        for (int i = 0; i < pubs.size(); i++)
        {
            tblData[i][0] = pubs.get(i).getCommodityid()+"";
            tblData[i][1] = pubs.get(i).getMenuid() + "";
            tblData[i][2] = pubs.get(i).getDescription();
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
    }
    public FrmMenuCommodity(Frame f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
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
        this.btnAdd.addActionListener(this);
        this.btnDelete.addActionListener(this);


    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==this.btnAdd)
        {
            FrmMenuCommodity_add add=new FrmMenuCommodity_add(this,"add",true);
            add.setVisible(true);
            if(add.getCommodity()!=0)
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
            BeanRecommend p = this.pubs.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定删除吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new AdminManager()).delMenuCommodity(p);
                this.reloadTable();
            }
        }
    }
}
