import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static InventoryManager manager = new InventoryManager();
    private static UserManager userManager = new UserManager();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;
    private static boolean isLoggedIn = false;

    public static void main(String[] args) {
        while (true) {
            if (!isLoggedIn) {
                System.out.println("\n1. Login");
                System.out.println("2. Signup");
                System.out.println("3. Display Users");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = getIntInput();

                switch (choice) {
                    case 1 -> login();
                    case 2 -> signup();
                    case 3 -> userManager.displayUsers();
                    case 4 -> System.exit(0);
                    default -> System.out.println("Invalid choice, please try again.");
                }
            }

            if (isLoggedIn) {
                manageInventory();
            }
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userManager.authenticate(username, password);
        if (user != null) {
            currentUser = user;
            isLoggedIn = true;
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void signup() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userManager.addUser(username, password)) {
            System.out.println("Signup successful! You can now log in.");
        } else {
            System.out.println("Username already exists. Please choose a different username.");
        }
    }

    private static void manageInventory() {
        while (isLoggedIn) {
            System.out.println("\nInventory System:");
            System.out.println("1. Add Item");
            System.out.println("2. Update Item Quantity");
            System.out.println("3. Check Inventory");
            System.out.println("4. Check Expiry Dates");
            System.out.println("5. Undo Last Action");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> addItem();
                case 2 -> updateItem();
                case 3 -> manager.checkInventory();
                case 4 -> displayExpiryDates(); // New method to display expiry dates
                case 5 -> manager.undoLastAction();
                case 6 -> logout();
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void addItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item quantity: ");
        int quantity = getIntInput();
        System.out.print("Enter expiry date (YYYY-MM-DD): ");
        LocalDate expiryDate = LocalDate.parse(scanner.nextLine());

        manager.addItem(name, quantity, expiryDate);
    }

    private static void updateItem() {
        System.out.print("Enter item name to update: ");
        String name = scanner.nextLine();
        System.out.print("Enter new quantity: ");
        int quantity = getIntInput();

        manager.updateItemQuantity(name, quantity);
    }

    private static void displayExpiryDates() {
        System.out.println("\nExpiry Dates of Products:");
        manager.checkExpiryDates(); // Call to InventoryManager's method to display expiry dates
    }

    private static void logout() {
        currentUser = null;
        isLoggedIn = false;
        System.out.println("You have logged out.");
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}