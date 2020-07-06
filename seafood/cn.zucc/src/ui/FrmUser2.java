package ui;


import control.ReadImage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class FrmUser2 extends JFrame implements ActionListener{
	private Dimension frameSize = new Dimension(1600, 840);
	private ImageIcon imageIcon = ReadImage.read("12.jpg");
	 	private JButton Button1= new JButton("浏览菜谱");
	    private JButton Button2= new JButton("生鲜购买");
	    private JButton Button3= new JButton("查看历史");
	    private JButton Button4= new JButton("购物评价");
        private JButton Button5= new JButton("个人信息");
	    private JButton Button6= new JButton("退出菜单");
	    private JPanel panel =new JPanel();
	    private JPanel panel2 =new JPanel();
	    private Box b=Box.createVerticalBox();

	    private Font font = new Font("宋体", Font.PLAIN, 40);
	 
  class ImagePanel extends JPanel {
    Dimension d;
    Image image;
    public ImagePanel(Dimension d, Image image) {
      super();
      this.d = d;
      this.image = image;
    }
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(image, 0, 0, d.width, d.height, this);
     this.repaint();
    }
  }
  
  
  public FrmUser2() {
    // 设置窗体属性
//    setSize(frameSize);
//    setLocationRelativeTo(null);
	  this.setExtendedState(Frame.MAXIMIZED_BOTH);
      this.setTitle("生鲜商超");
	  	setLayout(null);
	  	
	  	addComponents(); 
	  	
	  	
	    JLabel lbBg = new JLabel(imageIcon);
	    lbBg.setBounds(0, 0, this.getWidth(), this.getHeight());
	    this.getContentPane().add(lbBg);
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setIconImage(imageIcon.getImage());
  }
  public void addImageByRepaint() {
    ImagePanel imagePanel = new ImagePanel(frameSize, imageIcon.getImage());
    setContentPane(imagePanel);
    addComponents();
    setVisible(true);
  }
  
  private void addComponents() {
//	b.setSize(800, 200);
// 怎么设置按钮大小
    b.add(Box.createVerticalStrut(30));
    Button1.setFont(new Font("微软雅黑", Font.BOLD, 25));
    b.add( Button1 );
    this.Button1.addActionListener(this);
    b.add(Box.createVerticalStrut(50));
    Button2.setFont(new Font("微软雅黑", Font.BOLD, 25));
    b.add( Button2 );
    this.Button2.addActionListener(this);
    b.add(Box.createVerticalStrut(50));
    Button3.setFont(new Font("微软雅黑", Font.BOLD, 25));
    b.add( Button3 );
    this.Button3.addActionListener(this);
    b.add(Box.createVerticalStrut(50));
    Button4.setFont(new Font("微软雅黑", Font.BOLD, 25));
    b.add( Button4 );
    this.Button4.addActionListener(this);
    b.add(Box.createVerticalStrut(50));
    Button5.setFont(new Font("微软雅黑", Font.BOLD, 25));
    b.add( Button5 );
    this.Button5.addActionListener(this);
      b.add(Box.createVerticalStrut(50));
      Button6.setFont(new Font("微软雅黑", Font.BOLD, 25));
      b.add( Button6 );
      this.Button6.addActionListener(this);
    b.setFont(font);
    this.getContentPane().add(b);
//    this.getContentPane().setFont(font);
     
  }
  
  public void actionPerformed(ActionEvent e) {
      if(e.getSource()==this.Button1)
      {
    	  
      }
      else if(e.getSource()==this.Button2)
      {
    	  
      }
      else if(e.getSource()==this.Button3)
      {
    	  
      }
      else if(e.getSource()==this.Button4)
      {
    	  
      }
      else if(e.getSource()==this.Button5)
      {

      }
      else if(e.getSource()==this.Button6)
      {

      }
  }
  
  public static void main(String[] args) {
      FrmUser2 imageFrame = new FrmUser2();
    imageFrame.addImageByRepaint();
  }
}