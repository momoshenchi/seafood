package ui.root;

import control.AdminManager;
import control.PromoteManager;
import model.food.BeanType;
import model.promote.BeanCoupon;
import util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

public class FrmTypeManager_mod  extends JDialog implements ActionListener
{private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel  labeltypeid = new JLabel("Typeid");
    private JLabel labeltypename= new JLabel("Typename");
    private JLabel labeldes = new JLabel("description ");


    private JTextField edttypeid = new JTextField(22);
    private JTextField edttypename = new JTextField(20);
    private JTextField edtdes = new JTextField(20);



    private BeanType bc=null;
    public FrmTypeManager_mod(JDialog f, String s, boolean b, BeanType bc)
    {
        super(f, s, b);
        this.bc=bc;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labeltypeid);
        edttypeid.setText(bc.getTypeid()+"");
        edttypeid.setEnabled(false);
        workPane.add(edttypeid);
        workPane.add(labeltypename);
        edttypename.setText(bc.getTypename());
        edttypename.setEnabled(false);
        workPane.add(edttypename);
        workPane.add(labeldes);
        edtdes.setText(bc.getDescription()+"");
        workPane.add(edtdes);

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

                bc.setDescription(edtdes.getText());
                try
                {
                    (new AdminManager()).modifyType(bc);
                }
                catch (BusinessException businessException)
                {
                    businessException.printStackTrace();
                }
                this.setVisible(false);
            }
        }

    }

    public String getDes()
    {
        return edtdes.getText();
    }
}
