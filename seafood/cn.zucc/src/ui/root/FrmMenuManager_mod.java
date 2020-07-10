package ui.root;

import control.AdminManager;
import model.food.BeanCommodity;
import model.food.BeanMenu;
import util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmMenuManager_mod extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelmenuid = new JLabel("menuid ");
    private JLabel labelmenuname = new JLabel("menuname");
    private JLabel labelingredient = new JLabel("ingredient ");
    private JLabel labelstep = new JLabel("step");
    private JLabel labelpricture = new JLabel("pricture");

    private JTextField edtmenuid = new JTextField(22);
    private JTextField edtmenuname = new JTextField(20);
    private JTextField edtingredient = new JTextField(20);
    private JTextField edtstep = new JTextField(20);
    private JTextField edtpricture = new JTextField(20);


    private BeanMenu bm = null;

    public FrmMenuManager_mod(JDialog f, String s, boolean b, BeanMenu bm)
    {
        super(f, s, b);
        this.bm = bm;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelmenuid);
        edtmenuid.setText(bm.getMenuid() + "");
        edtmenuid.setEnabled(false);
        workPane.add(edtmenuid);
        workPane.add(labelmenuname);
        edtmenuname.setText(bm.getMenuname());
        edtmenuname.setEnabled(false);
        workPane.add(edtmenuname);

        workPane.add(labelingredient);
        edtingredient.setText(bm.getIngredient());
        workPane.add(edtingredient);

        workPane.add(labelstep);
        edtstep.setText(bm.getStep());
        workPane.add(edtstep);

        workPane.add(labelpricture);
        edtpricture.setText(bm.getPicture());
        workPane.add(edtpricture);

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
            if (JOptionPane.showConfirmDialog(this, "确定修改吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {

                bm.setPicture(edtpricture.getText());
                bm.setIngredient(edtingredient.getText());
                bm.setStep(edtstep.getText());
                try
                {
                    (new AdminManager()).modifyMenu(bm);
                }
                catch (BusinessException businessException)
                {
                    businessException.printStackTrace();
                }
                this.setVisible(false);
            }
        }

    }

    public String getIngredient()
    {
        return edtingredient.getText();
    }
}
