package ui.root;

import control.AdminManager;
import control.PromoteManager;
import model.food.BeanCommodity;
import model.root.BeanPurchase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import util.BaseException;
import util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmPurManager extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnAdd = new JButton("添加采购");
    private JButton btnModifyrev = new JButton("设置入库");
    private JButton btnModifyput = new JButton("设置上架");
    private JButton btnAddcom = new JButton("新货上市");
    private Object tblTitle[]={"purchaseid","commodityid","number","status","adminid","commodityname","purchasedate"};
    private Object tblData[][];
    List<BeanPurchase> pubs;


    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy--MM--dd");
        pubs=(new AdminManager()).loadAllPurchase();
        tblData =new Object[pubs.size()][7];
        for(int i=0;i<pubs.size();i++){
            tblData[i][0]=pubs.get(i).getPurchaseid()+"";
            tblData[i][1]=pubs.get(i).getCommodityid()+"";
            tblData[i][2]=pubs.get(i).getNumber()+"";
            tblData[i][3]=pubs.get(i).getStatus();
            tblData[i][4]=pubs.get(i).getAdminid()+"";
            tblData[i][5]=pubs.get(i).getCommodityname();
            tblData[i][6]=sdf.format(pubs.get(i).getPurchasedate());
        }
        tablmod.setDataVector(tblData,tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public  FrmPurManager(Frame f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnAdd.setFont(new Font("微软雅黑", Font.BOLD, 16));
        btnAddcom.setFont(new Font("微软雅黑", Font.BOLD, 16));
        btnModifyrev.setFont(new Font("微软雅黑", Font.BOLD, 16));
        btnModifyput.setFont(new Font("微软雅黑", Font.BOLD, 16));
        toolBar.add(btnAdd);
        toolBar.add(btnModifyrev);
        toolBar.add(btnModifyput);
        toolBar.add(btnAddcom);
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
        this.btnModifyput.addActionListener(this);
        this.btnModifyrev.addActionListener(this);
        this.btnAddcom.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==this.btnAdd)
        {
            FrmPurManager_add add=new FrmPurManager_add(this,"add",true);
            add.setVisible(true);
            if(add.getCommodityname()!=null)
            {
                reloadTable();
            }
        }
        else if(e.getSource()==this.btnModifyrev)
        {
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanPurchase p=this.pubs.get(i);
            try
            {
                (new AdminManager()).modifyPurchase(p);
            }
            catch (BusinessException businessException)
            {
                businessException.printStackTrace();
            }
            this.reloadTable();
        }
        else if(e.getSource()==this.btnModifyput)
        {
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanPurchase p=this.pubs.get(i);
            try
            {
                (new AdminManager()).modifyComNum(p);
            }
            catch (BusinessException be)
            {
                be.printStackTrace();
            }
            this.reloadTable();

        }
        else if(e.getSource()==this.btnAddcom)
        {
            FrmComManager_add cadd=new FrmComManager_add(this,"add",true);
            cadd.setVisible(true);
            if(cadd.getCommodityname()!=null)
            {
                reloadTable();
            }
        }

    }

}
