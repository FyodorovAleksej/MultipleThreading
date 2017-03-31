package Items;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexey on 25.03.2017.
 */
public class ControlStorage extends Storage {
    private Map<String, Integer> controlMap;

    public ControlStorage(){
        super();
        controlMap = new HashMap<String, Integer>();
    }


    public Map<String, Integer> getControlMap() {
        return controlMap;
    }

    public void setControlMap(Map<String, Integer> controlMap) {
        this.controlMap = controlMap;
    }

    @Override
    public boolean addCount(String prod, int value) {
       // this.addControlCount(prod,0);
        return super.addCount(prod, value);
    }

    public boolean addControlCount(String key, Integer count){
        if (controlMap.containsKey(key)){
            Integer i = controlMap.get(key);
            controlMap.remove(key);
            i += count;
            controlMap.put(key,i);
            return true;
        }
        else {
            controlMap.put(key, count);
            return false;
        }
    }

    public Integer getCountOfControl(String key) {
        if (controlMap.containsKey(key)) {
            return controlMap.get(key);
        }
        else {
            return 0;
        }
    }

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

    public boolean controlGetStorage(Storage storage){
        if (controlCheck(storage)) {
            for (String i : controlMap.keySet()) {
                Integer count = this.getCountOfProduct(i);
                Integer control = this.getCountOfControl(i);
                if (count > control) {
                    storage.addCount(i, count - control);
                    this.subCount(i, count - control);
                }
                if (count < control) {
                    storage.subCount(i, control - count);
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
                    this.subCount(i, count - control);
                }
            }
            return true;
        }
        else {
            return false;
        }
    }
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
