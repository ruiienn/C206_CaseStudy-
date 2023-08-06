import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class C206_CaseStudy {
    private static List<Order> orders = new ArrayList<>();
    private static List<Stall> stalls = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static Queue customerQueue = new Queue();
    private static User loggedInUser = null;

    public static void main(String[] args) {
        User testUser = new User("testuser", "testpassword", "customer");
        users.add(testUser);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to log in? (yes/no): ");
        String loginChoice = scanner.nextLine();

        if (loginChoice.equalsIgnoreCase("yes")) {
            // Perform login
            loggedInUser = login(users, scanner);

            if (loggedInUser == null) {
                System.out.println("Login failed.");
                String R = readString("Do you want to register an account? (yes/no):", scanner);
                if (R.equalsIgnoreCase("yes")) {
                    User.createNewUser(users, scanner);
                }
            } else {
                System.out.println("Login successful.");
            }
        } else if (loginChoice.equalsIgnoreCase("no")) {
            String R = readString("Do you want to register an account? (yes/no):", scanner);
            if (R.equalsIgnoreCase("yes")) {
                User.createNewUser(users, scanner);
            }
            System.out.println("Logging in as guest.");
        } else {
            System.out.println("Invalid choice. Logging in as guest.");
        }

        boolean exitProgram = false;

        while (!exitProgram) {
            System.out.println("\n--- Food Court Management System ---");
            System.out.println("1. Create a new order");
            System.out.println("2. View all orders");
            System.out.println("3. Delete an existing order");
            System.out.println("4. Add a new stall");
            System.out.println("5. View all stalls");
            System.out.println("6. Delete an existing stall");
            System.out.println("7. Queue for multiple stalls");
            System.out.println("8. View queued stalls");
            System.out.println("9. Leave the queue");
            System.out.println("10. Create a user account");
            System.out.println("11. Delete an existing user");
            System.out.println("12. View all users");
            System.out.println("13. Pay for an order");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after reading the choice

            // Check if the user is authorized to access the menu
            if (!isAuthorized(loggedInUser, choice)) {
                System.out.println("You are not authorized to perform this operation.");
                continue;
            }

            switch (choice) {
                case 1:
                    Order.createNewOrder(orders, scanner);
                    break;
                case 2:
                    Order.viewAllOrders(orders);
                    break;
                case 3:
                    Order.deleteExistingOrder(orders, scanner);
                    break;
                case 4:
                    Stall.addNewStall(stalls, scanner);
                    break;
                case 5:
                    Stall.viewAllStalls(stalls);
                    break;
                case 6:
                    Stall.deleteExistingStall(stalls, scanner);
                    break;
                case 7:
                    Queue.queueForMultipleStalls(customerQueue, stalls, scanner);
                    break;
                case 8:
                    Queue.viewQueuedStalls(customerQueue);
                    break;
                case 9:
                    Queue.leaveQueue(customerQueue, scanner);
                    break;
                case 10:
                    User.createNewUser(users, scanner);
                    break;
                case 11:
                    User.deleteExistingUser(users, scanner);
                    break;
                case 12:
                    User.viewAllUsers(users);
                    break;
                case 13:
                    Order.payOrder(orders, scanner);
                    break;
                case 0:
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Exiting the program. Thank you for using the Food Court Management System!");
    }

    // Helper method to perform login
    public static User login(List<User> users, Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null; // Login failed
    }

    // Helper method to check user authorization
    public static boolean isAuthorized(User user, int choice) {
        // Implement your logic for user authorization based on the role and menu choices.
        // For example, you can check if the user's role is "canteen_manager" to allow certain operations.
        // Return true if the user is authorized, otherwise return false.
        return true; // Replace this with your actual authorization logic
    }

    // Helper method to read a string input
    public static String readString(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
