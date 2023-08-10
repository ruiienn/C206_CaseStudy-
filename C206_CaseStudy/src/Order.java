import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Order {
    private int orderId;
    private String customerName;
    private List<String> items;
    private boolean isPaid;
    private Map<String, Double> menu;

    public Order(int orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.isPaid = false;
        this.menu = new HashMap<>(); // Initialize the menu as an empty HashMap
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void addItem(String item, double price) {
        items.add(item);
        menu.put(item, price);
    }

    public void removeItem(String item) {
        items.remove(item);
        menu.remove(item);
    }

    public List<String> getItems() {
        return items;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void markAsPaid() {
        isPaid = true;
    }

    public double calculateOrderAmount() {
        double totalAmount = 0.0;
        for (String item : items) {
            if (menu.containsKey(item)) {
                totalAmount += menu.get(item);
            }
        }
        return totalAmount;
    }

    public static void createNewOrder(List<Order> orders, Scanner scanner) {
        Random random = new Random();
        int orderId = 1000 + random.nextInt(9000); // Generate a 4-digit random number (between 1000 and 9999)
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        Order newOrder = new Order(orderId, customerName);

        // Add items to the order
        boolean addMoreItems = true;
        while (addMoreItems) {
            System.out.print("Enter Item Name (or type 'done' to finish adding items): ");
            String itemName = scanner.nextLine();
            if (itemName.equalsIgnoreCase("done")) {
                addMoreItems = false;
            } else {
                System.out.print("Enter Price for " + itemName + ": ");
                double price = Double.parseDouble(scanner.nextLine());
                newOrder.addItem(itemName, price);
            }
        }

        orders.add(newOrder);
        System.out.println("Order created successfully!");
    }

    public static void viewAllOrders(List<Order> orders) {
        System.out.println("--- All Orders ---");
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Customer Name: " + order.getCustomerName());
                System.out.println("Items:");
                for (String item : order.getItems()) {
                    System.out.println("- " + item + " ($" + order.menu.get(item) + ")");
                }
                System.out.println("Paid: " + order.isPaid());
                System.out.println("------------");
            }
        }
    }

    public static void deleteExistingOrder(List<Order> orders, Scanner scanner) {
        System.out.print("Enter the Order ID to delete: ");
        int orderIdToDelete = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character after reading the orderId

        Order orderToDelete = null;

        for (Order order : orders) {
            if (order.getOrderId() == orderIdToDelete) {
                orderToDelete = order;
                break;
            }
        }

        if (orderToDelete != null) {
            orders.remove(orderToDelete);
            System.out.println("Order with ID " + orderIdToDelete + " has been deleted.");
        } else {
            System.out.println("Order with ID " + orderIdToDelete + " not found.");
        }
    }

    public static void payOrder(List<Order> orders, Scanner scanner) {
        System.out.println("--- Pay for Order ---");
        System.out.print("Enter the Order ID to pay for: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character after reading the orderIds

        Order orderToPay = null;

        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                orderToPay = order;
                break;
            }
        }

        if (orderToPay != null) {
            if (orderToPay.isPaid()) {
                System.out.println("Order with ID " + orderId + " has already been paid.");
            } else {
                double totalAmount = orderToPay.calculateOrderAmount();
                System.out.println("Total Amount to Pay: $" + totalAmount);

                System.out.print("Enter the payment amount: ");
                double amount = scanner.nextDouble();
                double change = amount - totalAmount;
                String formattedChange = String.format("%.2f", change);
                scanner.nextLine(); // Consume the newline character after reading the amount

                if (amount >= totalAmount) {
                    orderToPay.markAsPaid();
                    System.out.println("Payment for Order ID " + orderId + " has been processed." + "\nThe change: $" + formattedChange);
                } else {
                    System.out.println("Payment amount is not sufficient. Payment failed.");
                }
            }
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }
}
