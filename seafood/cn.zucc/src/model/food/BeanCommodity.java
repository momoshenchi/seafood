package model.food;

public class BeanCommodity {
    private int commodityid;
    private String commodityname;
    private double price;
    private double  vipprice;
    private int remain_number;
    private String spec;
    private String detail;
    private String picture;
    private  int typeid;

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getCommodityid() {
        return commodityid;
    }

    public void setCommodityid(int commodityid) {
        this.commodityid = commodityid;
    }

    public String getCommodityname() {
        return commodityname;
    }

    public void setCommodityname(String commodityname) {
        this.commodityname = commodityname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVipprice() {
        return vipprice;
    }

    public void setVipprice(double vipprice) {
        this.vipprice = vipprice;
    }

    public int getRemain_number() {
        return remain_number;
    }

    public void setRemain_number(int remain_number) {
        this.remain_number = remain_number;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }
}
