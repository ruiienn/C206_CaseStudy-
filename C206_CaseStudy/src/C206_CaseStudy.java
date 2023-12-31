import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class C206_CaseStudy {
	private static List<Order> orders = new ArrayList<>();
	private static List<Stall> stalls = new ArrayList<>();
	private static List<User> users = new ArrayList<>();
	private static Queue customerQueue = new Queue();
	private static Feedback feedback = new Feedback();
	private static String loggedInUser = "Guest"; // Default to "Guest" if no user is logged ins

	public static void main(String[] args) {
		User testUser = new User("testuser", "testpassword", "administrator");
		users.add(testUser);

		Scanner scanner = new Scanner(System.in);

		System.out.print("Do you want to log in? (yes/no): ");
		String loginChoice = scanner.nextLine();

		if (loginChoice.equalsIgnoreCase("yes")) {
			// Perform login
			String loggedInUserName = login(users, scanner);

			if (loggedInUserName == null) {
				System.out.println("Login failed.");
				String R = readString("Do you want to register an account? (yes/no):", scanner);
				if (R.equalsIgnoreCase("yes")) {
					User.createNewUser(users, scanner);
				}
			} else {
				loggedInUser = loggedInUserName; // Set the loggedInUser to the customer name
				System.out.println("Login successful.");
			}
		} else if (loginChoice.equalsIgnoreCase("no")) { 
			String R = readString("Do you want to register an account? (yes/no):", scanner);
			if (R.equalsIgnoreCase("yes")) {
				User.createNewUser(users, scanner);
			} else {
				System.out.println(" Logging in as guest.");
			}
		}

		// Create some example stalls 
		Stall stall1 = new Stall("Delicious Noodles", "Chinese", "Food Court A",1);
		stall1.addMenuItem("Noodle Soup",13.00);
		stall1.addMenuItem("Fried Dumplings",13.00);
		stall1.addMenuItem("Spring Rolls",13.00);

		Stall stall2 = new Stall("Pizza Paradise", "Italian", "Food Court B",2);
		stall2.addMenuItem("Margherita Pizza",13.00);
		stall2.addMenuItem("Pepperoni Pizza",13.00);
		stall2.addMenuItem("Hawaiian Pizza",13.00);

		Stall stall3 = new Stall("Burger Junction", "American", "Food Court C",3);
		stall3.addMenuItem("Classic Cheeseburger",13.00);
		stall3.addMenuItem("BBQ Bacon Burger",13.00);
		stall3.addMenuItem("Veggie Burger",13.00);

		// Add the stalls to the list of stalls
		stalls.add(stall1);
		stalls.add(stall2);
		stalls.add(stall3);

		boolean exitProgram = false;

		while (!exitProgram) {
			System.out.println("\n--- Food Court Management System ---");
			System.out.println("1. View all stalls");
			System.out.println("2. Create a new order");
			System.out.println("3. View all orders");
			System.out.println("4. Delete an existing order");
			System.out.println("5. Queue for multiple stalls");
			System.out.println("6. View queued stalls");
			System.out.println("7. Leave the queue");
			System.out.println("8. Pay for an order");
			System.out.println("9. Feedback");
			System.out.println("10. Add a new stall");
			System.out.println("11. Edit stall Menu");
			System.out.println("12. Delete an existing stall");
			System.out.println("13. Create a user account");
			System.out.println("14. Delete an existing user");
			System.out.println("15. View all users");
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
                Stall.viewAllStalls(stalls);
                break;
            case 2:
                Order.createNewOrder(orders, scanner);
                break;
            case 3:
                Order.viewAllOrders(orders);
                break;
            case 4:
                Order.deleteExistingOrder(orders, scanner);
                break;
            case 5:
                Queue.queueForMultipleStalls(customerQueue, stalls, scanner);
                break;
            case 6:
                Queue.viewQueuedStalls(customerQueue);
                break;
            case 7:
                Queue.leaveQueue(customerQueue, scanner);
                break;
            case 8:
                Order.payOrder(orders, scanner);
                break;
            case 9:
                feedbackMenu(loggedInUser, feedback, scanner);
                break;
            case 10:
                Stall.addNewStall(stalls, scanner);
                break;
            case 11:
                manageStallMenu(scanner);
                break;
            case 12:
                Stall.deleteExistingStall(stalls, scanner);
                break;
            case 13:
                User.createNewUser(users, scanner);
                break;
            case 14:
                User.deleteExistingUser(users, scanner);
                break;
            case 15:
                User.viewAllUsers(users);
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
	public static String login(List<User> users, Scanner scanner) { //Jun Yang
		System.out.print("Enter Username: ");
		String username = scanner.nextLine();

		System.out.print("Enter Password: ");
		String password = scanner.nextLine();

		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user.getUsername();
			}
		}

		return null; // Login failed
	}

	// Helper method to check user authorization
	public static boolean isAuthorized(String username, int choice) { //Jun Yang
		// Find the user with the given username in the list of users
		User currentUser = null;
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				currentUser = user;
				break;
			}
		}

		if (currentUser == null) {
			return choice >= 0 && choice <= 8;// Default access for guests
		}

		// Determine the role of the current user and apply role-based access control
		if (currentUser.getRole().equalsIgnoreCase("administrator")) {
			return true; // Administrators have access to all menu options
		} else {
			// For other roles, apply the existing access control logic
			switch (currentUser.getRole()) {
			case "customer":
				return choice >= 0 && choice <= 9; 
			case "stall owner":
				return choice  ==0 || choice >= 11 && choice <12;
			default:
				System.out.println("Invalid role");
				return false; // Unknown role

			}
		}
	}

	// Helper method to read a string input
	public static String readString(String prompt, Scanner scanner) { 
		System.out.print(prompt);
		return scanner.nextLine();
	}
	 private static void feedbackMenu(String loggedInUser, Feedback feedback, Scanner scanner) { //Kai
	     boolean exitFeedbackMenu = false; 
	     while (!exitFeedbackMenu) { 
	         System.out.println("\n--- Feedback Menu ---"); 
	         System.out.println("1. View Feedback"); 
	         System.out.println("2. Add Feedback"); 
	         System.out.println("3. Delete Feedback"); 
	         System.out.println("0. Back to Main Menu"); 
	 
	         System.out.print("Enter your choice: "); 
	         int feedbackChoice; 
	         try { 
	             feedbackChoice = scanner.nextInt(); 
	             scanner.nextLine(); // Consume the newline character after reading the choice 
	         } catch (InputMismatchException e) { 
	             System.out.println("Invalid input. Please enter a valid number."); 
	             scanner.nextLine(); // Consume the invalid input 
	             continue; 
	         } 
	 
	         switch (feedbackChoice) { 
	             case 1: 
	                 feedback.viewFeedback(); 
	                 break; 
	             case 2: 
	                 System.out.print("Please enter item ordered and give feedback: "); 
	                 String userFeedback = scanner.nextLine(); 
	                 feedback.addFeedback(loggedInUser, userFeedback); 
	                 System.out.println("Feedback uploaded successfully."); 
	                 break; 
	             case 3: 
	                 feedback.viewFeedback(); // Display existing feedback for reference 
	                 System.out.print("Enter the index of the feedback to delete: "); 
	                 int indexToDelete; 
	                 try { 
	                     indexToDelete = scanner.nextInt(); 
	                     scanner.nextLine(); // Consume the newline character after reading the choice 
	                 } catch (InputMismatchException ex) { 
	                     System.out.println("Invalid input. Please enter a valid number."); 
	                     scanner.nextLine(); // Consume the invalid input 
	                     continue; 
	                 } 
	                 if (feedback.deleteFeedback(indexToDelete - 1)) { 
	                     System.out.println("Feedback deleted successfully."); 
	                 } else { 
	                     System.out.println("Failed to delete feedback. Invalid index."); 
	                 } 
	                 break; 
	             case 0: 
	                 exitFeedbackMenu = true; 
	                 break; 
	             default: 
	                 System.out.println("Invalid choice. Please try again."); 
	         } 
	     } 
	 } 
	 private static void manageStallMenu(Scanner scanner) { //Jun Yang
		    // Display available stalls
		    System.out.println("--- Select a Stall ---");
		    Stall.viewAllStalls(stalls);
		    System.out.print("Enter Stall Id: ");
		    int selectedStallId = scanner.nextInt();
		    scanner.nextLine(); // Consume the newline character

		    // Find the selected stall
		    Stall selectedStall = null;
		    for (Stall stall : stalls) {
		        if (stall.getStallId() == selectedStallId) {
		            selectedStall = stall;
		            break;
		        }
		    }

		    if (selectedStall != null) {
		        System.out.println("Selected Stall: " + selectedStall.getName());
		        System.out.println("1. Add Menu Item");
		        System.out.println("2. Remove Menu Item");
		        System.out.print("Enter your choice: ");
		        int menuChoice = scanner.nextInt();
		        scanner.nextLine(); // Consume the newline character

		        switch (menuChoice) {
		            case 1:
		                addMenuItemToStall(selectedStall, scanner);
		                break;
		            case 2:
		                removeMenuItemFromStall(selectedStall, scanner);
		                break;
		            default:
		                System.out.println("Invalid choice. Returning to main menu.");
		        }
		    } else {
		        System.out.println("Stall not found.");
		    }
		}

		private static void addMenuItemToStall(Stall stall, Scanner scanner) { //Jun Yang
		    System.out.print("Enter Menu Item Name: ");
		    String itemName = scanner.nextLine();

		    System.out.print("Enter Menu Item Price: ");
		    double itemPrice = scanner.nextDouble();
		    scanner.nextLine(); // Consume the newline character

		    stall.addMenuItem(itemName, itemPrice);
		    System.out.println("Menu item added successfully.");
		}

		private static void removeMenuItemFromStall(Stall stall, Scanner scanner) {//Jun Yang
		    System.out.print("Enter Menu Item Name to Remove: ");
		    String itemName = scanner.nextLine();

		    stall.removeMenuItem(itemName);
		    System.out.println("Menu item removed successfully.");
		}

	}