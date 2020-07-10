package ui.user;

import control.ReadImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmUser extends JFrame implements ActionListener {
    private JButton Button1= new JButton("浏览菜谱");
    private JButton Button2= new JButton("生鲜购买");
    private JButton Button3= new JButton("查看历史");
    private JButton Button4= new JButton("购物评价");
    private JButton Button5= new JButton("退出菜单");
    private JPanel panel =new JPanel();
    private JPanel panel2 =new JPanel();
    private JPanel contentPane =new JPanel();
    private Box b=Box.createVerticalBox();
    private Box b2=Box.createVerticalBox();
    private JLabel sd=new JLabel("");
    private JLabel sd2=new JLabel("1");
    private JLabel sd3=new JLabel("2");
    private JLabel sd4=new JLabel("3");
    private JLabel sd5=new JLabel("4");
    private JLabel labelpict;
    public FrmUser()  {

        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("生鲜商超");
        b.add(Box.createVerticalStrut(30));
        b.add( Button1 );
        this.Button1.addActionListener(this);
        b.add(Box.createVerticalStrut(30));
        b.add( Button2 );
        this.Button2.addActionListener(this);
        b.add(Box.createVerticalStrut(30));
        b.add( Button3 );
        this.Button3.addActionListener(this);
        b.add(Box.createVerticalStrut(30));
        b.add( Button4 );
        this.Button4.addActionListener(this);
        b.add(Box.createVerticalStrut(30));
        b.add( Button5 );
        this.Button5.addActionListener(this);

        b2.add(sd);
        b2.add(Box.createVerticalStrut(80));
        b2.add(sd2);
        b2.add(Box.createVerticalStrut(80));
        b2.add(sd3);
        b2.add(Box.createVerticalStrut(80));
        b2.add(sd4);
        b2.add(Box.createVerticalStrut(80));
        b2.add(sd5);
        panel2.add(b2);

        this.add(panel2,BorderLayout.NORTH);
        contentPane.add(b);
        this.add( contentPane ,BorderLayout.CENTER);


        ImageIcon icon = ReadImage.read("12.jpg");
        labelpict = new JLabel(icon);
        labelpict.setSize(new Dimension(40,40));
        panel.add(labelpict);
        this.add(panel,BorderLayout.WEST);


        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        new FrmUser();
    }
}
