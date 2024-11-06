package lib.GUIComponents.HighBasicComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import lib.DBComponents.DBConnectionClassInterface;
import lib.DBComponents.DBMenuOperations;
import lib.DBComponents.DBMenuOperationsInterface;
import lib.DBComponents.DynamicRefreshInterface;
import lib.GUIComponents.LowBasicComponents.InsertNewMealFrame;
import lib.GUIComponents.LowBasicComponents.ModifyMealFrame;
import lib.GUIComponents.LowBasicComponents.RemoveMealFrame;

/******** Class for the menu panel ********/
public class MenuPanel extends JPanel implements ActionListener, DynamicRefreshInterface {

    /**** Fields ****/
    // Variables
    private final MyMenuToolBar toolbar;                            // ToolBar for more options
    private final DBConnectionClassInterface connector;             // Connector object
    private final JTable menuTable;                                 // JTable for the menu


    /**** Constructors ****/
    // Main constructor
    public MenuPanel(final DBConnectionClassInterface conn) {

        // Setting the Reservations' panel info
        super.setBackground(new Color(238, 238, 238));                        // Setting the panel's background color
        super.setOpaque(true);                                                        // Making the background visible
        super.setLayout(new BorderLayout());

        // Initializing the connector
        this.connector = conn;

        // Initializing the menu operation connector
        DBMenuOperationsInterface menuOperationConnector = new DBMenuOperations(this.connector);

        // Defining the new toolBar
        this.toolbar = new MyMenuToolBar();

        // Setting the menu toolbar's action listener
        this.toolbar.getInsertDishButton().addActionListener(this);
        this.toolbar.getModifyDishButton().addActionListener(this);
        this.toolbar.getRemoveDishButton().addActionListener(this);
        
        // Create a Default table model to hold the table data
        DefaultTableModel tableModel = new DefaultTableModel();

        // Set the column names for the table
        String[] columnNames = {"Meal Code", "Name", "Price", "Description", "Availability"};
        tableModel.setColumnIdentifiers(columnNames);

        // Retrieve reservation data from the database
        Object[][] menuData = menuOperationConnector.getMenuData();

        // Add the reservation data to the table model
        for (Object[] rowData : menuData) {
            tableModel.addRow(rowData);
        }

        // Initializing the JTable for the menu using the table model
        this.menuTable = new JTable(tableModel);

        // Create a JScrollPane to wrap the JTable
        JScrollPane scrollPane = new JScrollPane(menuTable);

        // Add the scroll pane to the panel
        super.add(scrollPane, BorderLayout.CENTER);
        super.add(this.toolbar, BorderLayout.PAGE_END);
    }


    /**** Methods ****/
    // Action performed method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.toolbar.getInsertDishButton()) {           // If the "insert dish" button is pressed, open the relative tab
            new InsertNewMealFrame(this.connector, (MenuPanel) this.returnPanel(this));
        }

        else if (e.getSource() == this.toolbar.getRemoveDishButton()) {      // If the "remove dish" button is pressed, open the relative tab
            new RemoveMealFrame(this.connector, (MenuPanel) this.returnPanel(this));
        }

        else if (e.getSource() == this.toolbar.getModifyDishButton()) {      // If the "modify dish" button is pressed, open the relative tab
            new ModifyMealFrame(this.connector);
        }
    }

    // Return the menu panel's table
    public JTable returnPanelTable() {
        return this.menuTable;
    }
}