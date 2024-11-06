package lib.DBComponents;

/******** Interface for the MenuOperations class ********/
public interface DBMenuOperationsInterface {

    // Methods
    // Insert new meal method
    void insertNewMeal(String mealCode, String name, double price, String description, int amount);

    // Method to retrieve all meal codes
    String[] getMealCodes();

    // Method to delete the meal from the menu through the meal code
    void deleteMeal(String mealCode);

    // Method to update the meal's availability
    void updateMealAvailability(String mealCode, int amount);

    // Method to retrieve the menu from the database
    Object[][] getMenuData();
}