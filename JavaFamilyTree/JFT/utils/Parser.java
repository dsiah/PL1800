package JFT.utils;

import java.io.File;
import java.io.FileNotFoundException;
//import java.util.Arrays;
import java.util.Scanner;

public class Parser {
	private PeopleHash ph;
	private Scanner s;
	
	public Parser(PeopleHash ph) {
		this.ph = ph;
	}
	
	// This parse function is for testing using Command Line
	public void parse() {
		s = new Scanner(System.in);
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String[] operands = line.split(" ");
			determineCommand(operands);	
		}
	}
	
	// This parse function is for testing using an Input File (txt)
	public void parse(File f)  {
		try {
			s = new Scanner(f);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] operands = line.split(" ");
				determineCommand(operands);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}

		return;
	}

	public void determineCommand(String[] in) {
		int opNum = in.length; // number of operands
		
		if (opNum == 3)
			this.case3Operands(in[0], in[1], in[2]);
		else
			this.case4Operands(in[0], in[1], in[2], in[3]);
	}
	
	public void checkForPeople(String p1, String p2) {
		if (ph.lookupPerson(p1) == null) {
			Person parent1 = new Person(p1);
			ph.addPerson(p1, parent1);
			parent1.isTopLevel = true;
		}
		
		if (ph.lookupPerson(p2) ==  null) {
			Person parent2 = new Person(p2);
			ph.addPerson(p2, parent2);
			parent2.isTopLevel = true;
		}
	}
	
	public void checkForPeople(String p1, String p2, String p3) {
		// multi-arity function will call the 2 arg version
		// of checkForPeople then check for third person. 
		checkForPeople(p1, p2); 
		
		if (ph.lookupPerson(p3) == null)
			ph.addPerson(p3, new Person(p3));
	}
	
	public void case3Operands(String op1, String op2, String op3) {
		checkForPeople(op2, op3); // check for people in PeopleHash and generate accordingly
		String question = op1 + " " + op2 + " " + op3;
		Person firstPerson = ph.lookupPerson(op2); // track first person
		
		switch (op1) {
			case ("W"): // List-relationships in order 
				
				break;
			
			case ("R"): // What-relationship question
				String answer = firstPerson.findRelation(op3, ph) + "\n"; 
				System.out.println(question);
				System.out.println(answer);
				break;
			
			case ("E"): // Marriage Event
				firstPerson.marryTo(op3, ph);
				break;
			
			default:
				System.out.println("Not a recognizable event");
				break;
		}
	}
	
	public void case4Operands(String op1, String op2, String op3, String op4) {
		checkForPeople(op2, op3, op4);
		String question = op1 + " " + op2 + " " + op3 + " " + op4;
		Person firstPerson = ph.lookupPerson(op2);
		
		switch(op1) {
			case ("X"): // Is-Relationship Question
				boolean ans = firstPerson.isRelatedBy(op3, op4, ph);
				String answer = Boolean.toString(ans) + "\n";
				System.out.println(answer);
				break;
			
			case ("E"): // Birth Event
				firstPerson.addChild(op4, op3, ph);
				break;
			
			default:
				System.out.println("Not a recognizable event");
				break;
		}
	}
}