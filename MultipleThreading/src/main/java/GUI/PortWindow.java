package GUI;

import Client.Ship;
import Server.Port;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.StyledText;

/**
 * Class of PortWindow for show out work of the Port
 * Created by Alexey on 17.03.2017.
 */

public class PortWindow {

    //-----------------------Objects-------------------------------------------

    private Port port;
    protected Shell shell;
    private List portStorageTable;


    private Display display;
    private Label shipQueueLabel;
    private List shipQueueList;
    private Label portStorageLabel;
    private Label shipListLabel;
    private List shipList;
    private StyledText logText;
    private StyledText doc1Text;
    private StyledText doc2Text;
    private StyledText doc3Text;

    //-----------------------Constructors--------------------------------------

    /**
     * Create window for display work of the Port
     */
    public PortWindow() {
        display = Display.getDefault();
        shell = new Shell(display);
        shell.setLayout(new FillLayout(SWT.VERTICAL));
        shell.addListener(SWT.CLOSE, new Listener() {
            @Override
            public void handleEvent(Event event) {
                closeAction();
            }
        });
        shell.open();

        Composite northComposite = new Composite(shell, SWT.NONE);
        northComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

        Composite shipQueueComposite = new Composite(northComposite, SWT.NONE);
        shipQueueComposite.setLayout(new FillLayout(SWT.VERTICAL));

        shipQueueLabel = new Label(shipQueueComposite, SWT.NONE);
        shipQueueLabel.setText("ship queue in port");

        shipQueueList = new List(shipQueueComposite, SWT.BORDER | SWT.V_SCROLL);

        Composite tableComposite = new Composite(northComposite, SWT.NONE);
        tableComposite.setLayout(new FillLayout(SWT.VERTICAL));

        portStorageLabel = new Label(tableComposite, SWT.NONE);
        portStorageLabel.setText("port storage");

        portStorageTable = new List(tableComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);

        Composite shipListComposite = new Composite(northComposite, SWT.NONE);
        shipListComposite.setLayout(new FillLayout(SWT.VERTICAL));

        shipListLabel = new Label(shipListComposite, SWT.NONE);
        shipListLabel.setText("ship list");

        shipList = new List(shipListComposite, SWT.BORDER | SWT.V_SCROLL | SWT.DefaultSelection);

        Composite centerComposite = new Composite(shell, SWT.NONE);
        centerComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

        Composite logComposite = new Composite(centerComposite, SWT.NONE);
        logComposite.setLayout(new FillLayout(SWT.VERTICAL));

        logText = new StyledText(logComposite, SWT.BORDER | SWT.V_SCROLL);

        Composite southComposite = new Composite(shell, SWT.NONE);
        southComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

        Composite doc1Composite = new Composite(southComposite, SWT.NONE);
        doc1Composite.setLayout(new FillLayout(SWT.HORIZONTAL));

        doc1Text = new StyledText(doc1Composite, SWT.BORDER | SWT.V_SCROLL);

        Composite doc2Composite = new Composite(southComposite, SWT.NONE);
        doc2Composite.setLayout(new FillLayout(SWT.HORIZONTAL));

        doc2Text = new StyledText(doc2Composite, SWT.BORDER | SWT.V_SCROLL);

        Composite doc3Composite = new Composite(southComposite, SWT.NONE);
        doc3Composite.setLayout(new FillLayout(SWT.VERTICAL));

        doc3Text = new StyledText(doc3Composite, SWT.BORDER | SWT.V_SCROLL);

        Composite buttonsComposite = new Composite(southComposite, SWT.NONE);
        buttonsComposite.setLayout(new FillLayout(SWT.VERTICAL));

        Button addShipButton = new Button(buttonsComposite, SWT.NONE);
        addShipButton.setText("add ship");
        addShipButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                addAction();
            }
        });

        Button addInQueueButton = new Button(buttonsComposite, SWT.NONE);
        addInQueueButton.setText("add in queue");
        addInQueueButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                addInQueueAction();
            }
        });

        Button deleteButton = new Button(buttonsComposite, SWT.NONE);
        deleteButton.setText("delete ship");
        deleteButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                deleteAction();
            }
        });

        Button runButton = new Button(buttonsComposite, SWT.NONE);
        runButton.setText("run");
        runButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                runAction();
            }
        });

        Button closeButton = new Button(buttonsComposite, SWT.NONE);
        closeButton.setText("close");
        closeButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                closeAction();
            }
        });
        StyledText[] texts = new StyledText[3];
        texts[0] = doc1Text;
        texts[1] = doc2Text;
        texts[2] = doc3Text;
        port = new Port(3, texts, display, logText, portStorageTable);

        //portStorageTable.
    }

    //-----------------------Methods-------------------------------------------

    /**
     * method for open this window
     */
    public void open() {
        display = Display.getDefault();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    //-----------------------Actions-------------------------------------------

    /**
     * action, that was performed, than button "Add" was pressed
     */
    public void addAction(){
        ShipWindow shipWindow = new ShipWindow();
        Ship createdShip = shipWindow.open();
        if (createdShip != null) {
            port.addInList(createdShip);
            shipList.add(createdShip.toString());
            System.out.println("create");
        }
    }

    /**
     * action, that was performed, than button "Delete" was pressed
     */
    public void deleteAction(){
        int i = shipList.getSelectionIndex();
        if (i >= 0) {
            port.removeInList(shipList.getSelectionIndex());
            shipList.remove(shipList.getSelectionIndex());
            if (i == shipList.getItemCount()) {
                shipList.select(i - 1);
            }
            if (i != 0 || shipList.getItemCount() > 0){
                shipList.select(i);
            }
        }
        System.out.println("delete");
    }

    /**
     * action, that was performed, than button "Add in Queue" was pressed
     */
    public void addInQueueAction(){
        int index = shipList.getSelectionIndex();
        if (index >= 0) {
            shipQueueList.add(shipList.getItem(index));
            port.addInQueue(index);
            deleteAction();
            System.out.println("add");
        }
    }

    /**
     * action, that was performed, than button "Close" was pressed
     */
    public void closeAction(){
        shell.close();
        shell.dispose();
        port.interrupt();
        System.out.println("close");
    }

    /**
     * action, that was performed, than button "Run" was pressed
     */
    public void runAction(){
        port.start();
    }
}