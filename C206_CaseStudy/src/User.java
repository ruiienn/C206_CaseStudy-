import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
	private String username;
	private String password;
	private String role; // "customer", "stall owner", "administrator"s
	
	
	public User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
		
	}

	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}
    
	
	// Make the methods public and static
	public static void createNewUser(List<User> users, Scanner scanner) {
		System.out.print("Enter Username: ");
		String username = scanner.nextLine();

		System.out.print("Enter Password: ");
		String password = scanner.nextLine();

		System.out.print("Enter Role (customer/stall owner/administrator): ");
		String role = scanner.nextLine();

		if (role.equalsIgnoreCase("customer") || role.equalsIgnoreCase("stall owner") || role.equalsIgnoreCase("administrator")) {
			User newUser = new User(username, password, role);
			users.add(newUser);
			System.out.println("New user created successfully!");
		} else {
			System.out.println("Invalid role. User not created.");
		}
	}

	public static void deleteExistingUser(List<User> users, Scanner scanner) {
		System.out.print("Enter the Username to delete: ");
		String usernameToDelete = scanner.nextLine();

		User userToDelete = null;

		for (User user : users) {
			if (user.getUsername().equalsIgnoreCase(usernameToDelete)) {
				userToDelete = user;
				break;
			}
		}

		if (userToDelete != null) {
			users.remove(userToDelete);
			System.out.println("User with username " + usernameToDelete + " has been deleted.");
		} else {
			System.out.println("User with username " + usernameToDelete + " not found.");
		}
	}

	public static void viewAllUsers(List<User> users) {
		System.out.println("--- All Users ---");
		if (users.isEmpty()) {
			System.out.println("No users found.");
		} else {
			for (User user : users) {
				System.out.println("Username: " + user.getUsername());
				System.out.println("Password: " + user.getPassword());
				System.out.println("Role: " + user.getRole());
				System.out.println("------------");
			}
		}
	}
}
