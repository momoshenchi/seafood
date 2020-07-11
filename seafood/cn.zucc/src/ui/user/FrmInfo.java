package ui.user;

import control.AdminManager;
import control.PromoteManager;
import control.UserManager;
import model.customer.BeanUser;
import model.food.BeanCommodity;
import model.promote.BeanCoupon;
import model.root.BeanAdmin;
import ui.promote.FrmCouManager_add;
import ui.promote.FrmCouManager_mod;
import ui.root.FrmComManager_mod;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmInfo extends JDialog  implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private  JPanel jp1=new JPanel();
    private JButton btnModify = new JButton("修改个人信息");

    private Object tblTitle[] = { "username", "sex", "phonenumber", "mail","city",
            "register_time","isvip","vipendtime"};
    private Object tblData[][];
    BeanUser pubs;


    private void add()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy--MM--dd");
        pubs = (new UserManager()).loadUserSelf();
            jp1.add(new JLabel(pubs.getUsername()));
            jp1.add(new JLabel(pubs.getSex()));
            jp1.add(new JLabel(pubs.getPhonenumber()));
            jp1.add(new JLabel(pubs.getMail()));
            jp1.add(new JLabel(pubs.getCity()));
            jp1.add(new JLabel(sdf.format(pubs.getRegister_time())));
            jp1.add(new JLabel(pubs.getIsvip()));
            if("true".equals(pubs.getIsvip()))
                jp1.add(new JLabel(sdf.format(pubs.getVipendtime())));
    }

    public FrmInfo(Frame f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnModify);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.add();

        this.getContentPane().add(jp1, BorderLayout.CENTER);
        this.setSize(400, 300);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.btnModify.addActionListener(this);
        this.validate();


    }
    public void actionPerformed(ActionEvent e)
    {

        if(e.getSource()==this.btnModify)
        {
            BeanUser p = this.pubs;
            FrmInfo_mod mod = new FrmInfo_mod(this, "modify", true, p);
            mod.setVisible(true);
            if (mod.getSex() != null)
            {
//                reloadTable();
            }
        }

    }


}
