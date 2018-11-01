package com.uninsubria.watchneighbors.model;

import java.io.Serializable;

/**
 * Represents the registered user.
 * @author alessandro, christian, denise, silvia
 *
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String userID;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String city;
	private String address;
	private String district;
	private double usrLat;
	private double usrLng;

	/**
	 * Create a new user.
	 * @param userID The userID of the user.
	 * @param password The password used for managing the events.
	 * @param name The name of the user.
	 * @param surname The surname of the user.
	 * @param email The email address of the user.
	 * @param city The city of the user.
	 * @param address The address of the user.
	 * @param usrLat The user latitude.
	 * @param usrLng The user longitude.
	 * @param district The district of the user.
	 */
	public User(String userID, String password, String name, String surname, String email, String city, String address,
			double usrLat, double usrLng, String district) {
		super();
		this.userID = userID;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.city = city;
		this.address = address;
		this.district = district;
		this.usrLat = usrLat;
		this.usrLng = usrLng;
	}
	
	/** Create a User for with essential information.
	 * @param city The city of the user.
	 * @param district The district of the user.
	 * @param userID The userID of the user.
	 * @param name The name of the user.
	 * @param surname The surname of the user.
	 */
	public User(String city, String district, String userID, String name, String surname) {
		super();
		this.city = city;
		this.district = district;
		this.userID = userID;
		this.name = name;
		this.surname = surname;
	}
	 
	/**
	 * Create a User with userID and password.
	 * @param userID
	 * @param password
	 */
	public User(String userID, String password) {
		super();
		this.userID = userID;
		this.password = password;
	}
	/**
	 * Returns the userID of the user.
	 * @return String the userID.
	 */
	public String getUserID() {
		return userID;
	}
	/**
	 * Sets the userID.
	 * @param userID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	/**
	 * Returns the user password.
	 * @return String the user password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets user password.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Returns the user name.
	 * @return the user name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the user.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns the surname of the user.
	 * @return the surname of the user
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * Sets the user surname.
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * Returns the email of the user.
	 * @return the user email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Sets the email.
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Returns a string representing the city name of the user.
	 * @return
	 */
	public String getCity() {
		return city;
	}
	/**
	 * Sets the city name of the user.
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * Returns the address of the user - usually a street name with number.
	 * @return the address selected by the user during the registration process
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * Sets the address.
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * Returns the user latitude.
	 * @return the user latitude.
	 */
	public double getUsrLat() {
		return usrLat;
	}
	/**
	 * Sets the user latitude.
	 * @param usrLat
	 */
	public void setUsrLat(double usrLat) {
		this.usrLat = usrLat;
	}
	/**
	 * Returns the user longitude.
	 * @return the user longitude
	 */
	public double getUsrLng() {
		return usrLng;
	}
	/**
	 * Sets the user longitude.
	 * @param usrLng
	 */
	public void setUsrLong(double usrLng) {
		this.usrLng = usrLng;
	}
	/**
	 * Returns the district name of the user.
	 * @return the district name selected by the user during the registration process
	 */
	public String getDistrict() {
		return district;
	}
	/**
	 * Sets the district name.
	 * @param district
	 */
	public void setDistrict(String district) {
		this.district = district;
	}
	/**
	 * Returns the user position - latitude and longitude pair.
	 * @return the user position
	 */
	public Geoposition getGeoposition() {
		return new Geoposition(this.usrLat, this.usrLng);
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", city=" + city + ", address=" + address + ", usrLat=" + usrLat + ", usrLong="
				+ usrLng + ", district=" + district + "]";
	}
}