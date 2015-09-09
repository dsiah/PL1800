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
		
		// (TODO) Check for other cases where related and topLevel is the one passed in. 
		//if (this.isTopLevel)
			//return "unrelated";
		
		//check for parent
		if (!this.isTopLevel){
			
			if (this.parents[0].equals(name) || this.parents[1].equals(name))
				return "parent";
		}
		
		//check if sibling
		if (!this.isTopLevel)
			if ((this.parents[0].equals(ph.lookupPerson(name).parents[0]))&&this.parents[1].equals(ph.lookupPerson(name).parents[1]))
				return "sibling";
		
		//check for ancestor
		if (this.getAncestors(ph).contains(name))
			return "ancestor";
		
		//check for related
		ArrayList<String> ancestors1 = this.getAncestors(ph);
		ArrayList<String> ancestors2 = ph.lookupPerson(name).getAncestors(ph);
		for (int i=0; i<ancestors1.size(); i++){
			for (int j=0; j<ancestors2.size(); j++){
				if (ancestors1.get(i)==ancestors2.get(j))
					return "related";
			}
		}
		
		return "unrelated";
	}
	
	public ArrayList<String> listRelations(PeopleHash ph){
		ArrayList<String> relations = new ArrayList<String>();
		ArrayList<String> ancestors = this.getAncestors(ph);
		for (int i = 0; i < ancestors.size(); i++){//for each ancestor, adds all descendants
			relations.addAll(ph.lookupPerson(ancestors.get(i)).getDescendants(ph));
		}		
		return relations;
	}
	
	public boolean isRelatedBy (String relationship, String name, PeopleHash ph){
		String temp = this.findRelation(name, ph);
		//System.out.println(temp);
		if (relationship.equals(temp))
			return true;
		
		return false;
	}
	
	// (TODO) Fix this method
	public ArrayList<String> getAncestors (PeopleHash ph){
		ArrayList<String> ancestors  = new ArrayList<String>();

		if (!this.isTopLevel) {
			//System.out.println(this.name);
			//System.out.println(Arrays.toString(this.parents));
			String parent1 = this.parents[0];
			String parent2 = this.parents[1];
			ancestors.addAll(ph.lookupPerson(parent1).getAncestorsRec(ancestors, ph));
			ancestors.addAll(ph.lookupPerson(parent2).getAncestorsRec(ancestors, ph));
			ancestors.add(parent1);
			ancestors.add(parent2);
			ancestors.add(this.name);
		} else {
			ancestors.add(this.name);
			
		}
		return ancestors;
	}
	public ArrayList<String> getAncestorsRec (ArrayList<String> ancestors, PeopleHash ph){

		if (!this.isTopLevel) {
			//System.out.println(this.name);
			//System.out.println(Arrays.toString(this.parents));
			String parent1 = this.parents[0];
			String parent2 = this.parents[1];
			ancestors.addAll(ph.lookupPerson(parent1).getAncestorsRec(ancestors, ph));
			ancestors.addAll(ph.lookupPerson(parent2).getAncestorsRec(ancestors, ph));
			ancestors.add(parent1);
			ancestors.add(parent2);
			ancestors.add(this.name);
			return ancestors;
		} else {
			ancestors.add(this.name);
			return ancestors;
		}
		
	}
	
	public ArrayList<String> whoIs(String relationship, PeopleHash ph){
		ArrayList<String> peopleList = new ArrayList<String>();
		//iterate through peopleHash, checking each person, and if this.isRelatedBy(relationship, each)==true
		//then add them to peopleList. 
		for (String key : ph.ph.keySet()){
			if(this.isRelatedBy(relationship, key, ph)==true){
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