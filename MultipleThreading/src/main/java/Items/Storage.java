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

    /**
     * Create new empty Storage
     */
    public Storage(){
        productList = new HashMap<String,Integer>();
    }

    //-----------------------Get/Set-------------------------------------------

    /**
     * getting count of product from storage by name
     * @param name - the name of storage
     * @return - count of this product in the storage
     */
    public Integer getCountOfProduct(String name){
        if (productList.containsKey(name)) {
            return (Integer) productList.get(name);
        }
        else {
            return 0;
        }
    }

    /**
     * getting keySet of the Storage
     * @return - keySet of this Storage
     */
    public Set<String> getKeySet(){
        return this.productList.keySet();
    }

    //-----------------------Methods-------------------------------------------

    /**
     * method for adding new product into Storage
     * @param prod - the name of product
     * @param count - count of this product
     * @return true - if storage not contain this product
     *        false - if storage contain this product
     */
    public boolean put(String prod, Integer count) {
        if (!productList.containsKey(prod)) {
            productList.put(prod, count);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * method for deleting product from storage by name
     * @param prod - the name of the deleting product
     * @return true - if product was consist in the storage and was deleting successfully
     *        false - if product wasn't consist in the storage
     */
    public boolean delete(String prod){
        if (productList.containsKey(prod)) {
            productList.remove(prod);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * method for adding/subbing count of product in this storage
     * @param prod - the name of changed product
     * @param value - the delta for changed count of product
     * @return true - if product consist in the storage and adding was successfully
     *        false - if product not consist in the storage
     */
    public boolean addCount(String prod,int value){
        if (productList.containsKey(prod)){
            Integer i = (Integer) productList.get(prod);
            productList.remove(prod);
            i += value;
            productList.put(prod,i);
            return true;
        }
        else {
            if (value >= 0) {
                put(prod, value);
            }
            return false;
        }
    }

    /**
     * method for checking consist of the product in the storage
     * @param prod - the name of the checked product
     * @return true - if storage contain this product
     *        false - if storage doesn't contain this product
     */
    public boolean isContain(String prod){
        return productList.containsKey(prod);
    }

    /**
     * method for clear storage (removing all elements)
     */
    public void removeAll(){
        productList.clear();
    }

    /**
     * method for getting keys and values in format KEY " - " VALUE
     * @return - the array of Strings in format KEY " - " VALUE
     */
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

    /**
     * method for transform storage into String
     * @return - the result of transform
     */
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
