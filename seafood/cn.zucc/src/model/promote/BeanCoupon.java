package model.promote;

import java.util.Date;

public class BeanCoupon {
    private int couponid;
    private String detail;
    private double start_price;
    private double sub_price;
    private Date start_date;
    private Date end_date;

    public int getCouponid() {
        return couponid;
    }

    public void setCouponid(int couponid) {
        this.couponid = couponid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getStart_price() {
        return start_price;
    }

    public void setStart_price(double start_price) {
        this.start_price = start_price;
    }

    public double getSub_price() {
        return sub_price;
    }

    public void setSub_price(double sub_price) {
        this.sub_price = sub_price;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
