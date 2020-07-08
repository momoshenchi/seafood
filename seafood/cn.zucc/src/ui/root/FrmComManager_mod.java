package ui.root;

import control.AdminManager;
import control.PromoteManager;
import model.food.BeanCommodity;
import model.promote.BeanCoupon;
import util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

public class FrmComManager_mod   extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel  labelcommodityid = new JLabel("commodityid");
    private JLabel labelcommodityname = new JLabel("commodityname");
    private JLabel labelprice = new JLabel("price ");
    private JLabel labelvip_price = new JLabel("vip_price");
    private JLabel labelremain_number = new JLabel("remain_number");
    private JLabel labelspec = new JLabel("spec");
    private JLabel labeldetail = new JLabel("detail");
    private JLabel labelpricture = new JLabel("pricture");
    private JLabel labeltypeid = new JLabel("typeid");

    private JTextField edtcommodityid = new JTextField(22);
    private JTextField edtcommodityname = new JTextField(20);
    private JTextField edtprice = new JTextField(20);
    private JTextField edtvip_price = new JTextField(20);
    private JTextField edtremain_number = new JTextField(20);
    private JTextField edtspec = new JTextField(20);
    private JTextField edtdetail = new JTextField(20);
    private JTextField edtpricture = new JTextField(20);
    private JTextField edttypeid = new JTextField(20);

    private BeanCommodity bc=null;
    public FrmComManager_mod(JDialog f, String s, boolean b, BeanCommodity bc)
    {
        super(f, s, b);
        this.bc=bc;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelcommodityid);
        edtcommodityid.setText(bc.getCommodityid()+"");
        edtcommodityid.setEnabled(false);
        workPane.add(edtcommodityid);
        workPane.add(labelcommodityname);
        edtcommodityname.setText(bc.getCommodityname());
        edtcommodityname.setEnabled(false);
        workPane.add(edtcommodityname);
        workPane.add(labelprice);
        edtprice.setText(bc.getPrice()+"");
        workPane.add(edtprice);
        workPane.add(labelvip_price);
        edtvip_price.setText(bc.getVipprice()+"");
        workPane.add(edtvip_price);
        workPane.add(labelremain_number);
        edtremain_number.setText(bc.getRemain_number()+"");
        workPane.add(edtremain_number);
        workPane.add(labelspec);
        edtspec.setText(bc.getSpec());
        workPane.add(edtspec);
        workPane.add(labeldetail);
        edtdetail.setText(bc.getDetail());
        workPane.add(edtdetail);
        workPane.add(labelpricture);
        edtpricture.setText(bc.getPicture());
        workPane.add(edtpricture);
        workPane.add(labeltypeid);
        edttypeid.setText(bc.getTypeid()+"");
        edttypeid.setEnabled(false);
        workPane.add(edttypeid);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 240);
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
            if (edtprice.getText() == null || "".equals(edtprice.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input price", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (edtvip_price.getText() == null || "".equals(edtvip_price.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input vip_price", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (edtremain_number.getText() == null || "".equals(edtremain_number.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input remain_number", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (edttypeid.getText() == null || "".equals(edttypeid.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input typeid", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (JOptionPane.showConfirmDialog(this, "确定修改吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {

                bc.setPrice(Double.parseDouble(edtprice.getText()));
                bc.setVipprice(Double.parseDouble(edtvip_price.getText()));
                bc.setSpec(edtspec.getText());
                bc.setPicture(edtpricture.getText());
                bc.setRemain_number(Integer.parseInt(edtremain_number.getText()));
                bc.setTypeid(Integer.parseInt(edttypeid.getText()));
                bc.setDetail(edtdetail.getText());

                try
                {
                    (new AdminManager()).modifyCommodity(bc);
                }
                catch (BusinessException businessException)
                {
                    businessException.printStackTrace();
                }
                this.setVisible(false);
            }
        }

    }

    public String getSpce()
    {
        return edtspec.getText();
    }
}
