package ui.root;

import control.AdminManager;
import control.PromoteManager;
import model.food.BeanCommodity;
import model.food.BeanType;
import model.promote.BeanCoupon;
import model.promote.BeanDiscount;
import ui.promote.FrmCouManager_mod;
import util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmTypeManager extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnAdd = new JButton("添加类别");
    private JButton btnModify = new JButton("修改类别");
    private JButton btnDelete = new JButton("删除类别");

    private Object tblTitle[]={"typeid","typename","description"};
    private Object tblData[][];
    List<BeanType> pubs;


    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable(){
        pubs=(new AdminManager()).loadAllType();
        tblData =new Object[pubs.size()][3];
        for(int i=0;i<pubs.size();i++){
            tblData[i][0]=pubs.get(i).getTypeid()+"";
            tblData[i][1]=pubs.get(i).getTypename();
            tblData[i][2]=pubs.get(i).getDescription();
        }
        tablmod.setDataVector(tblData,tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public  FrmTypeManager(Frame f, String s, boolean b)
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
            FrmTypeManager_add add=new FrmTypeManager_add(this,"add",true);
            add.setVisible(true);
            if(add.getTypename()!=null)
            {
                reloadTable();
            }
        }
        else if(e.getSource()==this.btnModify)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择type", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanType p = this.pubs.get(i);
            FrmTypeManager_mod mod = new FrmTypeManager_mod(this, "modify", true, p);
            mod.setVisible(true);
            if (mod.getDes() != null)
            {
                reloadTable();
            }
        }
        else if(e.getSource()==this.btnDelete)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择type", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanType p = this.pubs.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定删除吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                try
                {
                    (new AdminManager()).delType(p);
                }
                catch (BusinessException businessException)
                {
                    businessException.printStackTrace();
                }
                this.reloadTable();
            }
        }
    }
}
