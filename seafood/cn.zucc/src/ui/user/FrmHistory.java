package ui.user;

import control.AdminManager;
import control.OrderManager;
import control.UserManager;
import model.customer.BeanOrder;
import model.food.BeanUserRecommend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmHistory  extends JDialog
{
    private JPanel toolBar = new JPanel();
    private JButton btnshouchang;
    private Object tblTitle[]={"original amount","settle amount ","ordertime"};
    private Object tblData[][];
    List<BeanOrder> pubs;

    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy--MM--dd");
        pubs = (new OrderManager()).loadUserOrders();
        tblData = new Object[pubs.size()][3];
        for (int i = 0; i < pubs.size(); i++)
        {
            tblData[i][0] = pubs.get(i).getOri_amount()+"";
            tblData[i][1] = pubs.get(i).getSet_amount()+"";
            tblData[i][2] =  sdf.format(pubs.get(i).getOrder_time());
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
    }
    public FrmHistory(Frame f, String s, boolean b)
    {
        super(f,s,b);
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
    }
}
