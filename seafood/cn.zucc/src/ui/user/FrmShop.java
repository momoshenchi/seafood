package ui.user;

import control.AdminManager;
import control.ReadImage;
import model.food.BeanCommodity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmShop
{
    private  JFrame frame =new JFrame();
    private ScrollPane s = new ScrollPane();
    private JPanel v = new JPanel();
    private ScrollPane n = new ScrollPane();
    private Box left = Box.createVerticalBox();
    private Box right = Box.createVerticalBox();
    private JLabel jl;
    private JLabel picture;
    private JButton btnadd;
    List<BeanCommodity> bc;

    public FrmShop()
    {
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setTitle("admin");
        bc = (new AdminManager()).loadAllCommodity();
        for (int i = 0; i < bc.size(); ++i)
        {
            if (i % 2 == 0)
            {
                left.add(readBean(bc.get(i)));
            }
            else
            {
                right.add(readBean(bc.get(i)));
            }
        }
        v.add(left);
        v.add(right);
        n.add(v);
        frame.add(s, BorderLayout.WEST);
        frame.add(n, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public Box readBean(BeanCommodity bc)
    {

        ImageIcon icon = ReadImage.read(bc.getPicture());
        icon.setImage(icon.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));  //设置大小
        JLabel picture = new JLabel(icon);
        JLabel jname =new JLabel("Name: " +bc.getCommodityname());
        JLabel jprice = new JLabel("Price: " + bc.getPrice());
        JLabel jvip = new JLabel("Vipprice: " + bc.getVipprice());
        jname.setFont(new Font("微软雅黑", Font.BOLD, 25));
        jprice.setFont(new Font("微软雅黑", Font.BOLD, 25));
        jvip.setFont(new Font("微软雅黑", Font.BOLD, 25));
        Box p = Box.createVerticalBox();
        JPanel m = new JPanel();
        JButton btnadd = new JButton("Buy this ");
        btnadd.setFont(new Font("微软雅黑", Font.BOLD, 25));
        btnadd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                FrmShop_add fsa=new FrmShop_add(frame,"number",true,bc);
                fsa.setVisible(true);
            }
        });

        p.add(picture);
        p.add(jname);
        p.add(jprice);
        p.add(jvip);
        p.add(btnadd);

        return p;
    }



    public static void main(String[] args)
    {
        new FrmShop();
    }
}
