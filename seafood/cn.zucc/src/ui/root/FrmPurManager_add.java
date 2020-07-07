package ui.root;

import control.AdminManager;
import control.PromoteManager;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmPurManager_add extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelnumber = new JLabel("Number");
    private JLabel labelcommodityname = new JLabel("Name ");


    private JTextField edtnumber = new JTextField(16);
    private JTextField edtcommodityname = new JTextField(16);

    public FrmPurManager_add(JDialog f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        workPane.add(labelcommodityname);
        workPane.add(edtcommodityname);
        workPane.add(labelnumber);
        workPane.add(edtnumber);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(260, 140);
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
            if(edtnumber.getText() == null||"".equals(edtnumber.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input number", "提示", JOptionPane.ERROR_MESSAGE);
            }
            try
            {
                (new AdminManager()).addPurchase(edtcommodityname.getText(), Integer.parseInt(edtnumber.getText()));
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
