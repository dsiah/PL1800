import JFT.utils.*;

public class FamilyTree {
	public static void main(String[] args) {
		System.out.println("HELLO");
		
		//Parser p = new Parser();
		//p.scanLine();
		
		Person sid = new Person("Sid");
		PeopleHash ph = new PeopleHash();
		ph.addPerson("Sid", sid);
		
		System.out.println(ph.lookupPerson("Sid"));
		System.out.println(ph.lookupPerson("Bob"));
	}
}