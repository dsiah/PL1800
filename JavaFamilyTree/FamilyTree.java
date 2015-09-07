import JFT.utils.*;

public class FamilyTree {
	public static void main(String[] args) {
		PeopleHash ph = new PeopleHash();
		Parser p = new Parser(ph);
		p.parse(); // will keep parsing until you enter Ctrl-D in terminal/console 
		
		//Person sid = new Person("Sid");
		//ph.addPerson("Sid", sid);
		//System.out.println(ph.lookupPerson("Sid"));
	}
}