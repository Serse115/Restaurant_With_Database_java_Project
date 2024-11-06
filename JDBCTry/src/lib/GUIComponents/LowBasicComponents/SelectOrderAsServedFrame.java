package lib.GUIComponents.LowBasicComponents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.JRadioButton;
import lib.DBComponents.DBConnectionClassInterface;
import lib.DBComponents.DBOrderOperations;
import lib.DBComponents.DBOrderOperationsInterface;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/******** Select order as served frame class ********/
public class SelectOrderAsServedFrame extends JFrame implements ActionListener {

    /**** Fields ****/
    // Variables
    private final JButton confirmButton;                                // Button to confirm the operation and close
    private final JComboBox<Integer> ordersComboBox;                    // Combo box for orders selection
    private final JRadioButton alreadyServedButton;                     // RadioButton to set as already served
    private final JRadioButton notServedYet;                            // RadioButton to set as not served yet
    private final DBOrderOperationsInterface ordersOperationsConnector; // Orders operations connector object


    /**** Constructors ****/
    // Main constructor
    public SelectOrderAsServedFrame(final String tableNum, final DBConnectionClassInterface conn) {

        // Setting the frame
        super("Select the order to set as served");
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setResizable(false);

        // Initializing the orders operations connector object
        this.ordersOperationsConnector = new DBOrderOperations(conn);

        // Setting the reference to the table number of the chosen table
        int tableNum1 = Integer.parseInt(tableNum);

        // Logo for the frame
        ImageIcon logo = new ImageIcon(Objects.requireNonNull(InsertNewReservationFrame.class.getResource("ForkKnifeLogo.png")));
        Image appLogo = logo.getImage();
        super.setIconImage(appLogo);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(400, 150));        // Set the preferred size of the main panel

        // Setting the radio button as already served
        this.alreadyServedButton = new JRadioButton("Y");
        this.alreadyServedButton.setFocusable(false);
        this.alreadyServedButton.addActionListener(this);

        // Setting the radio button as not yet served
        this.notServedYet = new JRadioButton("N");
        this.notServedYet.setFocusable(false);
        this.notServedYet.addActionListener(this);

        // Defining the insertion tage to tell the user what to choose
        JLabel insertionTag = new JLabel("Service status:");

        // Create form panel
        JPanel orderPanel = new JPanel(new FlowLayout());

        // Retrieve the order codes available from the database
        Integer[] ordersList = this.ordersOperationsConnector.getOrderNumbers(tableNum1);

        // Create orders combo box
        this.ordersComboBox = new JComboBox<>(ordersList);
        orderPanel.add(this.ordersComboBox);

        // Create form panel
        JPanel formPanel = new JPanel(new FlowLayout());

        // Add labels and fields to the form panel
        formPanel.add(insertionTag);
        formPanel.add(this.alreadyServedButton);
        formPanel.add(this.notServedYet);

        // Setting the confirmation button
        this.confirmButton = new JButton("Confirm");
        this.confirmButton.setFocusable(false);
        this.confirmButton.addActionListener(this);

        // Adding the components to the frame
        mainPanel.add(orderPanel, BorderLayout.PAGE_START);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(this.confirmButton, BorderLayout.PAGE_END);
        super.setContentPane(mainPanel);

        // Pack the frame to adjust its size
        super.pack();

        // Setting the frame as visible
        super.setVisible(true);
    }


    /**** Methods ****/
    // Action performed method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.confirmButton) {

            String serviceStatus;                   // Service status to choose
            Integer orderNumberChosen;              // Order number chosen

            // Obtaining the data
            try {
                orderNumberChosen = (Integer) ordersComboBox.getSelectedItem();     // Getting the chosen order number

                // Updating the order's status
                if (orderNumberChosen >= 0) {
                    if (this.alreadyServedButton.isSelected()) {                                                // If the already served button is pressed
                        serviceStatus = this.alreadyServedButton.getText();                                     // use the text of the already served button (Y)
                        this.ordersOperationsConnector.updateStatusService(orderNumberChosen, serviceStatus);   // to update the service status of that order

                    }
                    else if (this.notServedYet.isSelected()) {                                                  // If the not yet served button is pressed
                        serviceStatus = this.notServedYet.getText();                                            // use the text of the already served button (N)
                        this.ordersOperationsConnector.updateStatusService(orderNumberChosen, serviceStatus);   // to update the service status of that order
                    }
                }
                else {
                    new MyOptionPane("Please select an order number and a status service either equal to Y or N!", 0, "Error");
                }
            }
            catch (NumberFormatException | NullPointerException h) {
                new MyOptionPane("The data format you chose is invalid!", 0, "Error");
            }
        }
    }
}