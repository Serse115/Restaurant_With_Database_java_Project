package lib.DBComponents;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import lib.GUIComponents.LowBasicComponents.MyOptionPane;

/******** Class for the connection ********/
public final class DBConnectionClass implements DBConnectionClassInterface {

    /**** Fields ****/
    // Constants
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restaurant";  // Expected default DB URL
    private static final String MYSQL_SERVER_URL = "jdbc:mysql://localhost:3306/";  // Expected default MySQL server URL

    // Variables
    private Connection conn;                 // Connection field



    /**** Constructors ****/
    // Empty constructor
    public DBConnectionClass() {

    }



    /**** Methods ****/
    // Connect to the DB method
    public boolean connectToDB(final String user, final String password) {
        try {
            this.conn = DriverManager.getConnection(DB_URL, user, password);                                        // Getting the connection to the DB
            new MyOptionPane("Database connected successfully!", 1, "Database status");
            return true;                                                                                            // Returns true if the connection is established
        } catch (SQLException e) {
            new MyOptionPane("Could not connect to the Database!", 0, "Error");
            return false;                                                                                           // Otherwise it returns false
        }
    }

    // Get the current connection status method
    public Connection returnConnection() {
        return this.conn;
    }

    // Close the connection to the DB method
    public void closeConnectionToDB() {
        try {
            if (this.conn != null && !this.conn.isClosed()) {                                       // If the connection is not null and is not already closed
                this.conn.close();                                                                  // close it
                new MyOptionPane("Database disconnected successfully!", 1, "Database status");
            }
        } catch (SQLException e) {
            new MyOptionPane("Could not close the connection to the Database!", 0, "Error");
        }
    }

    // Connect to the mysql server to check the DB existence
    public boolean checkServerConnection(final String user, final String password) {
        try {
            this.conn = DriverManager.getConnection(MYSQL_SERVER_URL, user, password);     // Connecting to the expected MySQL server
            new MyOptionPane("Server connected successfully! Now checking the DB existence...", 1, "Database status");

            // If it exists, the check returns true
            // Otherwise it returns false and closes the application
            return checkDBExistence(conn);
            
        } catch (SQLException e) {
            new MyOptionPane("Could not connect to the server! Check your user and password!", 0, "Error");
            System.exit(0);
        }
        return false;
    }

    // Checking the DB existence into the MySQL server
    private boolean checkDBExistence(final Connection conn) {

        // Checking the existence of the DB
        try {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet resultSet = metaData.getCatalogs();
        while (resultSet.next()) {
            String dbName = resultSet.getString(1);
            if (dbName.equalsIgnoreCase("restaurant")) {     // Check if a DB called "restaurant" exists
                return true;                                            // Database exists so returns true
            }
        }
        } catch (SQLException e) {
            return false;                                               // Database does not exist so returns false
        }
        return false;
    }
}