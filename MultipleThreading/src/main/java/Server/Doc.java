package Server;

import Client.Ship;
import Items.Storage;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;

import static java.lang.Thread.sleep;

/**
 * Created by Alexey on 17.03.2017.
 * Class of Doc, that consist's in Port
 */
public class Doc extends Thread{

    //-----------------------Objects-------------------------------------------

    private final Display display;
    private static int count = 0;
    private final StyledText text;
    final private Storage storage;
    private int number;
    private Ship currentShip = null;

    //-----------------------Constructors--------------------------------------

    /**
     * Create Doc with storage from the Port, and GUI element for logging
     * @param styledText - the GUI element for display all working studies
     * @param storage - Storage of Port
     */
    public Doc(StyledText styledText,Storage storage, Display display){
        this.text = styledText;
        number = count;
        count++;
        this.storage = storage;
        this.display = display;
    }

    //-----------------------Get/Set-------------------------------------------

    /**
     * method for set ship for working
     * @param ship - ship for working
     */
    public void setCurrentShip(Ship ship){
        this.currentShip = ship;
    }

    /**
     * method for getting current working ship
     * @return - current ship
     *    null - if Doc is free
     */
    public Ship getCurrentShip(){
        return this.currentShip;
    }

    //-----------------------Methods-------------------------------------------

    /**
     * method for check if Doc is free
     * @return true - if Doc is free
     *        false - if Doc is busy
     */
    public boolean isFree(){
        return (currentShip == null);
    }

    /**
     * the work of Doc thread
     */
    @Override
    public void run() {
        display.syncExec(new Runnable() {
            @Override
            public void run() {
                synchronized (text) {
                    text.append("doc" + number + " run with " + getCurrentShip().toString() + "\n");
                }
            }
        });
        try {
            boolean flag;
            synchronized (storage){
                flag = currentShip.controlCheck(this.storage);
            }
            if (flag){
                sleep(currentShip.countOfOperations(this.storage)*100);
                synchronized (storage) {
                    currentShip.controlGetStorage(this.storage);
                }
                display.syncExec(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (text) {
                            text.append("doc" + number + " finish run with " + getCurrentShip().toString() + "\n");
                        }
                    }
                });
            }
            else{
                display.syncExec(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (text) {
                            text.append("doc" + number + " don't service " + getCurrentShip().toString() + "\n");
                        }
                    }
                });
            }

        } catch (InterruptedException e) {
            setCurrentShip(null);
            return;
        }
        setCurrentShip(null);
    }

    /**
     * method for transform Doc in String
     * @return - the result of transform String
     */
    public String toString(){
        if (currentShip == null){
            return "doc" + this.number + " is free\n";
        }
        else {
            return "doc" + this.number + " is working with " + currentShip + "\n";
        }
    }
}
