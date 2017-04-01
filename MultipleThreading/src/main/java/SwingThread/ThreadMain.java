package SwingThread;

/**
 * Created by Alexey on 31.03.2017.
 */
public class ThreadMain extends Thread{
    private final int SIZE = 10;
     public void run(){
         String[] strings = new String[SIZE];
         StringThread[] threads = new StringThread[SIZE];
         for (int i = 0; i < SIZE; i++){
             strings[i] = "123456789012345678901234567890123456789012345678901234567890";
             threads[i] = new StringThread(strings[i]);
         }
         for (int i = 0; i < SIZE; i++) {
             threads[i].start();
             synchronized (threads[i]) {
                 try {
                     threads[i].wait();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                     return;
                 }
             }
         }
         for (int i = 0; i < SIZE; i++){
             threads[i].interrupt();
         }
     }
}
