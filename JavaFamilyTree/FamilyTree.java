import java.io.File;

import JFT.utils.*;

public class FamilyTree {
	public static void main(String[] args) {
		PeopleHash ph = new PeopleHash(); // keep this global
		
		Parser p = new Parser(ph);
		
		File f = new File("TestFiles/testCases.txt");
		
		p.parse(f); // will keep parsing until you enter Ctrl-D in terminal/console 	
		
		//ph.toString();
	}
}