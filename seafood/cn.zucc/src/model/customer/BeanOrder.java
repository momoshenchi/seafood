package model.customer;

import java.util.Date;

public class BeanOrder {
    private int orderid;
    private int userid;
    private double ori_amount;
    private double set_amount;
    private int couponid;
    private Date order_time;
    private int addressid;
    private String status;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public double getOri_amount() {
        return ori_amount;
    }

    public void setOri_amount(double ori_amount) {
        this.ori_amount = ori_amount;
    }

    public double getSet_amount() {
        return set_amount;
    }

    public void setSet_amount(double set_amount) {
        this.set_amount = set_amount;
    }

    public int getCouponid() {
        return couponid;
    }

    public void setCouponid(int couponid) {
        this.couponid = couponid;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public int getAddressid() {
        return addressid;
    }

    public void setAddressid(int addressid) {
        this.addressid = addressid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
