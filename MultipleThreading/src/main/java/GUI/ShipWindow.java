package GUI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ShipWindow {

    protected Shell shell;
    private Table productTable;
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

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
            ShipWindow window = new ShipWindow();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell();
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
        shipNameField.setText("[ship name]");

        propertiesComposite = new Composite(leftFullComposite, SWT.NONE);
        propertiesComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

        timerComposite = new Composite(propertiesComposite, SWT.NONE);
        timerComposite.setLayout(new FillLayout(SWT.VERTICAL));

        timeSpinner = new Spinner(timerComposite, SWT.BORDER);

        priorityCombo = new Combo(timerComposite, SWT.NONE);

        checkComposite = new Composite(propertiesComposite, SWT.NONE);
        checkComposite.setLayout(new FillLayout(SWT.VERTICAL));

        timeCheckButton = new Button(checkComposite, SWT.CHECK);
        timeCheckButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        timeCheckButton.setText("Use Time");

        priorityCheckButton = new Button(checkComposite, SWT.CHECK);
        priorityCheckButton.setText("Use Priority");

        fineCheckButton = new Button(checkComposite, SWT.CHECK);
        fineCheckButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        fineCheckButton.setText("Fine");

        rightComposite = new Composite(northComposite, SWT.NONE);
        rightComposite.setLayout(new FillLayout(SWT.VERTICAL));

        productAddComposite = new Composite(rightComposite, SWT.NONE);
        productAddComposite.setLayout(new FillLayout(SWT.VERTICAL));

        productComboBox = new Combo(productAddComposite, SWT.NONE);

        productSpinner = new Spinner(productAddComposite, SWT.BORDER);

        buttonComposite = new Composite(rightComposite, SWT.NONE);
        buttonComposite.setLayout(new FillLayout(SWT.VERTICAL));

        Button btnNewButton = new Button(buttonComposite, SWT.NONE);
        btnNewButton.setText("New Button");

        Button btnNewButton_1 = new Button(buttonComposite, SWT.NONE);
        btnNewButton_1.setText("New Button");

        Button btnNewButton_2 = new Button(buttonComposite, SWT.NONE);
        btnNewButton_2.setText("New Button");

        southComposite = new Composite(shell, SWT.NONE);
        southComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

        productTable = new Table(southComposite, SWT.BORDER | SWT.FULL_SELECTION);
        productTable.setHeaderVisible(true);
        productTable.setLinesVisible(true);

    }
}