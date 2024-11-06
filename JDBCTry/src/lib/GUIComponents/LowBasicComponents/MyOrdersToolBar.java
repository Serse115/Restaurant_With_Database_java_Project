package lib.GUIComponents.LowBasicComponents;

import lib.DBComponents.DBConnectionClassInterface;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/******** Orders toolbar class ********/
public class MyOrdersToolBar extends JPanel implements ActionListener {

    /**** Fields ****/
    // Variables
    private final JButton showAllOrdersButton;              // Button to show all orders
    private final DBConnectionClassInterface connector;     // Connector object


    /**** Constructors ****/
    // Main constructor
    public MyOrdersToolBar(final DBConnectionClassInterface conn) {

        // Setting the panel
        super.setBackground(Color.WHITE);

        // Initializing the connection class object
        this.connector = conn;

        // Defining the button to show all orders
        this.showAllOrdersButton = new JButton("All orders");
        this.showAllOrdersButton.setFocusable(false);
        this.showAllOrdersButton.addActionListener(this);

        // Adding the components
        super.add(this.showAllOrdersButton);
    }


    /**** Methods ****/
    // Action listener method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.showAllOrdersButton) {                                        // If the button to show all the orders is pressed
            new ShowAllOrdersFrame("SELECT * FROM order_management", this.connector);   // Retrieve all the orders from the DB and shows them in a new frame
        }
    }
}