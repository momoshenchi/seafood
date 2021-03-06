package ui.user;

import control.CommentManager;
import model.customer.BeanDetailOrder;
import model.food.BeanComment;
import util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmComment_mod  extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelcomments = new JLabel("comments");
    private JLabel labellevels = new JLabel("Levels ");
    private JLabel labelpicture = new JLabel("Picture");

    private JTextField edtcomments = new JTextField(16);
    private JTextField edtlevels  = new JTextField(16);
    private JTextField edtpicture = new JTextField(16);

    private BeanComment bc;
    public FrmComment_mod(JDialog f, String s, boolean b, BeanComment bc)
    {
        super(f, s, b);
        this.bc=bc;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelcomments);
        edtcomments.setText(bc.getComments());
        workPane.add(edtcomments);
        workPane.add(labellevels);
        edtlevels.setText(bc.getLevels());
        workPane.add(edtlevels);
        workPane.add(labelpicture);
        edtpicture.setText(bc.getPicture());
        workPane.add(edtpicture);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(280, 180);
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


    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnCancel)
        {
            this.setVisible(false);
            return;
        }
        else if (e.getSource() == this.btnOk)
        {
            bc.setComments(edtcomments.getText());
            bc.setPicture(edtpicture.getText());
            bc.setLevels(edtlevels.getText());
            try
            {
                (new CommentManager()).modifyComment(bc);
            }
            catch (BusinessException e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            this.setVisible(false);
        }
    }
    public String getComment()
    {
        return edtcomments.getText();
    }
}
