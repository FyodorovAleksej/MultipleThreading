package Server;

import org.apache.logging.log4j.*;
import java.util.LinkedList;

/**
 * Created by Alexey on 15.03.2017.
 * User's Semaphore
 */
public class WorkQueue
{

            private class PoolWorker extends Thread {

                //-------------------Objects-------------------------------------------

                private final Logger log = LogManager.getLogger(PoolWorker.class);

                //-------------------Run-----------------------------------------------

                public void run() {
                    Runnable runnable;
                    while (true) {
                        synchronized (queue) {
                            while (queue.isEmpty()) {
                                try {
                                    queue.wait();
                                } catch (InterruptedException ignored) {
                                    System.out.println("Interrupt thread queue");
                                    return;
                                }
                            }
                            runnable = (Runnable) queue.removeFirst();
                        }

                        try {
                            log.info("Start Pool Worker");
                            runnable.run();
                            log.info("End Pool Worker");
                        } catch (RuntimeException e) {
                            log.error(e.getMessage());
                            return;
                        }
                    }
                }
            }

    //-----------------------Objects-------------------------------------------

    private final int nThreads;
    private final PoolWorker[] threads;
    private final LinkedList queue;

    //-----------------------Constructors--------------------------------------

    public WorkQueue(int nThreads) {
        this.nThreads = nThreads;
        queue = new LinkedList();
        threads = new PoolWorker[nThreads];

        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    //-----------------------Methods-------------------------------------------

    public void execute(Runnable r) {
        synchronized(queue) {
            queue.addLast(r);
            queue.notify();
        }
    }

}