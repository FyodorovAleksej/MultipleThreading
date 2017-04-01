package Client;

import Items.ControlStorage;
import Items.Storage;

/**
 * Class of users Object - ship, that extends from class Storage
 * Created by Alexey on 17.03.2017.
 */

public class Ship extends ControlStorage {

    //-----------------------Objects-------------------------------------------

    private String name;
    private static int count = 0;
    private QueuePriority priority = QueuePriority.USUAL;
    private long time = 5000;
    private boolean isUseTimer = false;
    private boolean isFine = false;
    private boolean isUsePriority = false;

    //-----------------------Constructors--------------------------------------

    /**
     * Create ship with defaults arguments:
     *  - name = count + "Ship"
     *  - priority = USUAL (2)
     *  - add in storage default values
     */
    public Ship(){
        this.name = "ship" + count;
        count++;
        this.defaultInitialize();
    }

    /**
     * Create ship with defaults arguments:
     *  - name = count + "Ship"
     *  - add in storage default values
     *  @param priority - the priority of ship
     */
    public Ship(QueuePriority priority){
        this.name = "ship" + count;
        count++;
        this.defaultInitialize();
        this.priority = priority;
    }

    /**
     * Create ship with defaults arguments:
     *  - priority = USUAL (2)
     *  - add in storage default values
     *  @param name - the name ships
     */
    public Ship(String name){
        this.name = name;
        this.defaultInitialize();
        this.priority = QueuePriority.USUAL;
    }

    /**
     * Create ship with defaults arguments:
     *  - add in storage default values
     *  @param priority - the priority of ship
     *  @param name - the name of the ship
     */
    public Ship(QueuePriority priority, String name){
        this.name = name;
        this.defaultInitialize();
        this.priority = priority;
    }

    //-----------------------Get/Set-------------------------------------------

    /**
     * method for getting int value of priority of this ship
     * @return - the value of priority
     */
    public int getPriority() {
        if (this.isUsePriority()) {
            return priority.getPriority();
        }
        else return 0;
    }

    /**
     * method for setting int value of priority
     * @param priority - new value of priority
     */
    public void setPriority(QueuePriority priority) {
        this.priority = priority;
    }


    /**
     * method for getting name of the ship
     * @return - String name of this Ship
     */
    public String getName(){
        return this.name;
    }

    /**
     * method for setting name of this Ship
     * @param name - new String for name of this Ship
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * get Time from timer
     * @return if use priority - the value of timer
     *       if usn't priority - -1
     */
    public long getTime() {
        if (this.isUsePriority()) {
            return time;
        }
        else return  -1;
    }

    /**
     * method for setting new value of time
     * @param time - the new value of time for timer
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     *
     * @return true - if ship use timer
     *        false - if ship usn't timer
     */
    public boolean isUseTimer() {
        return isUseTimer;
    }

    /**
     * method for setting using timer
     * @param useTimer - tne new value of UseTimer
     */
    public void setUseTimer(boolean useTimer) {
        isUseTimer = useTimer;
    }

    /**
     * method for checking fine of ship
     * @return true - ship has fine
     *        false - ship hasn't fine
     */
    public boolean isFine() {
        return isFine;
    }

    /**
     * method for setting fine of this ship
     * @param fine - new value of fine
     */
    public void setFine(boolean fine) {
        isFine = fine;
    }

    /**
     * method for checking Using priority
     * @return true - if ship use priority
     *        false - if ship usn't priority
     */
    public boolean isUsePriority() {
        return isUsePriority;
    }

    /**
     * method for setting value of using priority
     * @param usePriority - the new value of Using priority
     */
    public void setUsePriority(boolean usePriority) {
        isUsePriority = usePriority;
    }

    //-----------------------Methods-------------------------------------------

    /**
     * Default Initialization of storage
     * Milk - 40
     * Wood - 60
     */
    public void defaultInitialize() {
        this.addCount("Milk",40);
        this.addCount("Wood",60);
    }

    /**
     * Method for transform Ship into String
     * @return - the transformed String
     */
    public String toString(){
        return this.name;
    }

}
