package Server;

import org.eclipse.swt.widgets.Display;

import java.util.LinkedList;

/**
 * Created by Alexey on 15.03.2017.
 */
public class WorkQueue
{
    private Display display;
    private final int nThreads;
    private final PoolWorker[] threads;
    private final LinkedList queue;

    public WorkQueue(int nThreads, Display display)
    {
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
                            return;
                        }
                    }

                    r = (Runnable) queue.removeFirst();
                }

                // If we don't catch RuntimeException,
                // the pool could leak threads
                try {
                    System.out.println("work...");
                    display.asyncExec(r);
                    System.out.println("_______\n");
                }
                catch (RuntimeException e) {
                    // You might want to log something here
                }
            }
        }
    }
}