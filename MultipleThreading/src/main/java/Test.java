import Client.QueuePriority;
import Client.Ship;
import Items.ControlStorage;
import Items.Storage;
import Server.Port;
import junit.framework.TestCase;

import static java.lang.Thread.sleep;

/**
 * Created by Alexey on 18.03.2017.
 */
public class Test extends TestCase {
    private Storage storage = new Storage();

    @Override
    protected void setUp() throws Exception {
        assertTrue(storage.put("Silk",20));
        assertTrue(storage.put("Soda",40));
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        storage.removeAll();
        super.tearDown();
    }

    public void testProd1(){
        assertTrue(storage.put("Wood",90));
        assertTrue(storage.put("Steel",80));
        assertTrue(!storage.put("Silk",60));
        assertTrue(!storage.put("Soda",20));
    }
    public void testProd2(){
        assertEquals(storage.getCountOfProduct("Silk").intValue(),20);
        storage.removeAll();
        assertTrue(storage.put("Silk",40));
        assertTrue(storage.put("Soda",50));
        assertEquals(storage.getCountOfProduct("Silk").intValue(),40);
        storage.removeAll();
    }
    public void testProd3(){
        assertTrue(storage.delete("Silk"));
        assertTrue(storage.put("Silk",70));
        assertTrue(storage.getCountOfProduct("Silk").equals(70));
    }
    public void testProd4(){
        assertTrue(storage.addCount("Silk",-10));
        assertTrue(storage.getCountOfProduct("Silk").equals(10));
        System.out.println(storage);
    }

    public void testStorage1(){
        Ship ship = new Ship();
        Ship ship1 = new Ship();
        Ship ship2 = new Ship();
        Ship ship3 = new Ship();
        Ship ship4 = new Ship();
        Ship ship5 = new Ship(QueuePriority.ROCKET);
        Ship ship6 = new Ship();
        Ship ship7 = new Ship();
        Ship ship8 = new Ship();
        Ship ship9 = new Ship();

        /*Port port = new Port(2);

       port.addInQueue(ship);
       port.addInQueue(ship1);
       port.addInQueue(ship2);
       port.addInQueue(ship3);
       port.addInQueue(ship4);
       port.addInQueue(ship5);
       port.setDaemon(true);
        port.start();

        port.addInQueue(ship6);
        port.addInQueue(ship7);
        port.addInQueue(ship8);
        port.addInQueue(ship9);

        try {
            port.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }



    public void testStorage2(){
    }

    public void testControlStorage1(){
        ControlStorage controlStorage = new ControlStorage();

        assertTrue(!controlStorage.addControlCount("Silk",20));
        assertTrue(!controlStorage.addControlCount("Wood",0));
        assertTrue(!controlStorage.addControlCount("Soda",30));
        assertTrue(controlStorage.addControlCount("Soda",5));

        assertTrue(!controlStorage.addCount("Wood",40));

        assertTrue(controlStorage.controlGetStorage(storage));

        assertTrue(controlStorage.getCountOfProduct("Wood").equals(0));
        assertTrue(storage.getCountOfProduct("Soda").equals(5));
    }
    public void testControlStorage2(){
        ControlStorage controlStorage = new ControlStorage();

        assertTrue(!controlStorage.addControlCount("Silk",20));
        assertTrue(!controlStorage.addControlCount("Wood",0));
        assertTrue(!controlStorage.addControlCount("Soda",60));

        assertTrue(!controlStorage.addCount("Wood",40));

        assertEquals(controlStorage.countOfOperations(storage),0);

        assertTrue(!controlStorage.controlGetStorage(storage));
    }
    public void testControlStorage3(){
        ControlStorage controlStorage = new ControlStorage();

        assertTrue(!controlStorage.addControlCount("Silk",20));
        assertTrue(!controlStorage.addControlCount("Soda",60));
       // assertTrue(!controlStorage.addControlCount("Wood",0));

        assertTrue(!controlStorage.addCount("Soda",20));
        assertTrue(!controlStorage.addCount("Wood",40));

        Storage controlStorage2 = new Storage();

        assertTrue(!controlStorage2.addCount("Wood",60));
        assertTrue(!controlStorage2.addCount("Silk",60));
        assertTrue(!controlStorage2.addCount("Soda",60));

        System.out.println(controlStorage2 + "\n||\n\\/");

        System.out.println(controlStorage.countOfOperations(controlStorage2));
        controlStorage.controlGetStorage(controlStorage2);

        System.out.println(controlStorage2);
    }

}
