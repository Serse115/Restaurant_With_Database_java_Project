package lib.DBComponents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import lib.GUIComponents.LowBasicComponents.MyOptionPane;

/******** CLASS FOR MENU'S DATABASE OPERATIONS ********/
public final class DBMenuOperations implements DBMenuOperationsInterface {

    /**** Fields ****/
    // Variables
    private final DBConnectionClassInterface connector;           // Connector object



    /**** Constructors ****/
    public DBMenuOperations(final DBConnectionClassInterface connector) {
        this.connector = connector;
    }



    /**** Methods ****/
    /********************************* Menu operations *********************************/
    // Method to insert a new meal into the database
    public void insertNewMeal(final String mealCode, final String name, final double price, final String description, final int amount) {

        // Connection status check
        Connection conn = this.connector.returnConnection();
        if (conn == null) {
            new MyOptionPane("Please connect to the database first!", 0, "Error");
        }

        // Sql command for the method
        // The placeholders ? are used to avoid sqlInjections
        final String sqlCommand = "INSERT INTO menu (meal_code, name, price, description, availability) " + 
                                  "VALUES (?, ?, ?, ?, ?)";

        // Using the established connection to insert a new meal
        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sqlCommand);
            pstmt.setString(1, mealCode);
            pstmt.setString(2, name);
            pstmt.setDouble(3, price);
            pstmt.setString(4, description);
            pstmt.setInt(5, amount);
            pstmt.executeUpdate();
            new MyOptionPane("Meal added successfully!", 1, "Info");
        }
        catch (SQLException e) {
            new MyOptionPane("The Database is not connected or the operation requested is not available!", 0, "Error");
        }
    }

    // Method to retrieve all the meal codes
    public String[] getMealCodes() {

        // Connection status check
        Connection conn = this.connector.returnConnection();
        if (conn == null) {
            new MyOptionPane("Please connect to the database first!", 0, "Error");
            return new String[0];
        }

        // ArrayList for the meal codes
        ArrayList<String> meals = new ArrayList<>();

        // Using the established connection to get the meal codes
        try {
            Statement statement = conn.createStatement();

            // Execute the SQL query to retrieve the meal codes
            String sqlQuery = "SELECT meal_code FROM menu";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Process the result set and add the meal codes to the list
            while (resultSet.next()) {
                String mealCodeList = resultSet.getString("meal_code");
                meals.add(mealCodeList);
            }

        } catch (SQLException e) {
            new MyOptionPane("Oops, the meal codes could not be acquired!", 0, "Error");
        }

        // Convert the list of reservations to an array
        return meals.toArray(new String[0]);
    }

    // Method to delete the meal from the menu through the meal code
    public void deleteMeal(final String mealCode) {

        // Connection status check
        Connection conn = this.connector.returnConnection();
        if (conn == null) {
            new MyOptionPane("Please connect to the database first!", 0, "Error");
        }

        // Sql command for the method
        final String sqlCommand = "DELETE FROM menu WHERE meal_code = ?";

        // Using the established connection to delete the chosen meal
        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sqlCommand);
            pstmt.setString(1, mealCode);
            pstmt.executeUpdate();
            new MyOptionPane("Meal removed successfully!", 1, "Info");
        }
        catch (SQLException e) {
            new MyOptionPane("The Database is not connected or the operation requested is not available!", 0, "Error");
        }
    }

    // Method to update the meal's availability
    public void updateMealAvailability(final String mealCode, final int amount) {

        // Connection status check
        Connection conn = this.connector.returnConnection();
        if (conn == null) {
            new MyOptionPane("Please connect to the database first!", 0, "Error");
        }

        // Sql command for the method
        final String sqlCommand = "UPDATE menu SET availability = ? WHERE meal_code = ?";

        // Using the established connection to update the meal's availability
        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sqlCommand);
            pstmt.setInt(1, amount);
            pstmt.setString(2, mealCode);
            pstmt.executeUpdate();
            new MyOptionPane("Availability updated successfully", 1, "Info");
        }
        catch (SQLException e) {
            new MyOptionPane("The Database is not connected or the operation requested is not available!", 0, "Error");
        }
    }

    // Method to retrieve the full menu data from the database
    public Object[][] getMenuData() {

        // Connection status check
        Connection conn = this.connector.returnConnection();
        if (conn == null) {
            new MyOptionPane("Please connect to the database first!", 0, "Error");
            return new Object[0][0];
        }

        // Sql command for the method
        final String sqlCommand = "SELECT * FROM menu";

        // Using the established connection to retrieve the menu data
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);

            // ArrayList to hold the menu data
            ArrayList<Object[]> data = new ArrayList<>();

            // Iterate over the result set and collect the data
            while (rs.next()) {
                String mealCode = rs.getString("meal_code");
                String mealName = rs.getString("name");
                double mealPrice = rs.getDouble("price");
                String mealDescription = rs.getString("description");
                int mealAvailability = rs.getInt("availability");
                
                // Create an array of objects representing a row of data from the menu
                Object[] rowData = {mealCode, mealName, mealPrice, mealDescription, mealAvailability};
                data.add(rowData);
            }

            // Convert the list of data to a 2D array
            Object[][] dataArray = new Object[data.size()][];
            for (int i = 0; i < data.size(); i++) {
                dataArray[i] = data.get(i);
            }
            return dataArray;

        } catch (SQLException e) {
            new MyOptionPane("The Database is not connected or the data requested is not available!", 0, "Error");
        }

        return new Object[0][0];                    // Returns an empty 2D array if there was an error while getting the data
    }
    /********************************* End of menu operations *********************************/
}