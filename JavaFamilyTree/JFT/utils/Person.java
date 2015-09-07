package JFT.utils;

import java.util.ArrayList;

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
	public void marryTo(String name){
		
	}
	public void addChild(String childName, String spouseName){
		
	}
	public String findRelation(String name){
		return "test";
	}
	public ArrayList<String> listRelations(){
		ArrayList<String> test = new ArrayList<String>();
		return test;
	}
	public boolean isRelatedBy (String relationship, String name){
		return false;
	}
	
	
	
	
	
	
	
}