package Server;

import Client.Ship;
import Items.Storage;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.eclipse.swt.custom.StyledText;

import static java.lang.Thread.sleep;

/**
 * Created by Alexey on 17.03.2017.
 */
public class Doc implements Runnable{
    //-----------------------Objects-------------------------------------------
    private static int count = 0;
    private final StyledText text;
    private Storage storage;
    private int number;
    private Ship currentShip = null;

    //-----------------------Constructors--------------------------------------
    public Doc(StyledText t){
        this.text = t;
        number = count;
        count++;
        storage = new Storage();
        storage.put("Wood",40);
        storage.put("Steel",60);
    }

    //-----------------------Get/Set-------------------------------------------

    public void setCurrentShip(Ship ship){
        this.currentShip = ship;
    }

    public Ship getCurrentShip(){
        return this.currentShip;
    }

    //-----------------------Methods-------------------------------------------

    public boolean isFree(){
        return (currentShip == null);
    }

    @Override
    public void run() {
        synchronized (text) {
            text.append("doc" + number + " run with " + getCurrentShip().toString() + "\n");
        }
        try {
            sleep(1240);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (text) {
            text.append("doc" + number + " finish run" + getCurrentShip().toString() + "\n");
        }
        setCurrentShip(null);
    }

    public String toString(){
        if (currentShip == null){
            return "doc" + this.number + " is free\n";
        }
        else {
            return "doc" + this.number + " is working with " + currentShip + "\n";
        }
    }
}
