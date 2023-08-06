import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Order {
    private int orderId;
    private String customerName;
    private List<String> items;
    private boolean isPaid;

    public Order(int orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.isPaid = false;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(String item) {
        items.remove(item);
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

    // Make the methods public and static
    public static void createNewOrder(List<Order> orders, Scanner scanner) {
        System.out.print("Enter Order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

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
                newOrder.addItem(itemName);
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
                    System.out.println("- " + item);
                }
                System.out.println("Paid: " + order.isPaid());
                System.out.println("------------");
            }
        }
    }

    public static void deleteExistingOrder(List<Order> orders, Scanner scanner) {
        System.out.print("Enter the Order ID to delete: ");
        int orderIdToDelete = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

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

    // New method to allow user to pay for orders
    public static void payOrder(List<Order> orders, Scanner scanner) {
        System.out.println("--- Pay for Order ---");
        System.out.print("Enter the Order ID to pay for: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character after reading the orderId

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
                System.out.print("Enter the payment amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character after reading the amount

                orderToPay.markAsPaid();
                System.out.println("Payment for Order ID " + orderId + " has been processed.");
            }
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }
}