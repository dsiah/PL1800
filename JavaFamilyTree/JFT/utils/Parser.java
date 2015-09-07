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
}