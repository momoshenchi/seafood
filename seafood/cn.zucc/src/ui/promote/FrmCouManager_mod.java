package ui.promote;

import control.PromoteManager;
import model.promote.BeanCoupon;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmCouManager_mod   extends JDialog implements ActionListener
{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel  labelcouponid = new JLabel("Couponid");
    private JLabel labeldetail = new JLabel("detail");
    private JLabel labelstart_price = new JLabel("start_price ");
    private JLabel labelsub_price = new JLabel("sub_price");
    private JLabel labelstartdate = new JLabel("startDate");
    private JLabel labelenddate = new JLabel("endDate");

    private JTextField edtcouponid = new JTextField(22);
    private JTextField edtdetail = new JTextField(20);
    private JTextField edtstart_price = new JTextField(20);
    private JTextField edtsub_price = new JTextField(20);
    private JTextField edtstartdate = new JTextField(20);
    private JTextField edtenddate = new JTextField(20);


    private  BeanCoupon bc=null;
    public FrmCouManager_mod(JDialog f, String s, boolean b, BeanCoupon bc)
    {
        super(f, s, b);
        this.bc=bc;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelcouponid);
        edtcouponid.setText(bc.getCouponid()+"");
        edtcouponid.setEnabled(false);
        workPane.add(edtcouponid);
        workPane.add(labeldetail);
        edtdetail.setText(bc.getDetail());
        workPane.add(edtdetail);
        workPane.add(labelstart_price);
        edtstart_price.setText(bc.getStart_price()+"");
        workPane.add(edtstart_price);
        workPane.add(labelsub_price);
        edtsub_price.setText(bc.getSub_price()+"");
        workPane.add(edtsub_price);
        workPane.add(labelstartdate);
        edtstartdate.setText(sdf.format(bc.getStart_date()));
        workPane.add(edtstartdate);
        workPane.add(labelenddate);
        edtenddate.setText(sdf.format(bc.getEnd_date()));
        workPane.add(edtenddate);
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
            if (edtstart_price.getText() == null || "".equals(edtstart_price.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input start_price", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (edtsub_price.getText() == null || "".equals(edtsub_price.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input sub_price", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if(edtstartdate.getText() == null || "".equals(edtstartdate.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input startdate", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if(edtenddate.getText() == null || "".equals(edtenddate.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input enddate", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (JOptionPane.showConfirmDialog(this, "确定修改吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                Date start = null, end = null;
                try
                {
                    start = sdf.parse(edtstartdate.getText());
                    end = sdf.parse(edtenddate.getText());
                }
                catch (ParseException e1)
                {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }

                bc.setEnd_date(end);
                bc.setStart_date(start);
                bc.setStart_price(Double.parseDouble(edtstart_price.getText()));
                bc.setSub_price(Double.parseDouble(edtsub_price.getText()));
                bc.setDetail(edtdetail.getText());

                (new PromoteManager()).modifyCoupon(bc);
                this.setVisible(false);
            }
        }

    }

    public String getDetail()
    {
        return edtdetail.getText();
    }
}
