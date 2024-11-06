package lib.DBComponents;

/******** Interface to the MenuBarDeletion class ********/
public interface DBMenuBarDeletionInterface {

    // Methods
    // Delete all reservations
    void deleteAllReservations();

    // Delete all orders
    void deleteAllOrders();

    // Delete all waiters
    void deleteAllWaiters();

    // Delete all menu data
    void deleteAllMenu();

    // Reset the table and its auto_increment values
    void resetTable(String tableName);
}
