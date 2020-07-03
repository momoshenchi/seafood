package model.customer;

import java.util.Date;

public class BeanUser {
    private  static  BeanUser currentLoginUser =null;
    private  int userid;
    private  String  username;
    private  String sex;
    private  String pwd;
    private  String phonenumber;
    private  String mail;
    private  String city;
    private Date register_time;
    private  String isvip;
    private  Date vipendtime;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getRegister_time() {
        return register_time;
    }

    public void setRegister_time(Date register_time) {
        this.register_time = register_time;
    }

    public String getIsvip() {
        return isvip;
    }

    public void setIsvip(String isvip) {
        this.isvip = isvip;
    }

    public Date getVipendtime() {
        return vipendtime;
    }

    public void setVipendtime(Date vipendtime) {
        this.vipendtime = vipendtime;
    }
}
