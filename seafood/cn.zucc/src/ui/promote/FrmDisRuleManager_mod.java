package ui.promote;

import control.PromoteManager;
import model.promote.BeanDiscount;
import util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmDisRuleManager_mod  extends JDialog implements ActionListener
{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labeldis_id = new JLabel("discount_id");
    private JLabel labeldetail = new JLabel("detail");
    private JLabel labelminnumber = new JLabel("min_number");
    private JLabel labeldiscount = new JLabel("discount");
    private JLabel labelstartdate = new JLabel("startDate");
    private JLabel labelenddate = new JLabel("endDate");

    private JTextField edtdetail = new JTextField(20);
    private JTextField edtminnumber = new JTextField(20);
    private JTextField edtdiscount = new JTextField(20);
    private JTextField edtstartdate = new JTextField(20);
    private JTextField edtenddate = new JTextField(20);
    private JTextField edtdis_id = new JTextField(20);
    private BeanDiscount bc=null;
    public FrmDisRuleManager_mod(JDialog f, String s, boolean b, BeanDiscount bc)
    {
        super(f, s, b);
        this.bc=bc;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labeldis_id);
        edtdis_id.setText(bc.getDiscountid()+"");
        edtdis_id.setEnabled(false);
        workPane.add(edtdis_id);
        workPane.add(labeldetail);
        edtdetail.setText(bc.getDetail());
        workPane.add(edtdetail);
        workPane.add(labelminnumber);
        edtminnumber.setText(bc.getMin_number()+"");
        workPane.add(edtminnumber);
        workPane.add(labeldiscount);
        edtdiscount.setText(bc.getDiscount()+"");
        workPane.add(edtdiscount);
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
            if (edtminnumber.getText() == null || "".equals(edtminnumber.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input minnumber", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (edtdiscount.getText() == null || "".equals(edtdiscount.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input discount", "提示", JOptionPane.ERROR_MESSAGE);
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
                bc.setMin_number(Integer.parseInt(edtminnumber.getText()));
                bc.setDiscount(Double.parseDouble(edtdiscount.getText()));
                bc.setDetail(edtdetail.getText());

                try
                {
                    (new PromoteManager()).modifyDiscountRule(bc);
                }
                catch (BusinessException e2)
                {
                    JOptionPane.showMessageDialog(null, e2.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
                this.setVisible(false);
            }
        }

    }

    public String getDetail()
    {
        return edtdetail.getText();
    }
}
