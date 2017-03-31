package Client;

import Items.ControlStorage;
import Items.Storage;

/**
 * Created by Alexey on 17.03.2017.
 */
public class Ship extends ControlStorage {
    private String name;
    private static int count = 0;
    private QueuePriority priority = QueuePriority.USUAL;
    private long time = 5000;
    private boolean isUseTimer = false;
    private boolean isFine = false;
    private boolean isUsePriority = false;

    public Ship(){
        this.name = "ship" + count;
        count++;
        this.addCount("Milk",40);
        this.addCount("Wood",60);
    }
    public Ship(QueuePriority priority){
        this.name = "ship" + count;
        count++;
        this.addCount("Milk",40);
        this.addCount("Wood",60);
        this.priority = priority;
    }
    public Ship(String name){
        this.name = name;
        this.addCount("Milk",40);
        this.addCount("Wood",60);
        this.priority = QueuePriority.USUAL;
    }
    public Ship(QueuePriority priority, String name){
        this.name = name;
        this.addCount("Milk",40);
        this.addCount("Wood",60);
        this.priority = priority;
    }

    public int getPriority() {
        if (this.isUsePriority()) {
            return priority.getPriority();
        }
        else return 0;
    }

    public void setPriority(QueuePriority priority) {
        this.priority = priority;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String toString(){
        return this.name;
    }


    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }

    public boolean isUseTimer() {
        return isUseTimer;
    }
    public void setUseTimer(boolean useTimer) {
        isUseTimer = useTimer;
    }

    public boolean isFine() {
        return isFine;
    }
    public void setFine(boolean fine) {
        isFine = fine;
    }

    public boolean isUsePriority() {
        return isUsePriority;
    }
    public void setUsePriority(boolean usePriority) {
        isUsePriority = usePriority;
    }
}
