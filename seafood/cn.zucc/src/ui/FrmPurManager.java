package ui;

import control.AdminManager;
import control.PromoteManager;
import model.root.BeanPurchase;
import java.util.ArrayList;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmPurManager extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加采购");
    private Button btnModify = new Button("修改采购");
    private Button btnDelete = new Button("删除采购");

    private Object tblTitle[]={"purchaseid","commodityid","number","status","adminid","commodityname","purchasedate"};
    private Object tblData[][];
    List<BeanPurchase> pubs;


    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable(){
        pubs=(new AdminManager()).loadAllPurchase();
        tblData =new Object[pubs.size()][7];
        for(int i=0;i<pubs.size();i++){
            tblData[i][0]=pubs.get(i).getPurchaseid()+"";
            tblData[i][1]=pubs.get(i).getCommodityid()+"";
            tblData[i][2]=pubs.get(i).getNumber()+"";
            tblData[i][3]=pubs.get(i).getStatus();
            tblData[i][4]=pubs.get(i).getAdminid()+"";
            tblData[i][5]=pubs.get(i).getCommodityname();
            tblData[i][6]=pubs.get(i).getPurchasedate();
        }
        tablmod.setDataVector(tblData,tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public  FrmPurManager(Frame f, String s, boolean b)
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

        }
        else if(e.getSource()==this.btnModify)
        {

        }
        else if(e.getSource()==this.btnDelete)
        {

        }
    }

}
