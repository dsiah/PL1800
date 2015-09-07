package JFT.utils;

import java.io.File;
//import java.util.Arrays;
import java.util.Scanner;

public class Parser {
	private PeopleHash ph;
	private final Scanner s = new Scanner(System.in);
	
	public Parser(PeopleHash ph) {
		this.ph = ph;
	}
	
	// This parse function is for testing using Command Line
	public void parse() {
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String[] operands = line.split(" ");
			determineCommand(operands);	
		}
	}
	
	// This parse function is for testing using an Input File (txt)
	public void parse(File f)  {
		return;
	}

	public void determineCommand(String[] in) {
		int opNum = in.length; // number of operands
		
		if (opNum == 3)
			this.case3Operands(in[0], in[1], in[2]);
		else
			this.case4Operands(in[0], in[1], in[2], in[3]);
	}
	
	public void case3Operands(String op1, String op2, String op3) {
		switch (op1) {
			case ("W"): // List-relationships in order 
				break;
			
			case ("R"): // What-relationship question
				break;
			
			case ("E"): // Marriage Event
				break;
			
			default:
				System.out.println("Not a recognizable event");
				break;
		}
	}
	
	public void case4Operands(String op1, String op2, String op3, String op4) {
		switch(op1) {
			case ("X"): // Is-Relationship Question
				break;
			
			case ("E"): // Birth Event
				break;
			
			default:
				System.out.println("Not a recognizable event");
				break;
		}
	}
}