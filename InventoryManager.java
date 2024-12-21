import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class InventoryManager {
    private ArrayList<InventoryItem> inventory; // Store inventory
    private LinkedList<String> inventoryLog; // Log actions
    private Stack<InventoryItem> undoStack; // Undo operations
    private Queue<ProductExpiry> expiryQueue; // Track expiry dates
    private static final String FILE_NAME = "inventory.txt";

    public InventoryManager() {
        inventory = new ArrayList<>();
        inventoryLog = new LinkedList<>();
        undoStack = new Stack<>();
        expiryQueue = new PriorityQueue<>(Comparator.comparing(ProductExpiry::getExpiryDate));
        loadInventory();
    }

    private void loadInventory() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                InventoryItem item = InventoryItem.fromString(line);
                inventory.add(item);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous inventory found. Starting fresh.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addItem(String name, int quantity, LocalDate expiryDate) {
        for (InventoryItem item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                item.setQuantity(item.getQuantity() + quantity);
                undoStack.push(new InventoryItem(name, quantity));
                inventoryLog.add("Updated item: " + name + ", added quantity: " + quantity);
                expiryQueue.add(new ProductExpiry(name, expiryDate));
                saveInventory();
                return;
            }
        }
        InventoryItem newItem = new InventoryItem(name, quantity);
        inventory.add(newItem);
        undoStack.push(newItem);
        inventoryLog.add("Added new item: " + name + ", quantity: " + quantity);
        expiryQueue.add(new ProductExpiry(name, expiryDate));
        saveInventory();
    }

    public void updateItemQuantity(String name, int quantity) {
        for (InventoryItem item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                undoStack.push(new InventoryItem(name, item.getQuantity())); // Save previous state
                item.setQuantity(quantity);
                inventoryLog.add("Updated item: " + name + ", quantity changed to " + quantity);
                saveInventory();
                return;
            }
        }
        System.out.println("Item not found.");
    }

    public void undoLastAction() {
        if (!undoStack.isEmpty()) {
            InventoryItem lastItem = undoStack.pop();
            for (InventoryItem item : inventory) {
                if (item.getName().equalsIgnoreCase(lastItem.getName())) {
                    item.setQuantity(lastItem.getQuantity());
                    inventoryLog.add("Undo: reverted " + lastItem.getName() + " to quantity " + lastItem.getQuantity());
                    saveInventory();
                    return;
                }
            }
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public void checkInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("\nCurrent Inventory:");
        for (InventoryItem item : inventory) {
            System.out.println(item.getName() + ": " + item.getQuantity());
        }
    }

    public void checkExpiryDates() {
        System.out.println("\nUpcoming Expiry Dates:");
        for (ProductExpiry expiry : expiryQueue) {
            System.out.println(expiry);
        }
    }

    public void printLog() {
        System.out.println("\nInventory Log:");
        for (String log : inventoryLog) {
            System.out.println(log);
        }
    }

    private void saveInventory() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (InventoryItem item : inventory) {
                bw.write(item.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}