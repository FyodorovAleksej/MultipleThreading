package Client;

import Items.Storage;

/**
 * Created by Alexey on 17.03.2017.
 */
public class Ship extends Storage{
    private String name;
    private static int count = 0;
    private int priority = 0;

    public Ship(){
        this.name = "ship" + count;
        count++;
        this.addCount("Milk",40);
        this.addCount("Wood",60);
    }
    public Ship(int priority){
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
        this.priority = 0;
    }
    public Ship(int priority, String name){
        this.name = name;
        this.addCount("Milk",40);
        this.addCount("Wood",60);
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String toString(){
        return this.name;
    }
}
