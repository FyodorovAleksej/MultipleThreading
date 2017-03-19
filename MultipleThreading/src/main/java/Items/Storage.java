package Items;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexey on 18.03.2017.
 */
public class Storage{

    //-----------------------Objects-------------------------------------------

    private Map productList;

    //-----------------------Constructors--------------------------------------

    public Storage(){
        productList = new HashMap<String,Integer>();
    }

    //-----------------------Get/Set-------------------------------------------

    public Integer getCountOfProduct(String name){
        return (Integer) productList.get(name);
    }

    //-----------------------Methods-------------------------------------------

    public boolean put(String prod, Integer count) {
        if (!productList.containsKey(prod)) {
            productList.put(prod, count);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean delete(String prod){
        if (productList.containsKey(prod)) {
            productList.remove(prod);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean subCount(String prod,int value){
        if (productList.containsKey(prod)){
            Integer i = (Integer) productList.get(prod);
            productList.remove(prod);
            i -= value;
            productList.put(prod,i);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean addCount(String prod,int value){
        if (productList.containsKey(prod)){
            Integer i = (Integer) productList.get(prod);
            productList.remove(prod);
            i += value;
            productList.put(prod,i);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isContain(String prod){
        return productList.containsKey(prod);
    }

    public void removeAll(){
        productList.clear();
    }

    public String toString(){
        String s = "";
        if (this.productList != null) {
            Set<Map.Entry<String, Integer>> set = productList.entrySet();
            for (Map.Entry<String, Integer> me : set) {
                System.out.print(me.getKey() + " - ");
                System.out.println(me.getValue());
            }
        }
        return s;
    }

}
