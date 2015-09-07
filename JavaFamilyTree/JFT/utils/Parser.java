package JFT.utils;

import java.util.Scanner;

public class Parser {
	private final Scanner s = new Scanner(System.in);
	
	public void scanLine() {
		String line = s.nextLine();
		determineCommand(line);
	}

	public void determineCommand(String in) {
		switch (in) {
			case ("E"):
				// Call event method
				event();
				System.out.println("Event seen!");
				break;

			case ("R"):
				// Call closestRelation Question
				break;

			case ("X"):
				// Call isRelatedBy Question
				break;

			case ("W"):
				// Call get listRelatives
				break;
		}
	}

	// All the private methods to be called from the cases
	void event() {
		System.out.println("Calling Event!");
		return;
	}

	String closestRelation(String name1, String name2) {
		return "RELATIONSHIP GOES HERE"; // or unrelated
	}

	boolean isRelatedBy(String name1, String relation, String name2) {
		return false; // Print Yes or No
	}

	String listRelatives(String name1) {
		// Must be sorted alphabetically & listed
		return "RELATIVES GO HERE";
	}
}