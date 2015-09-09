package JFT.utils;

import JFT.utils.PeopleHash;

import java.util.Arrays;
import java.util.ArrayList;

public class Person {
	String name;
	public boolean isTopLevel; // Adam or Eve Generation
	String[] parents = new String[2]; // Max Two parents
	ArrayList<String> spouses  = new ArrayList<String>();
	ArrayList<String> children = new ArrayList<String>();
	
	public Person(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name + Arrays.toString(this.parents) + this.spouses.toString() + this.children.toString();
	}
	
	public void marryTo(String name, PeopleHash ph){
		this.spouses.add(name); // this person adds name to their spouses
		Person p2 = ph.lookupPerson(name); // find p2 object
		p2.spouses.add(this.name); // p2 adds this to their spouses
	}
	
	public void addChild(String childName, String spouseName, PeopleHash ph){
		if (checkMarriage(this.name, spouseName, ph) == false) {
			//System.out.println("Marrying " + this.name + " to " + spouseName);
			this.marryTo(spouseName, ph);
		}

		Person child = ph.lookupPerson(childName);
		this.children.add(childName); // add child to this persons' children
		ph.lookupPerson(spouseName).children.add(childName); // add child to spouses' children
		child.parents[0] = this.name;
		child.parents[1] = spouseName;
	}
	
	public static boolean checkMarriage(String p1, String p2, PeopleHash ph) {
		Person one = ph.lookupPerson(p1);
		if (one.spouses.contains(p2))
			return true;
		else
			return false;
	}
	
	public String findRelation(String name, PeopleHash ph){
		// check if child
		if (children.contains(name))
			return "child";
		
		//check for married
		if (this.spouses.contains(name))
			return "spouse";
		
		//check for parent
		if (!this.isTopLevel){
			if (this.parents[0].equals(name) || this.parents[1].equals(name))
				return "parent";
		}
		
		//check if sibling
		if (!this.isTopLevel)
			if ((this.parents[0].equals(ph.lookupPerson(name).parents[0]))&&this.parents[1].equals(ph.lookupPerson(name).parents[1]))
				return "sibling";
		
		// (TODO) Test the fuck out of this.
		if (this.getAncestors(ph).contains(name))
			return "ancestor";
		
		//checks relatives
		if (this.getDescendants(ph).contains(name))
			return "related";
		
		//System.out.println(this.getAncestors(ph));
		
		// (TODO) Need to fix
		/*
		ArrayList<String> ancestors1 = this.getAncestors(ph);
		ArrayList<String> ancestors2 = ph.lookupPerson(name).getAncestors(ph);
		for (int i=0; i<ancestors1.size(); i++){
			for (int j=0; j<ancestors2.size(); j++){
				if (ancestors1.get(i)==ancestors2.get(j))
					return "related";
			}
		}
		*/
		
		return "unrelated";
	}
	
	public boolean isRelatedBy (String relationship, String name, PeopleHash ph){
		String temp = this.findRelation(name, ph);
 
		if (relationship.equals(temp))
			return true;
		
		if (relationship.equals("ancestor")) {
			if (temp.equals("parent"))
				return true;
		}
		
		if (relationship.equals("relative")) {
			if (!temp.equals("unrelated")) {
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList<String> getAncestors(PeopleHash ph) {
		ArrayList<String> ans = new ArrayList<String>();
		
		if (this.isTopLevel) {
			ans.add(this.name);
		} else {
			Person parent1 = ph.lookupPerson(this.parents[0]);
			Person parent2 = ph.lookupPerson(this.parents[1]);
			
			ans.add(parent1.name);
			ans.add(parent2.name);
			
			parent1.getAncestors(ph, ans);
			parent2.getAncestors(ph, ans);
		}
		
		return ans;
	}

	public ArrayList<String> getAncestors(PeopleHash ph, ArrayList<String> ansCont) {
		if (this.isTopLevel && ansCont.contains(this.name)) {
			return ansCont;
		} else if (this.isTopLevel && !ansCont.contains(this.name)) {
			ansCont.add(this.name);
		} else {
			Person parent1 = ph.lookupPerson(this.parents[0]);
			Person parent2 = ph.lookupPerson(this.parents[1]);
			
			if (!ansCont.contains(parent1.name)) {
				ansCont.add(parent1.name);
			}
			
			if (!ansCont.contains(parent2.name)) {
				ansCont.add(parent2.name);
			}
			
			parent1.getAncestors(ph, ansCont);
			parent2.getAncestors(ph, ansCont);
		}
		
		return ansCont;
	}
	
	public boolean isAncestor(PeopleHash ph, String name) {
		if(this.isTopLevel) {
			return false;
		}
		else if (this.parents[0].equals(name)) {
			return true;
		}
		else if (this.parents[1].equals(name)) {
			return true;
		}
		else if (this.isAncestor(ph, ph.lookupPerson(this.parents[0]).name)) {
			return true;
		}
		else if (this.isAncestor(ph, ph.lookupPerson(this.parents[1]).name)) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<String> whoIs(String relationship, PeopleHash ph){
		ArrayList<String> peopleList = new ArrayList<String>();
		//iterate through peopleHash, checking each person, and if this.isRelatedBy(relationship, each)==true
		//then add them to peopleList. 
		for (String key : ph.ph.keySet()){
			if(this.isRelatedBy(relationship, key, ph) == true){
				peopleList.add(key);
			}
		}
		return peopleList;
	}
	public ArrayList<String> getDescendants(PeopleHash ph){
		ArrayList<String> descendants  = new ArrayList<String>();
		if (this.children.isEmpty()==false){
			for (int i=0; i<children.size(); i++){//adds children's descendants
				String tempName=children.get(i);
				descendants.addAll(ph.lookupPerson(tempName).getDescendants(ph));
			}	
		}
		descendants.addAll(this.children);//adds the children
		return descendants;
	}
	
	
}