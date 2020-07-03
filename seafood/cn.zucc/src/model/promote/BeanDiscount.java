package model.promote;

import java.util.Date;

public class BeanDiscount {
    private int discountid;
    private String detail;
    private int min_number;
    private int discount;
    private Date start_date;
    private Date end_date;

    public int getDiscountid() {
        return discountid;
    }

    public void setDiscountid(int discountid) {
        this.discountid = discountid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getMin_number() {
        return min_number;
    }

    public void setMin_number(int min_number) {
        this.min_number = min_number;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
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
