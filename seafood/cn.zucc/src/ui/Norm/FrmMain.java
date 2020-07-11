package ui.Norm;


import ui.Norm.FrmAd;
import ui.Norm.FrmAdmin;
import ui.Norm.FrmLogin;
import ui.user.FrmUser;

import javax.swing.*;


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
        {
            FrmUser imageFrame = new FrmUser();
            imageFrame.addImageByRepaint();
        }
        else if(flag==1)
        {
            dlga = new FrmAd(fadmin, "Admin Login", true);
            dlga.setVisible(true);
        }


    }

}
