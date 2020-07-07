package ui.root;

import control.AdminManager;
import control.PromoteManager;
import model.food.BeanCommodity;
import model.promote.BeanCoupon;
import model.root.BeanPurchase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmComManager extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnModify = new JButton("修改商品");
    private JButton btnDelete = new JButton("删除商品");

    private Object tblTitle[]={"commodityid","commodityname","price","vipprice","remain_number","spec","detail","picture","typeid"};
    private Object tblData[][];
    List<BeanCommodity> pubs;


    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable(){
        pubs=(new AdminManager()).loadAllCommodity();
        tblData =new Object[pubs.size()][9];
        for(int i=0;i<pubs.size();i++){
            tblData[i][0]=pubs.get(i).getCommodityid()+"";
            tblData[i][1]=pubs.get(i).getCommodityname();
            tblData[i][2]=pubs.get(i).getPrice()+"";
            tblData[i][3]=pubs.get(i).getVipprice()+"";
            tblData[i][4]=pubs.get(i).getRemain_number()+"";
            tblData[i][5]=pubs.get(i).getSpec();
            tblData[i][6]=pubs.get(i).getDetail();
            tblData[i][7]=pubs.get(i).getPicture();
            tblData[i][8]=pubs.get(i).getTypeid()+"";
        }
        tablmod.setDataVector(tblData,tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public  FrmComManager(Frame f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
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

        this.btnModify.addActionListener(this);
        this.btnDelete.addActionListener(this);


    }

    public void actionPerformed(ActionEvent e)
    {

        if(e.getSource()==this.btnModify)
        {

        }
        else if(e.getSource()==this.btnDelete)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择commodity", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanCommodity p = this.pubs.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定删除吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new AdminManager()).delCommodity(p);
                this.reloadTable();
            }
        }
    }
}
