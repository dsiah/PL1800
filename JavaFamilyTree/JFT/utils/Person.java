package JFT.utils;

import java.util.ArrayList;

public class Person {
	String name;
	boolean isTopLevel; // Adam or Eve
	String[] parents = new String[2]; // Max Two parents
	ArrayList<String> ancestors = new ArrayList<String>();
	ArrayList<String> children  = new ArrayList<String>();
	
	public Person(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}