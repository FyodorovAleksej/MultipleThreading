package Server;

import Items.Storage;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;

import java.util.ArrayList;

/**
 * Created by Alexey on 19.03.2017.
 * Class for Logging work of Port and Docs in file and GUI
 */
public class Logger extends Thread {

    //-----------------------Objects-------------------------------------------

    private final StyledText logtext;
    private Display display;
    private ArrayList<Doc> docs;
    public Logger(ArrayList<Doc> docs, StyledText logtext, Display display) {
        this.display = display;
        this.logtext = logtext;
        this.docs = docs;
    }

    //-----------------------Methods-------------------------------------------

    /**
     * work of the Logger
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
                        logtext.append(finalBuffer);
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
