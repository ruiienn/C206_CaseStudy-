
import java.util.ArrayList; 
import java.util.List; 

public class Feedback { 
	private List<String> feedbackList; 

	public Feedback() { 
		feedbackList = new ArrayList<>(); 
	} 

	public void addFeedback(String loggedInUser, String feedback) { 
		feedbackList.add(loggedInUser + ": " + feedback); 
	} 

	public boolean deleteFeedback(int index) { 
		if (index >= 0 && index < feedbackList.size()) { 
			feedbackList.remove(index); 
			return true; 
		} 
		return false; 
	} 

	public void viewFeedback() { 
		System.out.println("--- View Feedback ---"); 
		if (feedbackList.isEmpty()) { 
			System.out.println("No feedback found."); 
		} else { 
			for (int i = 0; i < feedbackList.size(); i++) { 
				System.out.println("Feedback " + (i + 1) + ": " + feedbackList.get(i)); 
			} 
		} 
	} 
}
