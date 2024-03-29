package JFT.utils;
import JFT.utils.Person;

import java.util.HashMap;

public class PeopleHash {
	public HashMap<String, Person> ph = new HashMap<String, Person>();

	public void addPerson(String name, Person obj) {
		// Insert Person by name and -> to the object itself
		ph.put(name, obj);
	}

	public Person lookupPerson(String name) {
		return ph.get(name);
	}
	
	public String toString(){
		//String allData = new String();
		for (String key : ph.keySet()){
			//System.out.println(key);
			System.out.println(ph.get(key).toString());
		}
		return "done";
		
	}
}