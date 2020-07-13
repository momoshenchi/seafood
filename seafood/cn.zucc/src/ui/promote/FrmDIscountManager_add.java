package ui.promote;

import control.PromoteManager;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmDIscountManager_add  extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelcommodityid = new JLabel("commodityid ");
    private JLabel labeldiscountid = new JLabel("discountid");


    private JTextField edtcommodityid = new JTextField(20);
    private JTextField edtdiscountid = new JTextField(20);


    public FrmDIscountManager_add(JDialog f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        workPane.add(labeldiscountid);
        workPane.add(edtdiscountid);
        workPane.add(labelcommodityid);
        workPane.add(edtcommodityid);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 160);
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
            if(edtdiscountid.getText() == null||"".equals(edtdiscountid.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input discountid", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (edtcommodityid.getText() == null||"".equals(edtcommodityid.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input commodityid", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try
            {
                (new PromoteManager()).addDiscount( Integer.parseInt(edtdiscountid.getText()),Integer.parseInt(edtcommodityid.getText()));
                this.setVisible(false);
            }
            catch (BaseException e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public int getDiscountid()
    {
        if(edtdiscountid.getText() == null||"".equals(edtdiscountid.getText()))
        {
            return  0;
        }
        return Integer.parseInt(edtdiscountid.getText());
    }
}
