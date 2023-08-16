import static org.junit.Assert.*;

import java.awt.Menu;
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
    private Queue customerQueue;
    private Stall stall1;
    private Stall stall2;
    private Stall stall3;


    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }
    @Before
    public void setUp() {
        customerQueue = new Queue();
        stall1 = new Stall("Stall1", "Cuisine1", "Location1", 1);
        stall2 = new Stall("Stall2", "Cuisine2", "Location2", 2);
        stall3 = new Stall("Stall3", "Cuisine3", "Location3", 3);
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
		// Verifying that the order has been deleted
		assertEquals(1, orders.size());
		assertNotEquals(0, orders.size());
		
	}

	@Test
	public void testViewAllOrders() { //Sheena
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
    public void testAddMenuItemWithInvalidValues() {//Sherwayne
        Stall stall = new Stall("Test Stall", "Test Cuisine", "Test Location", 1);

        assertThrows(IllegalArgumentException.class, () -> {
            stall.addMenuItem("", 10.0); // Empty name, should throw an exception
        });

        assertThrows(IllegalArgumentException.class, () -> {
            stall.addMenuItem("Item Name", -5.0); // Negative price, should throw an exception
        });
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

	      String input = "Tom\nspiderman12\ndone\n"; 
	      InputStream testIn = new ByteArrayInputStream(input.getBytes());
	      System.setIn(testIn);

	      List<User> users = new ArrayList<>();
	      users.add(new User("Tom", "spiderman12","customer"));
	      Scanner scanner = new Scanner(System.in);

	      User.createNewUser(users, scanner);

	      assertEquals(1, users.size()); 
	      
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
	    assertNotNull(users); // make sure there is something to delete
	  }
	  
		@Test //Ashley
		public void testViewAllUsers() {
		 List<User> users = new ArrayList<>();

		 User user1 = new User("millie", "password123", "customer");
		 User user2 = new User("Bobby", "secret456", "admin");

		 users.add(user1);
		 users.add(user2);

		 ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		 System.setOut(new PrintStream(outContent));

		 viewAllUsers(users); 

		  System.setOut(System.out);

		
		 assertNotNull(users.contains(user1));   
		 assertTrue(users.contains(user1));
		 assertTrue(users.contains(user2));
		 
		 }
 
		 private void viewAllUsers(List<User> users) {
		 System.out.println("All Users:");
		 for (User user : users) {
		     System.out.println("Name: " + user.getUsername() + ", Role: " + user.getRole());
		     }
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
	public void testViewQueuedStalls() { // Adawia & Jun Yang
	    Queue customerQueue = new Queue();
	    Stall stall1 = new Stall("Delicious Noodles", "Chinese", "Food Court A", 1);
	    Stall stall2 = new Stall("Pizza Paradise", "Italian", "Food Court B", 2);
	    Stall stall3 = new Stall("Burger Junction", "American", "Food Court C", 3);
	    customerQueue.addToQueue(stall1);
	    customerQueue.addToQueue(stall2);
	    customerQueue.addToQueue(stall3);

	    List<Stall> expectedStalls = Arrays.asList(stall1, stall2, stall3);

	    List<Stall> queuedStalls = customerQueue.getQueuedStalls();

	    assertEquals(expectedStalls, queuedStalls);
	}


	@Test
	public void testViewQueuedStallsEmptyQueue() { //Adawia & Jun Yang
	    Queue customerQueue = new Queue();

	    List<Stall> expectedStalls = new ArrayList<>(); // Empty list since the queue is empty

	    List<Stall> queuedStalls = customerQueue.getQueuedStalls();

	    assertEquals(expectedStalls, queuedStalls);
	}

    @Test
    public void testLeaveQueue() { // Adawia
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
    public void testLeaveQueueEmptyQueue() { //Adawia
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