package ui.root;

import control.AdminManager;
import control.PromoteManager;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmComManager_add extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelcommodityname = new JLabel("Name");
    private JLabel labelprice = new JLabel("price ");
    private JLabel labelvipprice = new JLabel("vipprice");
    private JLabel labelspec = new JLabel("spec  ");
    private JLabel labeldetail = new JLabel("detail ");
    private JLabel labelpicture = new JLabel("picture ");
    private JLabel labeltypeid = new JLabel("typeid ");


    private JTextField edtcommodityname = new JTextField(20);
    private JTextField edtprice = new JTextField(20);
    private JTextField edtvipprice = new JTextField(20);
    private JTextField edtspec = new JTextField(20);
    private JTextField edtdetail = new JTextField(20);
    private JTextField edtpicture = new JTextField(20);
    private JTextField edttypeid = new JTextField(20);


    public FrmComManager_add(JDialog f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelcommodityname);
        workPane.add(edtcommodityname);
        workPane.add(labelprice);
        workPane.add(edtprice);
        workPane.add(labelvipprice);
        workPane.add(edtvipprice);
        workPane.add(labelspec);
        workPane.add(edtspec);
        workPane.add(labeldetail);
        workPane.add(edtdetail);
        workPane.add(labelpicture);
        workPane.add(edtpicture);
        workPane.add(labeltypeid);
        workPane.add(edttypeid);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(280, 280);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.setResizable(false);
        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnCancel)
        {
            this.setVisible(false);
            return;
        }
        else if (e.getSource() == this.btnOk)
        {
            if(edtprice.getText() == null||"".equals(edtprice.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input price", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if(edtvipprice.getText() == null||"".equals(edtvipprice.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input vipprice", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if(edttypeid.getText() == null||"".equals(edttypeid.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input typeid", "提示", JOptionPane.ERROR_MESSAGE);
            }
            try
            {
                (new AdminManager()).addCommodity(edtcommodityname.getText(), Double.parseDouble(edtprice.getText()), Double.parseDouble(edtvipprice.getText())
                        , edtspec.getText(), edtdetail.getText(), edtpicture.getText(),
                        Integer.parseInt(edttypeid.getText()));
                this.setVisible(false);
            }
            catch (BaseException e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getCommodityname()
    {
        return edtcommodityname.getText();
    }
}
