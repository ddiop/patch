

package fr.ibm.com;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;




public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9136948866701460605L;
	
	String uid = "";
	String lastName = "";
	String firstName = "";
	String adress = "";
	String birthDate = "";
	ArrayList<Profile> profiles = new ArrayList<Profile>();
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public List<Profile> getProfiles() {
		return profiles;
	}
	public void setProfiles(ArrayList<Profile> profiles) {
		this.profiles = profiles;
	}
	public String toString(){
		String r = "{\"uid\":\""+getUid()+"\",\"lastName\":\""+getLastName()+"\",\"firstName\":\""+getFirstName()+"\",\"adress\":\""+getAdress()+"\",\"profiles\":[";
		for (int i = 0; i < profiles.size(); i++){
			r+="{" + profiles.get(i).toString() + "}";
			if(i+1 < profiles.size()){
				r+=",";
			}
		}
		r+="],\"birthDate\":\"" + getBirthDate() +"\"}";
		return r;
	}
}

