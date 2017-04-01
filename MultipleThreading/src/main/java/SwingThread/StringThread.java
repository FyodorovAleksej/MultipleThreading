package SwingThread;

/**
 * Created by Alexey on 31.03.2017.
 */
public class StringThread extends Thread {
    private final String buffer;
    private int count;
    public StringThread(String string) {
        buffer = string;
        count = 0;
    }
    public void run() {
        while (count <= 50) {
            System.out.print(buffer.charAt(count));
            this.inc();
        }
        System.out.println("");
        synchronized (buffer){
            buffer.notify();
        }
    }
    void inc(){
        this.count++;
    }
}
