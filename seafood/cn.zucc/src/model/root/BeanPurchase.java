package model.root;

import java.util.Date;

public class BeanPurchase {
    private  int purchaseid;
    private  int commodityid;
    private  int number;
    private  String status;
    private  int adminid;
    private  String commodityname;
    private Date purchasedate;

    public String getCommodityname()
    {
        return commodityname;
    }

    public void setCommodityname(String commodityname)
    {
        this.commodityname = commodityname;
    }

    public Date getPurchasedate()
    {
        return purchasedate;
    }

    public void setPurchasedate(Date purchasedate)
    {
        this.purchasedate = purchasedate;
    }

    public int getPurchaseid() {
        return purchaseid;
    }

    public void setPurchaseid(int purchaseid) {
        this.purchaseid = purchaseid;
    }

    public int getCommodityid() {
        return commodityid;
    }

    public void setCommodityid(int commodityid) {
        this.commodityid = commodityid;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAdminid() {
        return adminid;
    }

    public void setAdminid(int adminid) {
        this.adminid = adminid;
    }
}
