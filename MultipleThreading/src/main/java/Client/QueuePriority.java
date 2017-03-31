package Client;

/**
 * Created by Alexey on 21.03.2017.
 */
public enum QueuePriority {
    GRANIT("GRANIT",5), ROCKET("ROCKET", 4), BEREZA("BEREZA",3), USUAL("USUAL",2), DUTY("DUTY",1);
    private String name;
    private int priority;
    QueuePriority(String name, int prioriy){
        this.name = name;
        this.priority = prioriy;
    }

    public String toString(){
        return this.name;
    }

    public int getPriority(){
        return this.priority;
    }
}
