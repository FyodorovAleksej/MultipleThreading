package Server;

import org.eclipse.swt.widgets.Display;

import java.util.LinkedList;

/**
 * Created by Alexey on 15.03.2017.
 * User's Semaphore
 */
public class WorkQueue
{
    private Display display;
    private final int nThreads;
    private final PoolWorker[] threads;
    private final LinkedList queue;

    public WorkQueue(int nThreads, Display display) {
        this.display = display;
        this.nThreads = nThreads;
        queue = new LinkedList();
        threads = new PoolWorker[nThreads];

        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public void execute(Runnable r) {
        synchronized(queue) {
            queue.addLast(r);
            queue.notify();
        }
    }

    private class PoolWorker extends Thread {
        public void run() {
            Runnable r;
            while (true) {
                synchronized(queue) {
                    while (queue.isEmpty()) {
                        try
                        {
                            queue.wait();
                        }
                        catch (InterruptedException ignored)
                        {
                            System.out.println("Interrupt thread queue");
                            return;
                        }
                    }
                    r = (Runnable) queue.removeFirst();
                }

                try {
                    System.out.println("work...");
                    r.run();
                    System.out.println("_______\n");
                }
                catch (RuntimeException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}