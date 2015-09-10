package JFT.utils;

import java.io.File;
import java.io.FileNotFoundException;
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
			line = line.toLowerCase();
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
		else if (opNum == 4)
			this.case4Operands(in[0], in[1], in[2], in[3]);
		else
			return;
	}
	
	public void checkForPeople(String p1) {
		if (ph.lookupPerson(p1) == null) {
			Person parent1 = new Person(p1);
			parent1.isTopLevel = true;
			//System.out.println(p1 + " " + Boolean.toString(parent1.isTopLevel));
			ph.addPerson(p1, parent1);
		}
	}
	
	
	public void checkForPeople(String p1, String p2) {
		if (ph.lookupPerson(p1) == null) {
			Person parent1 = new Person(p1);
			parent1.isTopLevel = true;
			//System.out.println(p1 + " " + Boolean.toString(parent1.isTopLevel));
			ph.addPerson(p1, parent1);
		}
		
		if (ph.lookupPerson(p2) ==  null) {
			Person parent2 = new Person(p2);
			parent2.isTopLevel = true;
			//System.out.println(p2 + " " + Boolean.toString(parent2.isTopLevel));
			ph.addPerson(p2, parent2);
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
		String question = op1 + " " + op2 + " " + op3;
		
		switch (op1) {
			case ("w"): // List-relationships in order 
				
				Person firstPerson = ph.lookupPerson(op3);
				String relationship = op2;
				System.out.println(question);
				System.out.println(firstPerson.whoIs(relationship, ph));
				System.out.println();
				
				break;
			
			case ("r"): // What-relationship question
				checkForPeople(op3, op2); // check for people in PeopleHash and generate accordingly
				Person firstp = ph.lookupPerson(op3);
				String answer = firstp.findRelation(op2, ph) + "\n"; 
				System.out.println(question);
				System.out.println(answer);
				break;
			
			case ("e"): // Marriage Event
				Person fp = ph.lookupPerson(op2);
				fp.marryTo(op3, ph);
				break;
			
			default:
				System.out.println("Not a recognizable event");
				break;
		}
	}
	
	public void case4Operands(String op1, String op2, String op3, String op4) {
		
		switch(op1) {
			case ("x"): // Is-Relationship Question
				String question = op1 + " " + op2 + " " + op3 + " " + op4;
				
				checkForPeople(op4, op2);
				Person firstPerson = ph.lookupPerson(op4);
				boolean ans = firstPerson.isRelatedBy(op3, op2, ph);
				
				String answer = (ans ? "Yes" : "No") + "\n";
				
				System.out.println(question);
				System.out.println(answer);
				
				break;
			
			case ("e"): // Birth Event
				checkForPeople(op2, op3, op4);
				Person firstp = ph.lookupPerson(op2);
				
				firstp.addChild(op4, op3, ph);
				break;
			
			default:
				System.out.println("Not a recognizable event");
				break;
		}
	}
}