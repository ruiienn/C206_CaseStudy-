import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Stall {
	private String name;
	private String cuisine;
	private String location;
	private int stallId; // New attribute - stallIds
	private List<MenuItem> menu;

	public Stall(String name, String cuisine, String location, int stallId) {
		this.name = name;
		this.cuisine = cuisine;
		this.location = location;
		this.stallId = stallId; // Initialize stallId
		this.menu = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public String getCuisine() {
		return cuisine;
	}

	public String getLocation() {
		return location;
	}

	public int getStallId() {
		return stallId; // New getter method for stallId
	}

	public void addMenuItem(String itemName, double itemPrice) {
	    MenuItem item = new MenuItem(itemName, itemPrice);
	    menu.add(item);
	}

	public void removeMenuItem(String itemName) {
	    menu.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
	}


	public List<MenuItem> getMenu() {
		return menu;
	}

	public static void addNewStall(List<Stall> stalls, Scanner scanner) {
		System.out.print("Enter Stall Name: ");
		String name = scanner.nextLine();

		System.out.print("Enter Cuisine: ");
		String cuisine = scanner.nextLine();

		System.out.print("Enter Location: ");
		String location = scanner.nextLine();

		System.out.print("Enter Stall Id: ");
		int stallId = Integer.parseInt(scanner.nextLine()); // Parse the input as an integer

		Stall newStall = new Stall(name, cuisine, location, stallId); // Pass the stallId to the constructor

		// Add Menu Items with Prices
		boolean addMoreItems = true;
		while (addMoreItems) {
			System.out.print("Enter Menu Item (or type 'done' to finish adding menu items): ");
			String item = scanner.nextLine();
			if (item.equalsIgnoreCase("done")) {
				addMoreItems = false;
			} else {
				System.out.print("Enter Price for " + item + ": ");
				double price = Double.parseDouble(scanner.nextLine());
				newStall.addMenuItem(item, price);
			}
		}

		stalls.add(newStall);

		System.out.println("New stall added successfully!");
	}

	public static void viewAllStalls(List<Stall> stalls) {
		System.out.println("=============================================\n");
		System.out.println("--- All Stalls ---");
		if (stalls.isEmpty()) {
			System.out.println("No stalls found.");
		} else {
			for (Stall stall : stalls) {
				System.out.println("Stall Name: " + stall.getName());
				System.out.println("Cuisine: " + stall.getCuisine());
				System.out.println("Location: " + stall.getLocation());
				System.out.println("Stall Id: " + stall.getStallId()); // Print the stallId
				System.out.println("");
				System.out.println("Menu:");
				for (MenuItem item : stall.getMenu()) {
					System.out.println("= " + item.getName() + " ($" + item.getPrice() + ")");
				}
				System.out.println("=============================================\n");
			}
		}
	}

	public static void deleteExistingStall(List<Stall> stalls, Scanner scanner) {
		System.out.print("Enter the Stall Name to delete: ");
		String stallNameToDelete = scanner.nextLine();

		Stall stallToDelete = null;

		for (Stall stall : stalls) {
			if (stall.getName().equalsIgnoreCase(stallNameToDelete)) {
				stallToDelete = stall;
				break;
			}
		}

		if (stallToDelete != null) {
			stalls.remove(stallToDelete);
			System.out.println("Stall with name " + stallNameToDelete + " has been deleted.");
		} else {
			System.out.println("Stall with name " + stallNameToDelete + " not found.");
		}
	}

	public double calculateOrderAmount(String itemName, int quantity) {
		double totalAmount = 0.0;
		MenuItem item = findMenuItemByName(itemName);
		if (item != null) {
			totalAmount = item.getPrice() * quantity;
		}
		return totalAmount;
	}

	private MenuItem findMenuItemByName(String itemName) {
		for (MenuItem item : menu) {
			if (item.getName().equalsIgnoreCase(itemName)) {
				return item;
			}
		}
		return null;
	}

	public static class MenuItem {
		private String name;
		private double price;

		public MenuItem(String name, double price) {
			this.name = name;
			this.price = price;
		}

		public String getName() {
			return name;
		}

		public double getPrice() {
			return price;
		}
	}
	public double getTotalMenuCost() {
		double totalCost = 0.0;
		for (MenuItem item : menu) {
			totalCost += item.getPrice();
		}
		return totalCost;
	}
	
}
