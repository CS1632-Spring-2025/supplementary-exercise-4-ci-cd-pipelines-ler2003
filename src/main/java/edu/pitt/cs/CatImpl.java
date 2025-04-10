package edu.pitt.cs;

public class CatImpl implements Cat {

	int id;
	String name;
	boolean rented;

	public CatImpl(int id, String name) {
		this.id = id;
		this.name = name;
		rented = false;
	}

	public void rentCat() {
		if(!rented){
			rented = true;
		}
	}

	public void returnCat() {
		if(rented){
			rented = false;
		}
	}

	public void renameCat(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		// TODO: Fill in
		return id;
	}

	public boolean getRented() {
		// TODO: Fill in
		return rented;
	}

	public String toString() {
		// TODO: Fill in
		return ("ID " + id + ". " + name);
	}

}