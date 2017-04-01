package GUI;
import Client.QueuePriority;
import Client.Ship;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;

import java.util.Set;

/**
 * Class of PortWindow for creating new Ship
 * Created by Alexey on 17.03.2017.
 */

public class ShipWindow {

    //-----------------------Objects-------------------------------------------

    private Shell shell;
    private List productList;
    private List controlList;
    private Text shipNameField;
    private Label productLabel;
    private Composite timerComposite;
    private Spinner timeSpinner;
    private Combo priorityCombo;
    private Button timeCheckButton;
    private Button priorityCheckButton;
    private Composite northComposite;
    private Composite leftFullComposite;
    private Composite propertiesComposite;
    private Composite checkComposite;
    private Button fineCheckButton;
    private Composite rightComposite;
    private Composite productAddComposite;
    private Combo productComboBox;
    private Spinner productSpinner;
    private Composite buttonComposite;
    private Composite southComposite;

    private Ship ship = new Ship();

    //-----------------------Constructors--------------------------------------

    /**
     * Create window and link element with logic
     */

    public ShipWindow() {
        shell = new Shell(Display.getCurrent());
        shell.setSize(450, 300);
        shell.setText("SWT Application");
        shell.setLayout(new FillLayout(SWT.VERTICAL));

        northComposite = new Composite(shell, SWT.NONE);
        northComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

        leftFullComposite = new Composite(northComposite, SWT.NONE);
        leftFullComposite.setLayout(new FillLayout(SWT.VERTICAL));

        productLabel = new Label(leftFullComposite, SWT.RIGHT);
        productLabel.setText("product");

        shipNameField = new Text(leftFullComposite, SWT.BORDER);
        shipNameField.setText(ship.getName());

        propertiesComposite = new Composite(leftFullComposite, SWT.NONE);
        propertiesComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

        timerComposite = new Composite(propertiesComposite, SWT.NONE);
        timerComposite.setLayout(new FillLayout(SWT.VERTICAL));

        timeSpinner = new Spinner(timerComposite, SWT.BORDER);

        priorityCombo = new Combo(timerComposite, SWT.NONE);
        setPriorityList(priorityCombo);
        priorityCombo.select(0);

        checkComposite = new Composite(propertiesComposite, SWT.NONE);
        checkComposite.setLayout(new FillLayout(SWT.VERTICAL));

        timeCheckButton = new Button(checkComposite, SWT.CHECK);
        timeCheckButton.setText("Use Time");

        priorityCheckButton = new Button(checkComposite, SWT.CHECK);
        priorityCheckButton.setText("Use Priority");

        fineCheckButton = new Button(checkComposite, SWT.CHECK);
        fineCheckButton.setText("Fine");

        rightComposite = new Composite(northComposite, SWT.NONE);
        rightComposite.setLayout(new FillLayout(SWT.VERTICAL));

        productComboBox = new Combo(rightComposite, SWT.NONE);
        setProductList(productComboBox);
        productComboBox.select(0);

        productSpinner = new Spinner(rightComposite, SWT.BORDER);

        Button addButton = new Button(rightComposite, SWT.NONE);
        addButton.setText("Add product");
        addButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                addAction();
            }
        });

        Button addControlButton = new Button(rightComposite, SWT.NONE);
        addControlButton.setText("Add to control product");
        addControlButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                addControlAction();
            }
        });

        Button closeButton = new Button(rightComposite, SWT.NONE);
        closeButton.setText("Close");
        closeButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                closeAction();
            }
        });

        Button applyButton = new Button(rightComposite, SWT.NONE);
        applyButton.setText("Apply");
        applyButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                applyAction();
            }
        });

        southComposite = new Composite(shell, SWT.NONE);
        southComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

        productList = new List(southComposite,SWT.V_SCROLL | SWT.BORDER);
        controlList = new List(southComposite,SWT.V_SCROLL | SWT.BORDER);
    }

    //-----------------------Methods-------------------------------------------

    /**
     * the method for opening ship PortWindow
     * @return - the created Object of Ship
     */

    public Ship open() {
        this.show();
        return ship;
    }

    /**
     * method for showing ship PortWindow
     */

    public void show() {
        Display display = Display.getDefault();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * method for initialize comboBox with default priorityList
     * @param comboBox - comboBox, that was be initialize by default PriorityList
     */

    private void setPriorityList(Combo comboBox) {
        for (QueuePriority i : Client.QueuePriority.values()){
            comboBox.add(i.toString());
        }
        comboBox.select(0);
    }

    /**
     * method for initialize comboBox with default productList
     * @param comboBox - comboBox, that was be initialize by default ProductList
     */

    private void setProductList(Combo comboBox) {
        comboBox.add("Silk");
        comboBox.add("Wood");
        comboBox.add("Milk");
    }

    /**
     * method for refreshing out of storage of created ship
     * @param list - List, that was be refresh
     */

    private void refreshProductList(List list) {
        list.removeAll();
        Set<String> keys = ship.getKeySet();
        for (String i : keys) {
            list.add(i + " - " + ship.getCountOfProduct(i));
        }
    }

    /**
     * method for refreshing out of control of storage of created ship
     * @param list - List, that was be refresh
     */

    private void refreshControlList(List list){
        list.removeAll();
        Set<String> keys = ship.getControlMap().keySet();
        for (String i : keys) {
            list.add(i + " - " + ship.getCountOfControl(i));
        }
    }

    //-----------------------Actions-------------------------------------------

    /**
     * action, that performed, when button "Add" was pressed
     */
    private void addAction() {
        ship.addCount(productComboBox.getItem(productComboBox.getSelectionIndex()), productSpinner.getSelection());
        refreshProductList(productList);
    }

    /**
     * action, that performed, when button "Add Control" was pressed
     */
    private void addControlAction() {
        ship.addControlCount(productComboBox.getItem(productComboBox.getSelectionIndex()), productSpinner.getSelection());
        refreshControlList(controlList);
    }

    /**
     * action, that performed, when button "Close" was pressed
     */
    private void closeAction() {
        ship = null;
        shell.dispose();
    }

    /**
     * action, that performed, when button "Apply" was pressed
     */
    private void applyAction() {
        ship.setPriority(QueuePriority.valueOf(priorityCombo.getItem(priorityCombo.getSelectionIndex())));
        ship.setName(shipNameField.getText());
        ship.setUsePriority(this.priorityCheckButton.getSelection());
        ship.setFine(this.fineCheckButton.getSelection());
        ship.setUseTimer(this.timeCheckButton.getSelection());
        ship.setTime(this.timeSpinner.getSelection());
        shell.dispose();
    }
}