package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FrmMain extends JFrame  {
    //    private Icon icon;
    private static final long serialVersionUID = 1L;
    public static int flag=0;
    private FrmLogin dlgLogin = null;
    private FrmUser fuser = null;
    private FrmAdmin fadmin = null;
    private FrmAd dlga = null;

    public FrmMain() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        dlgLogin = new FrmLogin(fuser, "Login", true);
        dlgLogin.setVisible(true);

        if(flag==0)
            new FrmUser();
        else if(flag==1)
        {
            dlga = new FrmAd(fadmin, "Admin Login", true);
            dlga.setVisible(true);
        }


    }

}
