package model.food;

import java.util.List;

public class BeanUserRecommend
{
    private String menuname;
    private String ingredient;
    private String commodityname;
    private  double price;

    public String getMenuname()
    {
        return menuname;
    }

    public void setMenuname(String menuname)
    {
        this.menuname = menuname;
    }

    public String getIngredient()
    {
        return ingredient;
    }

    public void setIngredient(String ingredient)
    {
        this.ingredient = ingredient;
    }

    public String getCommodityname()
    {
        return commodityname;
    }

    public void setCommodityname(String commodityname)
    {
        this.commodityname = commodityname;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
}
