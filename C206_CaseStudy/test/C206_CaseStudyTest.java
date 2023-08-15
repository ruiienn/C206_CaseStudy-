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
		
		assertTrue(orders.contains(order1));
	    assertTrue(orders.contains(order2));


	}


	@Test
	public void testPayOrder() { //Sherwayne
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
	public void testViewAllStalls() {//Sherwayne
	    List<Stall> stalls = new ArrayList<>();

	    Stall stall1 = new Stall("Delicious Noodles", "Chinese", "Food Court A", 1);
	    stall1.addMenuItem("Noodle Soup", 13.00);
	    stall1.addMenuItem("Fried Dumplings", 13.00);

	    Stall stall2 = new Stall("Pizza Paradise", "Italian", "Food Court B", 2);
	    stall2.addMenuItem("Margherita Pizza", 13.00);

	    stalls.add(stall1);
	    stalls.add(stall2);

	    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));

	    Stall.viewAllStalls(stalls);

	    assertTrue(stalls.contains(stall1));
	    assertTrue(stalls.contains(stall2));
	}

	@Test
    public void testViewAllStallsNotFound() {//Sherwayne
        List<Stall> emptyStalls = new ArrayList<>();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Stall.viewAllStalls(emptyStalls);

        Stall stall1 = new Stall("Delicious Noodles", "Chinese", "Food Court A", 1);
        Stall stall2 = new Stall("Pizza Paradise", "Italian", "Food Court B", 2);

        assertFalse(emptyStalls.contains(stall1));
        assertFalse(emptyStalls.contains(stall2));
    }
	
	@Test
	  public void testCreateNewUser() { //Ashley

	      String input = "Tom\nspiderman12\ndone\n"; // 
	      InputStream testIn = new ByteArrayInputStream(input.getBytes());
	      System.setIn(testIn);

	      List<User> users = new ArrayList<>();
	      users.add(new User("Tom", "spiderman12","customer"));
	      Scanner scanner = new Scanner(System.in);

	      User.createNewUser(users, scanner);

	      assertEquals(1, users.size()); // Check if the user was added to the list
	      User user = users.get(0);
	      assertNotNull(user);
	      assertEquals("Tom", user.getUsername());
	      assertEquals("spiderman12", user.getPassword());
	      assertNotEquals("man", user.getUsername());
	  }

	  @Test
	  public void testDeleteExistingUser() { //Ashley

	    String input = "Tom\ndelete\n";
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

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));

	    feedback.viewFeedback();
	    String actualOutput = outputStream.toString().trim(); // Trim trailing newline

	    assertTrue(actualOutput.contains("Feedback 1: John: Great service!"));
	    assertTrue(actualOutput.contains("Feedback 2: Alice: Delicious food!"));
	}

	 

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

	@Test
    public void testViewQueuedStalls() { // have problem Adawia
        Queue customerQueue = new Queue();
        Stall stall1 = new Stall("Delicious Noodles", "Chinese", "Food Court A", 1);
		Stall stall2 = new Stall("Pizza Paradise", "Italian", "Food Court B", 2);
		Stall stall3 = new Stall("Burger Junction", "American", "Food Court C", 3);
        customerQueue.addToQueue(stall1);
        customerQueue.addToQueue(stall2);
        customerQueue.addToQueue(stall3);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        System.setOut(new PrintStream(outContent));

        Queue.viewQueuedStalls(customerQueue);

        System.setOut(originalOutput);

        String expectedOutput = "--- Queued Stalls ---\n" +
                "1. Delicious Noodles\n" +
                "2. Pizza Paradise\n" +
                "3. Burger Junction\n";

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testViewQueuedStallsEmptyQueue() {
        Queue customerQueue = new Queue();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        System.setOut(new PrintStream(outContent));

        Queue.viewQueuedStalls(customerQueue);

        System.setOut(originalOutput);

        String expectedOutput = "You have not queued for any stalls.\n";

        assertEquals(expectedOutput, outContent.toString());
    }
    
    @Test
    public void testLeaveQueue() { // have problem Adawia
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

        // Verify the printed messages (assertions similar to the previous examples)
    }

    @Test
    public void testLeaveQueueStallNotInQueue() {
        Queue customerQueue = new Queue();
        Stall stall1 = new Stall("Delicious Noodles", "Chinese", "Food Court A", 1);

        // Set up custom InputStream to simulate user input
        String input = "1\n";
        InputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        Scanner scanner = new Scanner(System.in);

        Queue.leaveQueue(customerQueue, scanner);

        // Verify that the method handles a stall not in the queue correctly
        // Assertions for messages and queue state
    }

    @Test
    public void testLeaveQueueEmptyQueue() {
        Queue customerQueue = new Queue();

        // Set up custom InputStream to simulate user input
        String input = "1\n";
        InputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        Scanner scanner = new Scanner(System.in);

        Queue.leaveQueue(customerQueue, scanner);

        // Verify that the method handles an empty queue correctly
        // Assertions for messages and queue state
    }
}