package lib.GUIComponents.LowBasicComponents;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import lib.DBComponents.DBConnectionClassInterface;
import lib.DBComponents.DBOrderOperations;
import lib.DBComponents.DBOrderOperationsInterface;

/******** Show all orders frame class ********/
public class ShowAllOrdersFrame extends JFrame {

    /**** Fields ****/
    // Empty


    /**** Constructors ****/
    // Main constructor
    public ShowAllOrdersFrame(final String sqlQuery, final DBConnectionClassInterface conn) {

        // Setting the panel
        super.setTitle("All orders");
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Logo for the frame
        ImageIcon logo = new ImageIcon(Objects.requireNonNull(InsertNewReservationFrame.class.getResource("ForkKnifeLogo.png")));
        Image appLogo = logo.getImage();
        super.setIconImage(appLogo);

        // Initializing the orders operations connector
        DBOrderOperationsInterface ordersOperationsConnector = new DBOrderOperations(conn);

        // Create a DefaultTableModel to hold the table data
        DefaultTableModel tableModel = new DefaultTableModel();

        // Set the column names for the table
        String[] columnNames = {"Order Number", "Table Number", "Meal Code", "Amount", "Special Requests", "Service"};
        tableModel.setColumnIdentifiers(columnNames);

        // Retrieve all orders data from the database
        Object[][] ordersData = ordersOperationsConnector.getOrdersData(sqlQuery);

        // Add the orders' data to the table model
        for (Object[] rowData : ordersData) {
            tableModel.addRow(rowData);
        }

        // Create the JTable using the table model for the orders
        JTable ordersTable = new JTable(tableModel);

        // Create a JScrollPane to wrap the JTable
        JScrollPane scrollPane = new JScrollPane(ordersTable);

        // Add the scroll pane to the frame's content pane
        super.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Pack the frame to adjust its size
        super.pack();

        // Setting the frame as visible
        super.setVisible(true);
    }

    /**** Methods ****/
    // Empty
}