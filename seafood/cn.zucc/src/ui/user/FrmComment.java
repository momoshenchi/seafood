package ui.user;

import control.AddressManager;
import control.CommentManager;
import control.PromoteManager;
import model.customer.BeanDetailOrder;
import model.food.BeanComment;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmComment extends JDialog implements ActionListener
{
    private JPanel toolBar = new JPanel();
    private JButton btnAdd = new JButton("添加评论");
    private JButton btnModify = new JButton("修改评论");
    private JButton btnDelete = new JButton("删除评论");
    private BeanDetailOrder bdo;
    private Object tblTitle[] = {"Comments", "Commentdate", "Levels", "Picture"};
    private Object tblData[][];
    List<BeanComment> pubs;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy--MM--dd");
        pubs = (new CommentManager()).loadTheComment(bdo);
        tblData = new Object[pubs.size()][4];
        for (int i = 0; i < pubs.size(); i++)
        {
            tblData[i][0] = pubs.get(i).getComments();
            tblData[i][1] = sdf.format(pubs.get(i).getCommentdate());
            tblData[i][2] = pubs.get(i).getLevels();
            tblData[i][3] = pubs.get(i).getPicture();
        }
        tablmod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public FrmComment(JDialog f, String s, boolean b, BeanDetailOrder bdo)
    {
        super(f, s, b);
        this.bdo = bdo;
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        btnAdd.setFont(new Font("微软雅黑", Font.BOLD, 18));
        toolBar.add(btnModify);
        btnModify.setFont(new Font("微软雅黑", Font.BOLD, 18));
        toolBar.add(btnDelete);
        btnDelete.setFont(new Font("微软雅黑", Font.BOLD, 18));
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.setResizable(false);
        this.btnAdd.addActionListener(this);
        this.btnModify.addActionListener(this);
        this.btnDelete.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAdd)
        {
            FrmComment_add mod = new FrmComment_add(this, "Comment_add", true, bdo);
            mod.setVisible(true);
            if (mod.getComment() != null)
            {
                reloadTable();
            }
        }
        else if (e.getSource() == this.btnModify)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择评论", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanComment p = this.pubs.get(i);
            FrmComment_mod mod = new FrmComment_mod(this, "Comment_modify", true, p);
            mod.setVisible(true);
            if (mod.getComment() != null)
            {
                reloadTable();
            }
        }
        else if (e.getSource() == this.btnDelete)
        {
            int i = this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择评论", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanComment p = this.pubs.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定删除吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                (new CommentManager()).delComment(p);
                this.reloadTable();
            }
        }
    }
}
