package Server;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;

import java.util.ArrayList;

/**
 * Created by Alexey on 19.03.2017.
 */
public class Logger extends Thread {
    private final StyledText logtext;
    private Display display;
    private ArrayList<Doc> docs;
    public Logger(ArrayList<Doc> docs, StyledText logtext, Display display) {
        this.display = display;
        this.logtext = logtext;
        this.docs = docs;
    }
    public void run(){
        //byte flag = 0;
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
                e.printStackTrace();
            }
        }
    }
}
