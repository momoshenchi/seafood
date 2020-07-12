package ui.user;

import control.AddressManager;
import control.AdminManager;
import model.customer.BeanAddr;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmAddressManager  extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnAdd = new JButton("增加地址");
    private JButton btnModify = new JButton("修改地址");
    private JButton btnDelete = new JButton("删除地址");

    private Object tblTitle[]={"detail_address","contactname","phonenumber"};
    private Object tblData[][];
    List<BeanAddr> pubs;


    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable()
    {
        pubs = (new AddressManager()).loadAllUserAddr();
        tblData = new Object[pubs.size()][3];
        for (int i = 0; i < pubs.size(); i++)
        {
            tblData[i][0] = pubs.get(i).getDetail_address();
            tblData[i][1] = pubs.get(i).getContactname();
            tblData[i][2] = pubs.get(i).getPhonenumber();
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
    }
    public  FrmAddressManager(Frame f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        btnAdd .setFont(new Font("微软雅黑", Font.BOLD, 18));
        toolBar.add(btnModify);
        btnModify.setFont(new Font("微软雅黑", Font.BOLD, 18));
        toolBar.add(btnDelete);
        btnDelete.setFont(new Font("微软雅黑", Font.BOLD, 18));
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
            FrmAddressManager_add add=new FrmAddressManager_add(this,"add",true);
            add.setVisible(true);
            if(add.getDetail()!=null)
            {
                reloadTable();
            }
        }
        else if(e.getSource()==this.btnModify)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择地址", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanAddr p = this.pubs.get(i);
            FrmAddressManager_mod mod = new FrmAddressManager_mod(this, "modify", true, p);
            mod.setVisible(true);
            if (mod.getDetail() != null)
            {
                reloadTable();
            }
        }
        else if(e.getSource()==this.btnDelete)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择地址", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanAddr p = this.pubs.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定删除吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new AddressManager()).delAddr(p);
                this.reloadTable();
            }
        }
    }
}
