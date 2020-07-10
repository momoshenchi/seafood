package ui.root;

import control.AdminManager;
import util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmMenuManager_add  extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelMenuname = new JLabel("Menuname");
    private JLabel labelingredient = new JLabel("ingredient ");
    private JLabel labelstep = new JLabel("step ");
    private JLabel labelpicture = new JLabel("picture ");


    private JTextField edtMenuname = new JTextField(16);
    private JTextField edtingredient = new JTextField(16);
    private JTextField edtstep = new JTextField(16);
    private JTextField edtpicture = new JTextField(16);
    public FrmMenuManager_add(JDialog f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        workPane.add(labelMenuname);
        workPane.add(edtMenuname);
        workPane.add(labelingredient);
        workPane.add(edtingredient);
        workPane.add(labelstep);
        workPane.add(edtstep);

        workPane.add(labelpicture);
        workPane.add(edtpicture);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(260, 180);
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

            try
            {
                (new AdminManager()).addMenu(edtMenuname.getText(),edtingredient.getText(),edtstep.getText(),edtpicture.getText());
                this.setVisible(false);
            }
            catch (BusinessException e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public String getMenuname()
    {
        return edtMenuname.getText();
    }
}
