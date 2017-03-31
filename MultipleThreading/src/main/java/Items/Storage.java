package Items;

import org.eclipse.swt.widgets.List;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexey on 18.03.2017.
 */
public class Storage{

    //-----------------------Objects-------------------------------------------

    protected Map<String,Integer> productList;

    //-----------------------Constructors--------------------------------------

    public Storage(){
        productList = new HashMap<String,Integer>();
    }

    //-----------------------Get/Set-------------------------------------------

    public Integer getCountOfProduct(String name){
        if (productList.containsKey(name)) {
            return (Integer) productList.get(name);
        }
        else {
            return 0;
        }
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
            if (i >= 0) {
                productList.put(prod, i);
                return true;
            }
        }
        return false;
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
            put(prod,value);
            return false;
        }
    }

    public boolean isContain(String prod){
        return productList.containsKey(prod);
    }

    public Set<String> getKeySet(){
        return this.productList.keySet();
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

    public boolean getFromStorage(Storage storage) {
        if (this.isContainsInStorage(storage)) {
            Set<String> keySet = productList.keySet();
            for (String i : keySet) {
                storage.subCount(i, this.getCountOfProduct(i));
                this.addCount(i, this.getCountOfProduct(i));
            }
            return true;
        }
        return false;
    }

    public boolean putToStorage(Storage storage) {
        Set<String> keySet = productList.keySet();
        for (String i : keySet) {
            storage.addCount(i, this.getCountOfProduct(i));
            this.subCount(i, this.getCountOfProduct(i));
        }
        return true;
    }

    public boolean isContainsInStorage(Storage storage) {
        Set<String> keySet = productList.keySet();
        for (String i : keySet) {
            if (storage.isContain(i)) {
                if (this.getCountOfProduct(i) > storage.getCountOfProduct(i)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
    public String[] out(){
        String[] strings = new String[this.productList.size()];
        int i = 0;
        if (this.productList != null) {
            Set<Map.Entry<String, Integer>> set = productList.entrySet();
            for (Map.Entry<String, Integer> me : set) {
                strings[i++] = (me.getKey() + " - " + me.getValue());
            }
        }
        return strings;
    }
}
