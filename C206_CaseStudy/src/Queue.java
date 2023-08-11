import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Queue {
	private List<Stall> queuedStalls;

	public Queue() {
		this.queuedStalls = new ArrayList<>();
	}

	public void addToQueue(Stall stall) {
		queuedStalls.add(stall);
	}

	public void removeFromQueue(Stall stall) {
		queuedStalls.remove(stall);
	}

	public List<Stall> getQueuedStalls() {
		return queuedStalls;
	}

	// Make the methods public and static
	public static void queueForMultipleStalls(Queue customerQueue, List<Stall> stalls, Scanner scanner) {
		System.out.println("--- Queue for Multiple Stalls ---");
		System.out.println("Available Stalls:");
		int index = 1;
		for (Stall stall : stalls) {
			System.out.println(index + ". " + stall.getName());
			index++;
		}

		boolean queueMoreStalls = true;
		while (queueMoreStalls) {
			System.out.print("Enter the number of the stall to queue (or 0 to stop queuing): ");
			int stallNumber = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			if (stallNumber == 0) {
				queueMoreStalls = false;
			} else if (stallNumber >= 1 && stallNumber <= stalls.size()) {
				Stall selectedStall = stalls.get(stallNumber - 1);
				customerQueue.addToQueue(selectedStall);
				System.out.println("You have queued for " + selectedStall.getName());
			} else {
				System.out.println("Invalid stall number. Please try again.");
			}
		}

		System.out.println("You have finished queuing for stalls.");
	}

	public static void viewQueuedStalls(Queue customerQueue) {
		System.out.println("--- Queued Stalls ---");
		List<Stall> queuedStalls = customerQueue.getQueuedStalls();
		if (queuedStalls.isEmpty()) {
			System.out.println("You have not queued for any stalls.");
		} else {
			for (int i = 0; i < queuedStalls.size(); i++) {
				System.out.println((i + 1) + ". " + queuedStalls.get(i).getName());
			}
		}
	}

	public static void leaveQueue(Queue customerQueue, Scanner scanner) {
		viewQueuedStalls(customerQueue);

		if (customerQueue.getQueuedStalls().isEmpty()) {
			System.out.println("You have not queued for any stalls.");
			return;
		}

		System.out.print("Enter the number of the stall to leave the queue: ");
		int stallNumber = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character

		if (stallNumber >= 1 && stallNumber <= customerQueue.getQueuedStalls().size()) {
			String confirm = Helper.readString("Are you sure you want to leave the queue? (y/n) > ");
			if (confirm.equalsIgnoreCase("y")) {
				Stall selectedStall = customerQueue.getQueuedStalls().get(stallNumber - 1);
				customerQueue.removeFromQueue(selectedStall);
				System.out.println("You have left the queue for " + selectedStall.getName());
				System.out.println(" You have successfully left the queue");
			} else {
				System.out.println("Leaving queue is unsuccessful!");
			} 
		} else {
			System.out.println("Invalid stall number. Please try again.");
		}
	}
}
