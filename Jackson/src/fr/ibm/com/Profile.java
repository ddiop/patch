package fr.ibm.com;

import java.io.Serializable;


public class Profile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1064314433108318277L;
	String email = "";
	String location = "";
	
	public Profile(){
		
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String toString(){
		return "\"email\":\"" + getEmail() + "\",\"location\":\"" + getLocation() + "\"";
	}
}
