import Client.ClientThread;
import Server.WorkQueue;
import org.eclipse.swt.*;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

/**
 * Created by Alexey on 15.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        final WorkQueue queue = new WorkQueue(1,Display.getDefault());

        final Display display = Display.getDefault();
        final Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        int style = SWT.BORDER;
        Button test = new Button(shell,style);
        test.setText("test");
        test.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                queue.execute(new ClientThread());
            }
        });
        style = SWT.CENTER;
        Label label = new Label(shell, style);
        Browser browser;
        style = SWT.CENTER;
        browser = new Browser(shell, style);
        browser.setText("text <b>text</b> <i>text</i>");;
        shell.setSize(300, 200);
        label.setText("Hello World");
        label.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
    }
}
