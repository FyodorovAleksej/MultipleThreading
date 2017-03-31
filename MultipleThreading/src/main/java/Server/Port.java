package Server;

import Client.Ship;
import Items.Storage;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static java.lang.Thread.sleep;
import static java.lang.Thread.yield;

/**
 * Created by Alexey on 17.03.2017.
 */
public class Port extends Thread {
    private ArrayList<Doc> docList;
    private Logger logger;
    private final Storage storage;
    private Display display;
    private StyledText logText;
   // private int docCount;
    private final List portStorageTable;
    private LinkedList<Ship> shipList = new LinkedList<>();
    private WorkQueue workQueue;
    private final LinkedList<Ship> shipQueue = new LinkedList<>();

    //-----------------------Constructors--------------------------------------

    public Port(int doc_count, StyledText text[], Display display, StyledText logText, List portStorageTable){
        this.storage = new Storage();
        this.initialize();
        this.logText = logText;
        //this.docCount = doc_count;
        this.display = display;
        this.portStorageTable = portStorageTable;
        workQueue = new WorkQueue(doc_count,display);
        docList = new ArrayList<>(doc_count);
        for (int i = 0; i < doc_count; i++){
            Doc doc = new Doc(text[i],this.storage);
            docList.add(doc);
        }
    }

    private void initialize() {
        this.storage.addCount("Milk",60);
        this.storage.addCount("Wood",60);
        this.storage.addCount("Silk",60);
    }

    //-----------------------Methods-------------------------------------------

    public boolean serviceShip(Ship ship){
        for (Doc i : docList){
            if (i.isFree()){
                i.setCurrentShip(ship);
                workQueue.execute(i);
                shipQueue.remove(ship);
                return true;
            }
        }
        return false;
    }

    public void addInQueue(Ship ship){
        synchronized (shipQueue) {
            shipQueue.addLast(ship);
            shipQueue.notify();
        }
    }

    public void addInQueue(int index){
        addInQueue(shipList.get(index));
    }

    public Ship getPriorityShip(){
        int max = 0;
        Ship j = shipQueue.get(0);
        for (Ship i : shipQueue){
            if (i.getPriority() > max){
                j = i;
                max = i.getPriority();
            }
        }
        return j;
    }

    @Override
    public void run() {
        logger = new Logger(docList, logText, display);
        logger.start();
            while (true) {
                display.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        portStorageTable.removeAll();
                        for (String i: storage.out()) {
                            portStorageTable.add(i);
                        }
                    }
                });
                synchronized (shipQueue) {
                    while (shipQueue.isEmpty()) {
                        try {
                            shipQueue.wait();
                        } catch (InterruptedException ignored) {
                            logger.interrupt();
                            for (Doc i : docList){
                                i.interrupt();
                            }
                            return;
                        }
                    }
                    serviceShip(getPriorityShip());
                }
                final String[] strings = storage.out();
                display.syncExec(new Runnable() {
                    @Override
                    public void run() {
                        for (String i : strings) {
                            portStorageTable.add(i);
                        }
                    }
                });
            }
        //System.out.println("Port exit");
    }
    public void addInList(Ship ship){
        shipList.add(ship);
    }
    public void removeInList(int index){
        shipList.remove(index);
    }
}
