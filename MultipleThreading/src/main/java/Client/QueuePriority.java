package Client;

/**
 * Created by Alexey on 21.03.2017.
 * Enum for Describe the priority of Ships
 */

public enum QueuePriority {

    //-----------------------Values--------------------------------------------

    GRANIT("GRANIT",5), ROCKET("ROCKET", 4), BEREZA("BEREZA",3), USUAL("USUAL",2), DUTY("DUTY",1);

    //-----------------------Objects-------------------------------------------

    private String name;
    private int priority;

    //-----------------------Constructors--------------------------------------

    /**
     * Create Object of this enum by name and priority value
     * @param name - name of enum Priority object
     * @param priority - int value of priority
     */
    QueuePriority(String name, int priority){
        this.name = name;
        this.priority = priority;
    }

    //-----------------------Get/Set-------------------------------------------

    /**
     * get int value of priority from this object of enum
     * @return - int value of priority
     */
    public int getPriority(){
        return this.priority;
    }

    //-----------------------Methods-------------------------------------------

    /**
     * transform enum object to String
     * @return - transformed String
     */
    public String toString(){
        return this.name;
    }
}
