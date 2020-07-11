package model.customer;

public class BeanDetailOrder {
    private int orderid;
    private int commodityid;
    private String commodityname;
    private  int userid;
    private int number;


    public String getCommodityname()
    {
        return commodityname;
    }

    public void setCommodityname(String commodityname)
    {
        this.commodityname = commodityname;
    }

    private double price;
    private double vipprice;

    private int discountid;
    private double discount;

    public double getVipprice()
    {
        return vipprice;
    }

    public void setVipprice(double vipprice)
    {
        this.vipprice = vipprice;
    }


    private  int saleid;
    private double saleprice;
    private  String orderstatus;

    public int getSaleid()
    {
        return saleid;
    }

    public void setSaleid(int saleid)
    {
        this.saleid = saleid;
    }

    public double getSaleprice()
    {
        return saleprice;
    }

    public void setSaleprice(double saleprice)
    {
        this.saleprice = saleprice;
    }

    public String getOrderstatus()
    {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus)
    {
        this.orderstatus = orderstatus;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getDiscountid() {
        return discountid;
    }

    public void setDiscountid(int discountid) {
        this.discountid = discountid;
    }
}
