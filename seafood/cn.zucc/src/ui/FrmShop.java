package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.UserManager;
import model.food.BeanCommodity;
import model.promote.BeanCoupon;
import control.AdminManager;
import control.ReadImage;

public class FrmShop extends JDialog implements ActionListener{

	private JFrame frame=new JFrame("GridLayou���ּ�����");
	private JPanel panel=new JPanel();    //�������
     
     private JPanel picture1=new JPanel();
     private JPanel picture2=new JPanel();
     private JPanel picture3=new JPanel();
     private JPanel picture4=new JPanel();
     
     private JLabel sd=new JLabel("0");
     private JLabel sd2=new JLabel("1");
     private JLabel sd3=new JLabel("2");
     private JLabel sd4=new JLabel("3");
     private JLabel sd5=new JLabel("4");
     private JLabel p1;
     private JLabel p2;
     private JLabel p3;
     private JLabel p4;
     
     List<BeanCommodity> pubs;
     //ָ�����Ĳ���ΪGridLayout��4��4�У���϶Ϊ5
     public FrmShop() {
    	 
    	 panel.setLayout(new GridLayout(2,2,5,5));
     frame.add(panel);   
     frame.setBounds(300,200,200,150);
     
//    pubs= (new AdminManager()).loadAllCommodity();
//    for (int i = 0; i < pubs.size(); i++)
//    {
//    	BeanCommodity tem=pubs.get(i);
//    	readIcon(new JPanel(),new JLabel(tem.getSpec()),tem.getPicture());
//    }
     readIcon(picture1,sd,"12.jpg");
//     readIcon(picture2,sd2,"123.jpg");
//     readIcon(picture3,sd3,"124.jpg");
//     readIcon(picture4,sd4,"125.jpg");
     
     frame.setExtendedState(Frame.MAXIMIZED_BOTH);
     frame.setTitle("生鲜商超");
     frame.add(panel);
     frame.setVisible(true);
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
     public void readIcon(JPanel jp,JLabel des,String name)
     {
//    	 jp.setSize(40, 40);
    	 ImageIcon icon = ReadImage.read(name);
         p1 = new JLabel(icon);
//         p1.setSize(40,40);
//         p1.set
         jp.add(p1);
         jp.add(des);
         panel.add(jp);
     }
	public void actionPerformed(ActionEvent e)
    {
    }
	public static void main(String[] args) {
		new FrmShop();
	}
    
}
