package Items;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexey on 25.03.2017.
 * Class of modified storage by control List - the priority State for Storage
 */
public class ControlStorage extends Storage {

    //-----------------------Objects-------------------------------------------

    private Map<String, Integer> controlMap;

    //-----------------------Constructors--------------------------------------

    /**
     * Constructor of Control Storage - initialize by empty Storage
     *                                            and empty Control List
     */
    public ControlStorage(){
        super();
        controlMap = new HashMap<String, Integer>();
    }

    //-----------------------Get/Set-------------------------------------------

    /**
     * method for getting Control List
     * @return - Control list of current ControlStorage
     */
    public Map<String, Integer> getControlMap() {
        return controlMap;
    }

    /**
     * method for search and getting count of product by name
     * @param key - the name of the find product
     * @return - the count of founded product
     */
    public Integer getCountOfControl(String key) {
        if (controlMap.containsKey(key)) {
            return controlMap.get(key);
        }
        else {
            return 0;
        }
    }

    //-----------------------Methods-------------------------------------------

    /**
     * method for adding/subbing count of product in control list
     * @param key - the name of product
     * @param count - delta for adding with count
     * @return true - if product find in list and was added
     *        false - if product don't find in list and was added
     */
    public boolean addControlCount(String key, Integer count){
        if (controlMap.containsKey(key)){
            Integer i = controlMap.get(key);
            controlMap.remove(key);
            if (count >= 0) {
                i += count;
            }
            controlMap.put(key,i);
            return true;
        }
        else {
            controlMap.put(key, count);
            return false;
        }
    }

    /**
     * method for checking allow sending products between 2 storage by 1 control List
     * @param storage - storage, that was putting delays of control Storage
     * @return true - if all delays are possible
     *        false - if not all delays are possible
     */
    public boolean controlCheck(Storage storage){
        for (String i : controlMap.keySet()) {
            if (this.getCountOfProduct(i) < this.getCountOfControl(i)) {
                if (storage.getCountOfProduct(i) < (this.getCountOfControl(i) - this.getCountOfProduct(i))){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * method for sending products between 2 storage by 1 control List
     * @param storage - storage, that was putting delays of control Storage
     * @return true - if all delays are possible and ending successfully
     *        false - if not all delays are possible
     */
    public boolean controlGetStorage(Storage storage){
        if (controlCheck(storage)) {
            for (String i : controlMap.keySet()) {
                Integer count = this.getCountOfProduct(i);
                Integer control = this.getCountOfControl(i);
                if (count > control) {
                    storage.addCount(i, count - control);
                    this.addCount(i, control - count);
                }
                if (count < control) {
                    storage.addCount(i, count - control);
                    this.addCount(i, control - count);
                }
            }
            String[] keys = new String[productList.keySet().size()];
            keys = productList.keySet().toArray(keys);
            for (String i : keys) {
                if (!controlMap.keySet().contains(i)) {
                    Integer count = this.getCountOfProduct(i);
                    Integer control = this.getCountOfControl(i);
                    storage.addCount(i, count - control);
                    this.addCount(i, control - count);
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * method for getting summary all delays between 2 storage by 1 control List
     * @param storage - storage, that was putting delays of control Storage
     * @return - sum of all delays, if it possible
     *                          0 - if it impossible
     */
    public int countOfOperations(Storage storage){
        int result = 0;
        if (controlCheck(storage)) {
            for (String i : controlMap.keySet()) {
                Integer count = this.getCountOfProduct(i);
                Integer control = this.getCountOfControl(i);
                if (count > control) {
                    result += count - control;
                } else {
                    result += control - count;
                }
            }
            String[] keys = new String[productList.keySet().size()];
            keys = productList.keySet().toArray(keys);
            for (String i : keys) {
                if (!controlMap.keySet().contains(i)) {
                    Integer count = this.getCountOfProduct(i);
                    Integer control = this.getCountOfControl(i);
                    result += count - control;
                }
            }
        }
        return result;
    }
}
