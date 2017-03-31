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
public class Doc extends Thread{
    //-----------------------Objects-------------------------------------------
    private static int count = 0;
    private final StyledText text;
    final private Storage storage;
    private int number;
    private Ship currentShip = null;

    //-----------------------Constructors--------------------------------------
    public Doc(StyledText t,Storage storage){
        this.text = t;
        number = count;
        count++;
        this.storage = storage;
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
            boolean flag;
            synchronized (storage){
                flag = currentShip.controlGetStorage(this.storage);
            }
            if (flag){
                sleep(currentShip.countOfOperations(this.storage)*100);
                synchronized (text) {
                    text.append("doc" + number + " finish run with " + getCurrentShip().toString() + "\n");
                }
            }
            else{
                synchronized (text) {
                    text.append("doc" + number + " don't service " + getCurrentShip().toString() + "\n");
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            setCurrentShip(null);
            return;
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
