package GUI;

import Client.Ship;
import Server.Port;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.StyledText;


public class Window {
    private Port port;
    protected Shell shell;
    private Table portStorageTable;


    Display display;
    Label shipQueueLabel;
    List shipQueueList;
    Label portStorageLabel;
    Label shipListLabel;
    List shipList;
    StyledText logText;
    StyledText doc1Text;
    StyledText doc2Text;
    StyledText doc3Text;


    public static void main(String[] args) {
        try {
            Window window = new Window();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        display = Display.getDefault();
        createContents();
        /*display.syncExec(new Runnable() {
            @Override
            public void run() {
                doc1Text.append("sync input");
            }
        });*/
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    protected void createContents() {
        shell = new Shell();
        shell.setLayout(new FillLayout(SWT.VERTICAL));

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

        portStorageTable = new Table(tableComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
        portStorageTable.setHeaderVisible(true);
        portStorageTable.setLinesVisible(true);

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
        port = new Port(3, texts, display, logText);

        //portStorageTable.
    }

    public void addAction(){
        Ship ship = new Ship();
        port.addInList(ship);
        shipList.add(ship.toString());
        System.out.println("create");
    }
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
    public void addInQueueAction(){
        int index = shipList.getSelectionIndex();
        if (index >= 0) {
            shipQueueList.add(shipList.getItem(index));
            port.addInQueue(index);
            deleteAction();
            System.out.println("add");
        }
    }
    public void closeAction(){
        shell.close();
        port.interrupt();
        System.out.println("close");
    }
    public void runAction(){
        port.start();
    }

}