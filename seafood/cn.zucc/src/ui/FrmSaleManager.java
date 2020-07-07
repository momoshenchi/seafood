package ui;

import control.AdminManager;
import control.PromoteManager;
import model.promote.BeanSale;
import model.root.BeanPurchase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmSaleManager extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加促销");
    private Button btnModify = new Button("修改促销");
    private Button btnDelete = new Button("删除促销");

    private Object tblTitle[]={"saleid","commodityid","saleprice","maxnumber","start_date","end_date"};
    private Object tblData[][];
    List<BeanSale> pubs;


    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable(){
        pubs=(new PromoteManager()).loadAllSale();
        tblData =new Object[pubs.size()][6];
        for(int i=0;i<pubs.size();i++){
            tblData[i][0]=pubs.get(i).getSaleid()+"";
            tblData[i][1]=pubs.get(i).getCommodityid()+"";
            tblData[i][2]=pubs.get(i).getSaleprice()+"";
            tblData[i][3]=pubs.get(i).getMaxnumber()+"";
            tblData[i][4]=pubs.get(i).getStart_date();
            tblData[i][5]=pubs.get(i).getEnd_date();
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

        }
        else if(e.getSource()==this.btnModify)
        {

        }
        else if(e.getSource()==this.btnDelete)
        {

        }
    }
}
