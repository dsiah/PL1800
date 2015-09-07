import JFT.utils.*;

public class FamilyTree {
	public static void main(String[] args) {
		PeopleHash ph = new PeopleHash(); // keep this global
		Parser p = new Parser(ph);
		p.parse(); // will keep parsing until you enter Ctrl-D in terminal/console 		
	}
}