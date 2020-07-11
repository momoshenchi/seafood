package ui.promote;

import control.PromoteManager;
import model.promote.BeanCoupon;
import model.promote.BeanSale;
import util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmSaleManager_mod extends JDialog implements ActionListener
{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelsaleid = new JLabel("saleid");
    private JLabel labelcomid = new JLabel("commodityid");
    private JLabel labelsaleprice = new JLabel("saleprice");
    private JLabel labelmaxnumber = new JLabel("maxnumber");
    private JLabel labelstartdate = new JLabel("startDate");
    private JLabel labelenddate = new JLabel("endDate");

    private JTextField edtsaleprice = new JTextField(20);
    private JTextField edtmaxnumber = new JTextField(20);
    private JTextField edtstartdate = new JTextField(20);
    private JTextField edtenddate = new JTextField(20);
    private JTextField edtsaleid = new JTextField(20);
    private JTextField edtcomid = new JTextField(20);
    private BeanSale bc = null;

    public FrmSaleManager_mod(JDialog f, String s, boolean b, BeanSale bc)
    {
        super(f, s, b);
        this.bc = bc;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelsaleid);
        edtsaleid.setText(bc.getSaleid() + "");
        edtsaleid.setEnabled(false);
        workPane.add(edtsaleid);
        workPane.add(labelcomid);
        edtcomid.setText(bc.getCommodityid() + "");
        edtcomid.setEnabled(false);
        workPane.add(edtcomid);
        workPane.add(labelsaleprice);
        edtsaleprice.setText(bc.getSaleprice() + "");
        workPane.add(edtsaleprice);
        workPane.add(labelmaxnumber);
        edtmaxnumber.setText(bc.getMaxnumber() + "");
        workPane.add(edtmaxnumber);
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
            if (edtsaleprice.getText() == null || "".equals(edtsaleprice.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input saleprice", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (edtmaxnumber.getText() == null || "".equals(edtmaxnumber.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input maxnumber", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (edtstartdate.getText() == null || "".equals(edtstartdate.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input startdate", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (edtenddate.getText() == null || "".equals(edtenddate.getText()))
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
                catch (ParseException e2)
                {
                    JOptionPane.showMessageDialog(null, e2.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }

                bc.setEnd_date(end);
                bc.setStart_date(start);
                bc.setSaleprice(Double.parseDouble(edtsaleprice.getText()));
                bc.setMaxnumber(Integer.parseInt(edtmaxnumber.getText()));

                try
                {
                    (new PromoteManager()).modifySale(bc);
                }
                catch (BusinessException e4)
                {
                    JOptionPane.showMessageDialog(null, e4.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
                this.setVisible(false);
            }
        }

    }
    public Double getPrice()
    {
        if (edtsaleprice.getText() == null || "".equals(edtsaleprice.getText()))
        {
            return 0.0;
        }
        return Double.parseDouble(edtsaleprice.getText());

    }
}
