package ui.promote;

import control.PromoteManager;
import model.promote.BeanCoupon;
import model.promote.BeanDiscount;
import model.promote.BeanSale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmSaleManager extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnAdd = new JButton("添加促销");
    private JButton btnModify = new JButton("修改促销");
    private JButton btnDelete = new JButton("删除促销");

    private Object tblTitle[]={"saleid","commodityid","saleprice","maxnumber","start_date","end_date"};
    private Object tblData[][];
    List<BeanSale> pubs;


    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy--MM--dd");
        pubs=(new PromoteManager()).loadAllSale();
        tblData =new Object[pubs.size()][6];
        for(int i=0;i<pubs.size();i++){
            tblData[i][0]=pubs.get(i).getSaleid()+"";
            tblData[i][1]=pubs.get(i).getCommodityid()+"";
            tblData[i][2]=pubs.get(i).getSaleprice()+"";
            tblData[i][3]=pubs.get(i).getMaxnumber()+"";
            tblData[i][4]=sdf.format(pubs.get(i).getStart_date());
            tblData[i][5]=sdf.format(pubs.get(i).getEnd_date());
        }
        tablmod.setDataVector(tblData,tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public  FrmSaleManager(Frame f, String s, boolean b)
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

        this.btnAdd.addActionListener(this);
        this.btnModify.addActionListener(this);
        this.btnDelete.addActionListener(this);


    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==this.btnAdd)
        {
                FrmSaleManager_add add=new FrmSaleManager_add(this,"add",true);
                add.setVisible(true);
                if(add.getComid()!=0)
                {
                    reloadTable();
                }
        }
        else if(e.getSource()==this.btnModify)
        {
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择sale","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanSale p=this.pubs.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定修改吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new PromoteManager()).modifySale(p);
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnDelete)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择sale", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanSale p = this.pubs.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定删除吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new PromoteManager()).delSale(p);
                this.reloadTable();
            }
        }
    }
}
