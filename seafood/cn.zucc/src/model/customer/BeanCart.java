package model.customer;

public class BeanCart
{
    private int commodityid;
    private int number;
    private  double price;
    private int userid;
    private double vipprice;

    public int getCommodityid()
    {
        return commodityid;
    }

    public void setCommodityid(int commodityid)
    {
        this.commodityid = commodityid;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getUserid()
    {
        return userid;
    }

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

    public double getVipprice()
    {
        return vipprice;
    }

    public void setVipprice(double vipprice)
    {
        this.vipprice = vipprice;
    }
}
