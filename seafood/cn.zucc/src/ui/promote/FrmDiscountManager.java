package ui.promote;

import control.PromoteManager;
import model.promote.BeanComDiscount;
import model.promote.BeanDiscount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmDiscountManager  extends JDialog implements ActionListener
{private JPanel toolBar = new JPanel();
    private JButton btnAdd = new JButton("添加折扣");
    private JButton btnDelete = new JButton("删除折扣");

    private Object tblTitle[]={"discountid","commodityid","start_date","end_date"};
    private Object tblData[][];
    List<BeanComDiscount> pubs;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);
    private void reloadTable(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy--MM--dd");
        pubs=(new PromoteManager()).loadAllDiscount();
        tblData =new Object[pubs.size()][4];
        for(int i=0;i<pubs.size();i++){
            tblData[i][0]=pubs.get(i).getDiscountid()+"";
            tblData[i][1]=pubs.get(i).getCommodityid();
            tblData[i][2]=sdf.format(pubs.get(i).getStart_date());
            tblData[i][3]=sdf.format(pubs.get(i).getEnd_date());
        }
        tablmod.setDataVector(tblData,tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }
    public  FrmDiscountManager(Frame f, String s, boolean b)
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
        this.setResizable(false);
        this.btnAdd.addActionListener(this);
        this.btnDelete.addActionListener(this);

    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==this.btnAdd)
        {
            FrmDIscountManager_add fdrm=new FrmDIscountManager_add(this,"add",true);
            fdrm.setVisible(true);
            if(fdrm.getDiscountid()!=0)
            {
                reloadTable();
            }
        }
        else if(e.getSource()==this.btnDelete)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择discount", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanComDiscount p = this.pubs.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定删除吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new PromoteManager()).delDiscount(p);
                this.reloadTable();
            }
        }
    }
}
