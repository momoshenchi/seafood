package ui.user;

import control.CommentManager;
import model.customer.BeanDetailOrder;
import model.customer.BeanOrder;
import util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmTheDetail_Order   extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btncmt = new JButton("添加评论");


    private Object tblTitle[]={"Commodityname","number","price","vipprice","discount","saleprice","orderstatus"};
    private Object tblData[][];
    List<BeanDetailOrder> pubs;
    BeanOrder bo;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable()
    {
        try
        {
            pubs = (new CommentManager()).loadTheDetail(bo);
        }
        catch (BusinessException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
        tblData = new Object[pubs.size()][7];
        for (int i = 0; i < pubs.size(); i++)
        {
            tblData[i][0] = pubs.get(i).getCommodityname();
            tblData[i][1] = pubs.get(i).getNumber() + "";
            tblData[i][2] = pubs.get(i).getPrice()+"";
            tblData[i][3] = pubs.get(i).getVipprice()+"";
            tblData[i][4] = pubs.get(i).getDiscount()+"";
            tblData[i][5] = pubs.get(i).getSaleprice()+"";
            tblData[i][6] = pubs.get(i).getOrderstatus();
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
    }
    public FrmTheDetail_Order(JDialog f, String s, boolean b, BeanOrder bo)
    {
        super(f,s,b);
        this.bo=bo;
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btncmt);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(600, 400);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.setResizable(false);
        this.validate();
        this.btncmt.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btncmt)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择detail", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanDetailOrder p = this.pubs.get(i);
            FrmComment_add mod = new FrmComment_add(this, "Comment", true, p);
            mod.setVisible(true);
            if (mod.getComment() != null)
            {
                reloadTable();
            }
        }
    }
}
