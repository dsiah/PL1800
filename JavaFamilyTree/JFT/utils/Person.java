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
		for (int i=0; i<ancestors.size(); i++){//for each ancestor, adds all descendants
			relations.addAll(ph.lookupPerson(ancestors.get(i)).getDescendants(ph));
		}		
		return relations;
	}
	public boolean isRelatedBy (String relationship, String name, PeopleHash ph){
		String temp= this.findRelation(name, ph);
		if (relationship==temp)
			return true;
		return false;
	}
	public ArrayList<String> getAncestors (PeopleHash ph){
		ArrayList<String> ancestors  = new ArrayList<String>();
		String parent1= ph.lookupPerson(this.toString()).parents[0];
		String parent2= ph.lookupPerson(this.toString()).parents[1];
		if (parent1!=null){ //if not adam and eve generation, ad the parents, and the parents ancestors
			ancestors.addAll(ph.lookupPerson(parent1).getAncestors(ph));
			ancestors.addAll(ph.lookupPerson(parent2).getAncestors(ph));
			ancestors.add(parent1);
			ancestors.add(parent2);
		}
		return ancestors;
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