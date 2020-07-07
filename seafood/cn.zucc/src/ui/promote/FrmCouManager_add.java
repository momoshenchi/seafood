package ui.promote;

import control.PromoteManager;
import util.BaseException;
import util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmCouManager_add extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labeldetail = new JLabel("detail");
    private JLabel labelstart_price = new JLabel("start_price ");
    private JLabel labelsub_price = new JLabel("sub_price");
    private JLabel labelstartdate = new JLabel("startDate");
    private JLabel labelenddate = new JLabel("endDate");

    private JTextField edtdetail = new JTextField(20);
    private JTextField edtstart_price = new JTextField(20);
    private JTextField edtsub_price = new JTextField(20);
    private JTextField edtstartdate = new JTextField(20);
    private JTextField edtenddate = new JTextField(20);

    public FrmCouManager_add(JDialog f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labeldetail);
        workPane.add(edtdetail);
        workPane.add(labelstart_price);
        workPane.add(edtstart_price);
        workPane.add(labelsub_price);
        workPane.add(edtsub_price);
        workPane.add(labelstartdate);
        workPane.add(edtstartdate);
        workPane.add(labelenddate);
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
            if (edtstart_price.getText() == null||"".equals(edtstart_price.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input start_price", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (edtsub_price.getText() == null||"".equals(edtsub_price.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input sub_price", "提示", JOptionPane.ERROR_MESSAGE);
            }
            try
            {

                (new PromoteManager()).addCoupon(edtdetail.getText(), Double.parseDouble(edtstart_price.getText()),
                        Double.parseDouble(edtsub_price.getText()), edtstartdate.getText(), edtenddate.getText());
                this.setVisible(false);
            }
            catch (BaseException e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public String getDetail()
    {
        return edtdetail.getText();
    }
}
