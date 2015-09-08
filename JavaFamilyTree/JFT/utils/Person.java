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
		return this.name + Arrays.toString(this.parents) + this.spouses.toString();
	}
	
	public void marryTo(String name, PeopleHash ph){
		this.spouses.add(name); // this person adds name to their spouses
		Person p2 = ph.lookupPerson(name); // find p2 object
		p2.spouses.add(this.name); // p2 adds this to their spouses
	}
	
	public void addChild(String childName, String spouseName, PeopleHash ph){
		if (checkMarriage(this.name, spouseName, ph) == false)
			this.marryTo(spouseName, ph);
			
		children.add(childName);
		ph.lookupPerson(spouseName).children.add(childName);
	}
	
	public static boolean checkMarriage(String p1, String p2, PeopleHash ph) {
		Person one = ph.lookupPerson(p1);
		if (one.spouses.contains(p2))
			return true;
		else
			return false;
	}
	
	public String findRelation(String name, PeopleHash ph){
		//check for child
		if (children.contains(name))
			return "child";
		//check for parent
		if (parents[0]==name || parents[1]==name)
			return "parent";
		//check for married
		if (spouses.contains(name))
			return "spouse";
		//check if sibling
		if (parents==ph.lookupPerson(name).parents)
			return "siblings";
		//check for ancestor
		if (this.getAncestors(ph).contains(name))
			return "ancestor";
		//check for related
		ArrayList<String> ancestors1=this.getAncestors(ph);
		ArrayList<String> ancestors2=ph.lookupPerson(name).getAncestors(ph);
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
		
		if (relationship == temp)
			return true;
		
		return false;
	}
	
	public ArrayList<String> getAncestors (PeopleHash ph){
		ArrayList<String> ancestors  = new ArrayList<String>();
		//System.out.println(this.name);
		//System.out.println(this.isTopLevel);
		if (!this.isTopLevel) {
			String parent1 = ph.lookupPerson(this.toString()).parents[0];
			String parent2 = ph.lookupPerson(this.toString()).parents[1];
			ancestors.addAll(ph.lookupPerson(parent1).getAncestors(ph));
			ancestors.addAll(ph.lookupPerson(parent2).getAncestors(ph));
			ancestors.add(parent1);
			ancestors.add(parent2);
			ancestors.add(this.name);
			return ancestors;
		} else {
			ancestors.add(this.name);
			return ancestors;
		}
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