package ui.promote;

import control.PromoteManager;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmSaleManager_add extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelcommodityid = new JLabel("commodityid");
    private JLabel labelsaleprice = new JLabel("saleprice ");
    private JLabel labelmaxnumber = new JLabel("maxnumber");
    private JLabel labelstartdate = new JLabel("startDate");
    private JLabel labelenddate = new JLabel("endDate");

    private JTextField edtcommodityid = new JTextField(20);
    private JTextField edtsaleprice = new JTextField(20);
    private JTextField edtmaxnumber = new JTextField(20);
    private JTextField edtstartdate = new JTextField(20);
    private JTextField edtenddate = new JTextField(20);

    public FrmSaleManager_add(JDialog f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelcommodityid);
        workPane.add(edtcommodityid);
        workPane.add(labelsaleprice);
        workPane.add(edtsaleprice);
        workPane.add(labelmaxnumber);
        workPane.add(edtmaxnumber);
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
            if(edtcommodityid.getText() == null||"".equals(edtcommodityid.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input commodityid", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if(edtsaleprice.getText() == null||"".equals(edtsaleprice.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input saleprice", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if(edtmaxnumber.getText() == null||"".equals(edtmaxnumber.getText()))
            {
                JOptionPane.showMessageDialog(null, "please input maxnumber", "提示", JOptionPane.ERROR_MESSAGE);
            }
            try
            {
                (new PromoteManager()).addSale(Integer.parseInt(edtcommodityid.getText()), Double.parseDouble(edtsaleprice.getText()), Integer.parseInt(edtmaxnumber.getText())
                        , edtstartdate.getText(), edtenddate.getText());
                this.setVisible(false);
            }
            catch (BaseException e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public Integer getComid()
    {
        if(edtcommodityid.getText() == null||"".equals(edtcommodityid.getText()))
        {
            return 0;
        }
        return Integer.parseInt(edtcommodityid.getText());
    }
}
