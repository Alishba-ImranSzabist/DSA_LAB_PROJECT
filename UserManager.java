import java.io.*;
import java.util.*;

public class UserManager {
    private ArrayList<User> userList; // Store users
    private static final String FILE_NAME = "users.txt";

    public UserManager() {
        userList = new ArrayList<>();
        loadUsers();
    }

    private void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                userList.add(User.fromString(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous user data found. Starting fresh.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) return false;
        }
        User newUser = new User(username, password);
        userList.add(newUser);
        saveUsers();
        return true;
    }

    public User authenticate(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void displayUsers() {
        System.out.println("\nRegistered Users:");
        for (User user : userList) {
            System.out.println(user.getUsername());
        }
    }

    private void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : userList) {
                bw.write(user.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}