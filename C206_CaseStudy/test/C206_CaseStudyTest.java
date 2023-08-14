import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class C206_CaseStudyTest {
	
	
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


	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	


}