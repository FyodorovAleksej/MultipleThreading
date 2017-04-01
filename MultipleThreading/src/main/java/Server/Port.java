package Server;

import Client.Ship;
import Items.Storage;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;

import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

/**
 * Created by Alexey on 17.03.2017.
 * Class of Port with 3 Docs
 */
public class Port extends Thread {

    //-----------------------Objects-------------------------------------------

    private ArrayList<Doc> docList;
    private PortLogger portLogger;
    private final Storage storage;
    private Display display;
    private StyledText logText;
    private final List portStorageTable;
    private LinkedList<Ship> shipList = new LinkedList<>();
    private WorkQueue workQueue;
    private final LinkedList<Ship> shipQueue = new LinkedList<>();

    //-----------------------Constructors--------------------------------------

    /**
     * Create Port with Docs
     * @param doc_count - the count of Docs
     * @param text - GUI Text elements for logging Docs's work
     * @param display - Display for refresh GUI elements
     * @param logText - GUI Text element for logging Port's work
     * @param portStorageTable - GUI element for show Storage of Port
     */
    public Port(int doc_count, StyledText text[], Display display, StyledText logText, List portStorageTable){
        this.storage = new Storage();
        this.initialize();
        this.logText = logText;
        this.display = display;
        this.portStorageTable = portStorageTable;
        workQueue = new WorkQueue(doc_count);
        docList = new ArrayList<>(doc_count);
        for (int i = 0; i < doc_count; i++){
            Doc doc = new Doc(text[i],this.storage, this.display);
            docList.add(doc);
        }
    }

    //-----------------------Methods-------------------------------------------

    /**
     * Default initialize the Port's Storage
     */
    private void initialize() {
        this.storage.addCount("Milk",60);
        this.storage.addCount("Wood",60);
        this.storage.addCount("Silk",60);
    }


    /**
     * try to service Ship in the Doc
     * @param ship - current ship
     * @return true - if ship was begin to service
     *        false - if ship wasn't begin to service
     */
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

    /**
     * method for adding in the Queue ship
     * @param ship - the ship to adding in the end of Queue
     */
    public void addInQueue(Ship ship){
        synchronized (shipQueue) {
            shipQueue.addLast(ship);
            shipQueue.notify();
        }
    }

    /**
     * method for adding in the Queue ship from list by index
     * @param index - the index of ship in the Ship List
     */
    public void addInQueue(int index){
        addInQueue(shipList.get(index));
    }

    /**
     * method for getting the most priority ship from the Ship Queue
     * @return - the most priority Ship
     */
    public Ship getPriorityShip(){
        Ship maxShip = shipQueue.get(0);
        int max = maxShip.getPriority();
        for (Ship i : shipQueue){
            if (i.getPriority() > max){
                maxShip = i;
                max = i.getPriority();
            }
        }
        return maxShip;
    }

    /**
     * the work of the Port
     */
    @Override
    public void run() {
        portLogger = new PortLogger(docList, logText, display);
        portLogger.start();
            while (true) {
                display.syncExec(new Runnable() {
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
                            portLogger.interrupt();
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

    /**
     * method for adding in the ShipList new Ship
     * @param ship - the ship to adding in the Ship List
     */
    public void addInList(Ship ship){
        shipList.add(ship);
    }

    /**
     * method for remove ship from the ShipList by the index
     * @param index - the index to remove from the ShipList
     */
    public void removeInList(int index){
        shipList.remove(index);
    }
}
