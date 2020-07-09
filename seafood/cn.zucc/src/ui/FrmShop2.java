package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.PromoteManager;
import control.ReadImage;
import model.promote.BeanCoupon;

public class FrmShop2 extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel rightBar = new JPanel();
    private JButton btnAdd = new JButton("添加购物车");
    private JButton btnModify = new JButton("修改");
    private JButton btnDelete = new JButton("删除");

    private Object tblTitle[] = {"description", "picture"};
    private Object tblData[][];
    List<BeanCoupon> pubs;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy--MM--dd");
//	        pubs = (new PromoteManager()).loadAllCoupon();
        tblData = new Object[2][2];
        for (int i = 0; i < 2; i++)
        {
            tblData[i][0] = i+"";
            tblData[i][1] = i + "";

        }
        tablmod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public JPanel readIcon(String name)
    {

        ImageIcon icon = ReadImage.read(name);
        JLabel p1 = new JLabel(icon);
        JPanel p = new JPanel();
        p.add(p1);
        p.setSize(40,40);
        return p;
    }

    public FrmShop2()
    {
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnModify);
        toolBar.add(btnDelete);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        rightBar.add(btnAdd);

        this.getContentPane().add(readIcon("12.jpg"),BorderLayout.WEST);
        this.getContentPane().add(rightBar,BorderLayout.EAST);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(1200, 800);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnAdd.addActionListener(this);
        this.btnModify.addActionListener(this);
        this.btnDelete.addActionListener(this);
        this.setVisible(true);

    }

    public void actionPerformed(ActionEvent e)
    {

    }

    public static void main(String[] args)
    {
        new FrmShop2();
    }
}