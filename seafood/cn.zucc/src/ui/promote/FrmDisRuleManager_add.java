package ui.promote;

import control.PromoteManager;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmDisRuleManager_add extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labeldetail = new JLabel("detail");
    private JLabel labelmin_number = new JLabel("min_number ");
    private JLabel labeldiscount = new JLabel("discount");
    private JLabel labelstartdate = new JLabel("startDate");
    private JLabel labelenddate = new JLabel("endDate");

    private JTextField edtdetail = new JTextField(20);
    private JTextField edtmin_number = new JTextField(20);
    private JTextField edtdiscount = new JTextField(20);
    private JTextField edtstartdate = new JTextField(20);
    private JTextField edtenddate = new JTextField(20);

    public FrmDisRuleManager_add(JDialog f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labeldetail);
        workPane.add(edtdetail);
        workPane.add(labelmin_number);
        workPane.add(edtmin_number);
        workPane.add(labeldiscount);
        workPane.add(edtdiscount);
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
            if(edtmin_number.getText() == null||"".equals(edtmin_number.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input min_number", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (edtdiscount.getText() == null||"".equals(edtdiscount.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input discount", "提示", JOptionPane.ERROR_MESSAGE);
            }
            try
            {
                (new PromoteManager()).addDiscountRule(edtdetail.getText(), Integer.parseInt(edtmin_number.getText()), Double.parseDouble(edtdiscount.getText())
                        , edtstartdate.getText(), edtenddate.getText());
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
