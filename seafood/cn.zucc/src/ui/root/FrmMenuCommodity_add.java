package ui.root;

import control.AdminManager;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmMenuCommodity_add extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelcommodityid = new JLabel("commodityid");
    private JLabel labelmenuid = new JLabel("menuid");
    private JLabel labeldes = new JLabel("description");

    private JTextField edtcommodityid = new JTextField(20);
    private JTextField edtmenuid = new JTextField(20);
    private JTextField edtdes  = new JTextField(20);


    public FrmMenuCommodity_add(JDialog f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelcommodityid);
        workPane.add(edtcommodityid);
        workPane.add(labelmenuid);
        workPane.add(edtmenuid);
        workPane.add(labeldes);
        workPane.add(edtdes);

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
            if(edtcommodityid.getText() == null||"".equals(edtcommodityid.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input edtcommodity", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if(edtmenuid.getText() == null||"".equals(edtmenuid.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input edtmenuid", "提示", JOptionPane.ERROR_MESSAGE);
            }
            try
            {
                (new AdminManager()).addMenuCommodity(Integer.parseInt(edtcommodityid.getText()),Integer.parseInt(edtmenuid.getText()),edtdes.getText());
                this.setVisible(false);
            }
            catch (BaseException e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public int getCommodity()
    {
        if(edtcommodityid.getText() == null||"".equals(edtcommodityid.getText()))
        {
            return  0;
        }
        return Integer.parseInt(edtcommodityid.getText());
    }
}
