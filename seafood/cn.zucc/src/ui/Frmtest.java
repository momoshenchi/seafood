package ui;

import control.ReadImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frmtest extends JFrame implements ActionListener
{
    private ScrollPane s = new ScrollPane();
    private  JPanel v=new JPanel();
    private ScrollPane n = new ScrollPane();
    private Box left = Box.createVerticalBox();
    private Box right = Box.createVerticalBox();
    private JLabel jl;
    private JLabel picture;
    private JButton btnadd;


    public Frmtest()
    {
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("admin");
        left.add(readIcon("12.jpg"));
        left.add(readIcon("j12.jpg"));
        left.add(readIcon("j13.jpg"));
        left.add(readIcon("j11.jpg"));

        right.add(readIcon("12.jpg"));
        right.add(readIcon("j12.jpg"));
        right.add(readIcon("j13.jpg"));
        right.add(readIcon("j11.jpg"));
        v.add(left);
        v.add(right);
        n.add(v);
        this.add(s,BorderLayout.WEST);
        this.add(n,BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public Box readIcon(String name)
    {

        ImageIcon icon = ReadImage.read(name);
        icon.setImage(icon.getImage().getScaledInstance(500,500,Image.SCALE_DEFAULT));  //设置大小
        JLabel picture = new JLabel(icon);
         JLabel jl=new JLabel("detail_address ");
        jl.setFont(new Font("微软雅黑", Font.BOLD, 25));
//        JPanel p = new JPanel();
         Box p = Box.createVerticalBox();
         JPanel m=new JPanel();
         JButton btnadd=new JButton("buy this ");
        btnadd.setFont(new Font("微软雅黑", Font.BOLD, 25));
//        Dimension preferredSize = new Dimension(200,200);
//        btnadd.setPreferredSize(preferredSize );

        p.add(picture);
        p.add(jl);
        p.add(btnadd);
//        p.setSize(320, 480);
//        m.add(p);

        return p;
    }

    public void actionPerformed(ActionEvent e)
    {

    }

    public static void main(String[] args)
    {
        new Frmtest();
    }
}
