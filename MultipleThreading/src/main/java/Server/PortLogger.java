package Server;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;

import java.util.ArrayList;

/**
 * Created by Alexey on 19.03.2017.
 * Class for Logging work of Port and Docs in file and GUI
 */
public class PortLogger extends Thread {

    //-----------------------Objects-------------------------------------------

    private final StyledText logText;
    private Display display;
    private ArrayList<Doc> docs;

    //-----------------------Constructor---------------------------------------

    /**
     * Create the Port Logger
     * @param docs - the array of docs
     * @param logText - The GUI element of text
     * @param display - The GUI Display for Refresh GUI elements
     */
    public PortLogger(ArrayList<Doc> docs, StyledText logText, Display display) {
        this.display = display;
        this.logText = logText;
        this.docs = docs;
    }

    //-----------------------Methods-------------------------------------------

    /**
     * work of the PortLogger
     */
    public void run(){
        String buffer;
        while (true) {
            for (Doc i : docs) {
                buffer = i.toString();
                final String finalBuffer = buffer;
                display.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        logText.append(finalBuffer);
                    }
                });
            }
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
