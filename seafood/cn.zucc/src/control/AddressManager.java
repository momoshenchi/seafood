package control;

import model.customer.BeanAddr;

import java.util.ArrayList;
import java.util.List;

public class AddressManager {
    public BeanAddr addAddr(String detail_add,String name,String phone)
    {
        BeanAddr ba=new BeanAddr();
        return ba;
    }

    public List<BeanAddr> loadAll(){
        List<BeanAddr> result=new ArrayList<>();
        return  result;
    }
}
