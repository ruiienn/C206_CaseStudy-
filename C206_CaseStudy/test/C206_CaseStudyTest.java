import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class C206_CaseStudyTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

	@Test
	public void testCreateNewOrder() { //Sheena
		// Set up custom InputStream to simulate user input
		String input = "John Doe\nPizza\n5.00\ndone\n";
		InputStream testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		List<Order> orders = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);

		Order.createNewOrder(orders, scanner);

		assertEquals(1, orders.size());
		Order order = orders.get(0);
		assertNotNull(order);
		assertEquals("John Doe", order.getCustomerName());
		assertFalse(order.isPaid());
		assertEquals(1, order.getItems().size());
		assertTrue(order.getItems().contains("Pizza"));
	}

	@Test
	public void testdeleteExistingOrders() { //Sheena
		// Creating a new order to test if the order can be deleted.
		List<Order> orders = new ArrayList<>();
		Order testOrder = new Order(1234, "Test Customer");
		orders.add(testOrder);

		String input = "1234\nyes\n"; // Simulating the user input
		InputStream testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		// Calling the method that is being tested
		Order.deleteExistingOrder(orders, new Scanner(System.in));
		// Verifying that the order has been deleted
		assertEquals(0, orders.size());

	}

	@Test
	public void testViewAllOrders() {
		List<Order> orders = new ArrayList<>();

		Order order1 = new Order(1234, "Sheena");
		Order order2 = new Order(2345, "Jun Yang");

		orders.add(order1);
		orders.add(order2);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // Creating a new instance
		System.setOut(new PrintStream(outContent)); 

		Order.viewAllOrders(orders);

		String expectedOutput = "--- All Orders ---\n" +
				"Order ID: 1234\n" +
				"Customer Name: Sheena\n" +
				"Items:\n" +
				"Paid: false\n" +
				"------------\n" +
				"Order ID: 2345\n" +
				"Customer Name: Jun Yang\n" +
				"Items:\n" +
				"Paid: false\n" +
				"------------\n";

		assertEquals("Test that the output is correct", expectedOutput, outContent.toString());
	}

	@Test
	public void testPayOrder() { //sherwayne
		List<Order> orders = new ArrayList<>();
		Order order = new Order(1001, "Alice");
		order.addItem("Burger", 8.50);
		orders.add(order);

		// Set up custom InputStream to simulate user input
		String input = "1001\n10.00\n";
		InputStream testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		Scanner scanner = new Scanner(System.in);

		Order.payOrder(orders, scanner);

		assertTrue(order.isPaid());
	}
	@Test
	public void testAddMenuItem() {//Sherwayne
		Stall stall = new Stall("Test Stall", "Test Cuisine", "Test Location", 1);
		stall.addMenuItem("Item 1", 10.00);

		assertEquals(1, stall.getMenu().size());
		assertEquals("Item 1", stall.getMenu().get(0).getName());
		assertEquals(10.00, stall.getMenu().get(0).getPrice(), 0.001);
	}

	@Test
	public void testRemoveMenuItem() {//Sherwayne
		Stall stall = new Stall("Test Stall", "Test Cuisine", "Test Location", 1);
		stall.addMenuItem("Item 1", 10.00);
		stall.addMenuItem("Item 2", 15.00);

		stall.removeMenuItem("Item 1");

		assertEquals(1, stall.getMenu().size());
		assertEquals("Item 2", stall.getMenu().get(0).getName());
	}

	@Test
	public void testRemoveMenuItemNotFound() {//Sherwayne
		Stall stall = new Stall("Test Stall", "Test Cuisine", "Test Location", 1);
		stall.addMenuItem("Item 1", 10.00);

		stall.removeMenuItem("Item 2");

		assertEquals(1, stall.getMenu().size());
		assertEquals("Item 1", stall.getMenu().get(0).getName());
	}

	@Test
	public void testViewAllStallsNonEmpty() {
		List<Stall> stalls = new ArrayList<>();
		Stall stall1 = new Stall("Delicious Noodles", "Chinese", "Food Court A", 1);
		stall1.addMenuItem("Noodle Soup", 13.00);
		stall1.addMenuItem("Fried Dumplings", 13.00);
		stall1.addMenuItem("Spring Rolls", 13.00);

		Stall stall2 = new Stall("Pizza Paradise", "Italian", "Food Court B", 2);
		stall2.addMenuItem("Margherita Pizza", 13.00);
		stall2.addMenuItem("Pepperoni Pizza", 13.00);
		stall2.addMenuItem("Hawaiian Pizza", 13.00);

		Stall stall3 = new Stall("Burger Junction", "American", "Food Court C", 3);
		stall3.addMenuItem("Classic Cheeseburger", 13.00);
		stall3.addMenuItem("BBQ Bacon Burger", 13.00);
		stall3.addMenuItem("Veggie Burger", 13.00);

		stalls.add(stall1);
		stalls.add(stall2);
		stalls.add(stall3);

		// Redirect System.out to capture the printed output
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		Stall.viewAllStalls(stalls);

		String expectedOutput = "=============================================\n" +
				"--- All Stalls ---\n" +
				"Stall Name: Delicious Noodles\n" +
				"Cuisine: Chinese\n" +
				"Location: Food Court A\n" +
				"Stall Id: 1\n" +
				"\n" +
				"Menu:\n" +
				"= Noodle Soup ($13.0)\n" +
				"= Fried Dumplings ($13.0)\n" +
				"= Spring Rolls ($13.0)\n" +
				"=============================================\n" +
				"Stall Name: Pizza Paradise\n" +
				"Cuisine: Italian\n" +
				"Location: Food Court B\n" +
				"Stall Id: 2\n" +
				"\n" +
				"Menu:\n" +
				"= Margherita Pizza ($13.0)\n" +
				"= Pepperoni Pizza ($13.0)\n" +
				"= Hawaiian Pizza ($13.0)\n" +
				"=============================================\n" +
				"Stall Name: Burger Junction\n" +
				"Cuisine: American\n" +
				"Location: Food Court C\n" +
				"Stall Id: 3\n" +
				"\n" +
				"Menu:\n" +
				"= Classic Cheeseburger ($13.0)\n" +
				"= BBQ Bacon Burger ($13.0)\n" +
				"= Veggie Burger ($13.0)\n" +
				"=============================================\n";
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testCreateNewUser() { //Ashley

		String input = "Tom\npassword123\ndone\n";
		InputStream testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		List<User> users = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);

		User.createNewUser(users, scanner);

		assertEquals(1, users.size());
		User user = users.get(0);
		assertNotNull(user);
		assertEquals("Tom", user.getUsername());
		assertEquals("spiderman", user.getPassword());
	}

	@Test
	public void testDeleteExistingUser() { //Ashley

		String input = "Alice\ndelete\n";
		InputStream testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		List<User> users = new ArrayList<>();
		users.add(new User("Tom", "spiderman12","customer")); // Add a user for testing

		Scanner scanner = new Scanner(System.in);

		User.deleteExistingUser(users, scanner);

		assertEquals(0, users.size()); // Expecting user to be deleted
		assertNotEquals(1,users.size()); // user not deleted
	}

	@Test
	public void testViewAllUsers() { //Ashley
		List<User> users = new ArrayList<>();
		users.add(new User("harry", "potter123","customer"));
		users.add(new User("james", "bond456","admin"));
		users.add(new User("snow", "white789","stall owner"));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		User.viewAllUsers(users);

		String expectedOutput = "User List:\n" +
				"1. Username: harry\n" +
				"2. Username: james\n" +
				"3. Username: snow\n";
		String actualOutput = outputStream.toString();

		assertEquals(expectedOutput, actualOutput);
	}


	@Test
	public void testAddFeedback() {//kai
		Feedback feedback = new Feedback();
		feedback.addFeedback("John", "Great service!");

		assertEquals(1, feedback.getFeedbackList().size());
		assertEquals("John: Great service!", feedback.getFeedbackList().get(0));
	}

	@Test
	public void testDeleteFeedback() {//kai
		Feedback feedback = new Feedback();
		feedback.addFeedback("John", "Great service!");

		assertTrue(feedback.deleteFeedback(0));
		assertEquals(0, feedback.getFeedbackList().size());
	}

	@Test
	public void testDeleteFeedbackInvalidIndex() {//kai
		Feedback feedback = new Feedback();
		feedback.addFeedback("John", "Great service!");

		assertFalse(feedback.deleteFeedback(1)); // Index 1 is out of range
		assertEquals(1, feedback.getFeedbackList().size());
	}

	@Test
	public void testViewFeedback() {//kai
		Feedback feedback = new Feedback();
		feedback.addFeedback("John", "Great service!");
		feedback.addFeedback("Alice", "Delicious food!");

		String expectedOutput = "--- View Feedback ---\n" +
				"Feedback 1: John: Great service!\n" +
				"Feedback 2: Alice: Delicious food!\n";

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		feedback.viewFeedback();
		String actualOutput = outputStream.toString().trim(); // Trim trailing newline

		assertEquals(expectedOutput, actualOutput);
	}

	public class QueueTest {

		@Test
		public void testQueueForMultipleStalls() { // Adawia
			Queue customerQueue = new Queue();
			Stall stall1 = new Stall("Delicious Noodles", "Chinese", "Food Court A", 1);
			Stall stall2 = new Stall("Pizza Paradise", "Italian", "Food Court B", 2);
			Stall stall3 = new Stall("Burger Junction", "American", "Food Court C", 3);
			List<Stall> stalls = new ArrayList<>(Arrays.asList(stall1, stall2, stall3));

			// Set up custom InputStream to simulate user input
			String input = "1\n2\n3\n0\n";
			InputStream testIn = new ByteArrayInputStream(input.getBytes());
			System.setIn(testIn);

			Scanner scanner = new Scanner(System.in);

			Queue.queueForMultipleStalls(customerQueue, stalls, scanner);

			List<Stall> queuedStalls = customerQueue.getQueuedStalls();
			assertEquals(3, queuedStalls.size()); // Check the number of stalls queued
			assertTrue(queuedStalls.contains(stall1)); // Check if stall1 is in the queue
			assertTrue(queuedStalls.contains(stall2)); // Check if stall2 is in the queue
			assertTrue(queuedStalls.contains(stall3)); // Check if stall2 is in the queue
		}
	}
	@Test
	public void testViewQueuedStalls() { // Adawia
		Queue customerQueue = new Queue();
		Stall stall1 = new Stall("Delicious Noodles", "Chinese", "Food Court A", 1);
		Stall stall2 = new Stall("Pizza Paradise", "Italian", "Food Court B", 2);
		Stall stall3 = new Stall("Burger Junction", "American", "Food Court C", 3);
		customerQueue.addToQueue(stall1);
		customerQueue.addToQueue(stall2);
		customerQueue.addToQueue(stall3);

		// Redirect system output to capture printed messages
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream originalOutput = System.out;
		System.setOut(new PrintStream(outputStream));

		Queue.viewQueuedStalls(customerQueue);

		// Restore original system output
		System.setOut(originalOutput);

		// Check the output of the method
		String expectedOutput = "--- Queued Stalls ---\n1. Delicious Noodles\n2. Pizza Paradise\n3. Burger Junction\n";
		assertEquals(expectedOutput, outputStream.toString());
	}

	@Test
	public void testViewQueuedStallsEmptyQueue() {
		Queue customerQueue = new Queue();

		// Redirect system output to capture printed messages
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream originalOutput = System.out;
		System.setOut(new PrintStream(outputStream));

		Queue.viewQueuedStalls(customerQueue);

		// Restore original system output
		System.setOut(originalOutput);

		// Check the output of the method when the queue is empty
		String expectedOutput = "You have not queued for any stalls.\n";
		assertEquals(expectedOutput, outputStream.toString());
	}
	@Test
	public void testLeaveQueue() { //Adawia
		Queue customerQueue = new Queue();
		Stall stall1 = new Stall("Delicious Noodles", "Chinese", "Food Court A", 1);
		Stall stall2 = new Stall("Pizza Paradise", "Italian", "Food Court B", 2);
		Stall stall3 = new Stall("Burger Junction", "American", "Food Court C", 3);
		customerQueue.addToQueue(stall1);
		customerQueue.addToQueue(stall2);
		customerQueue.addToQueue(stall3);

		// Set up custom InputStream to simulate user input
		String input = "1\ny\n";
		InputStream testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		Scanner scanner = new Scanner(System.in);

		Queue.leaveQueue(customerQueue, scanner);

		// Verify that the stall was removed from the queue
		assertEquals(1, customerQueue.getQueuedStalls().size());
		assertFalse(customerQueue.getQueuedStalls().contains(stall1));

		// Verify the printed messages
		String expectedOutput = "--- Queued Stalls ---\n1. Delicious Noodles\n2. Pizza Paradise\n3. Burger Junction\n"
				+ "Are you sure you want to leave the queue? (y/n) > "
				+ "You have left the queue for Delicious Noodles\n"
				+ " You have successfully left the queue\n";
		assertEquals(expectedOutput, getSystemOut());
	}

	@Test
	public void testLeaveQueueEmptyQueue() { // Adawia
		Queue customerQueue = new Queue();

		// Set up custom InputStream to simulate user input
		String input = "1\n";
		InputStream testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		Scanner scanner = new Scanner(System.in);

		Queue.leaveQueue(customerQueue, scanner);

		// Verify that the method handles an empty queue correctly
		assertEquals(0, customerQueue.getQueuedStalls().size());

		// Verify the printed messages for an empty queue
		String expectedOutput = "You have not queued for any stalls.\n";
		assertEquals(expectedOutput, getSystemOut());
	}

	// Helper method to capture printed messages
	private String getSystemOut() {
		return new java.io.ByteArrayOutputStream().toString();
	}
}