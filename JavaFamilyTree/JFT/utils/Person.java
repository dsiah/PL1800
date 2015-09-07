package JFT.utils;

import java.util.ArrayList;
import JFT.utils.PeopleHash;
public class Person {
	String name;
	boolean isTopLevel; // Adam or Eve Generation
	String[] parents = new String[2]; // Max Two parents
	ArrayList<String> spouses  = new ArrayList<String>();
	ArrayList<String> children = new ArrayList<String>();
	
	public Person(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	public void marryTo(String name, PeopleHash ph){
		spouses.add(name);
		ph.lookupPerson(name).spouses.add(name);
		
	}
	public void addChild(String childName, String spouseName, PeopleHash ph){
		children.add(childName);
		ph.lookupPerson(spouseName).children.add(childName);
	}
	public String findRelation(String name, PeopleHash ph){
		//check for child
		if (children.contains(name))
			return "child";
		//check for parent
		if (parents[0]==name || parents[1]==name)
			return "parent";
		//check for married
		//check for ancestor
		//check for related
		return "unrelated";
	}
	public ArrayList<String> listRelations(PeopleHash ph){
		ArrayList<String> test = new ArrayList<String>();
		return test;
	}
	public boolean isRelatedBy (String relationship, String name, PeopleHash ph){
		return false;
	}
	
	
	
	
	
	
	
}