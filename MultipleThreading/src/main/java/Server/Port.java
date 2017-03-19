package Server;

import Client.Ship;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static java.lang.Thread.sleep;
import static java.lang.Thread.yield;

/**
 * Created by Alexey on 17.03.2017.
 */
public class Port extends Thread{
    private ArrayList<Doc> docList;
    private Logger logger;
    private Display display;
    private StyledText logText;
   // private int docCount;
    private LinkedList<Ship> shipList = new LinkedList<>();
    private WorkQueue workQueue;
    private final LinkedList<Ship> shipQueue = new LinkedList<>();

    //-----------------------Constructors--------------------------------------

    public Port(int doc_count, StyledText text[], Display display, StyledText logText){
        this.logText = logText;
        //this.docCount = doc_count;
        this.display = display;
        workQueue = new WorkQueue(doc_count,display);
        docList = new ArrayList<>(doc_count);
        for (int i = 0; i < doc_count; i++){
            Doc doc = new Doc(text[i]);
            docList.add(doc);
        }
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
                synchronized (shipQueue) {
                    while (shipQueue.isEmpty()) {
                        try {
                            shipQueue.wait();
                        } catch (InterruptedException ignored) {
                            return;
                        }
                    }
                    serviceShip(getPriorityShip());
                }
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
