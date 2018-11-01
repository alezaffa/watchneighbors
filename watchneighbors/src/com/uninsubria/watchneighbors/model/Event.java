package com.uninsubria.watchneighbors.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents the event created by the registered user.
 * @author alessandro, christian, denise, silvia
 */
public class Event implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userID;
	private Date dateCreation;
	private Date dateCompletion;
	private String district;
	private String description;
	private String status;
	private String city;
	private String eventName;
	private String outcome;
	private String resolventUser;
	private boolean watchIt;
	private int eventId;
	private double lat;
	private double lon;
	private double usrLat;
	private double usrLong;

	/**
	 * Create an Event without the eventID and the resolvent user.
	 * @param userID The userID of the user that creates the event.
	 * @param dateCreation The event date of creation.
	 * @param dateCompletion The event date of completion.
	 * @param district The district of the event.
	 * @param description The description of the event.
	 * @param status The status of the event.
	 * @param latitude The latitude of the event.
	 * @param longitude The longitude of the event.
	 * @param city The city of the event.
	 * @param watchIt A boolean, true if the event is managed by an user, false otherwise.
	 * @param usrLat The latitude of the user that created the event.
	 * @param usrLong The longitude of the user that created the event.
	 */
	public Event(String userID, Date dateCreation, Date dateCompletion, String district, String description, String status,
		double latitude, double longitude, String city, boolean watchIt, double usrLat, double usrLong) {
		super();
		this.userID = userID;
		this.dateCreation = dateCreation;
		this.dateCompletion = dateCompletion;
		this.district = district;
		this.description = description;
		this.status = status;
		this.lat = latitude;
		this.lon = longitude;
		this.city = city;
		this.watchIt = watchIt;
		this.usrLat = usrLat;
		this.usrLong = usrLong;
		this.setEventName(userID + " on " + dateCreation + " - " + description + " " + district + " " + latitude + " " + longitude);
	}
	/**
	 * Create an Event with eventID, outcome and resolvent user.
	 * @param userID The userID of the user that creates the event.
	 * @param dateCreation The event date of creation.
	 * @param dateCompletion The event date of completion.
	 * @param district The district of the event.
	 * @param description The description of the event.
	 * @param status The status of the event.
	 * @param latitude The latitude of the event.
	 * @param longitude The longitude of the event.
	 * @param city The city of the event.
	 * @param watchIt A boolean, true if the event is managed by an user, false otherwise.
	 * @param usrLat The latitude of the user that created the event.
	 * @param usrLong The longitude of the user that created the event.
	 * @param outcome The event result provided by the resolvent user.
	 * @param resolventUser The userID of the resolvent user.
	 */
	public Event(int eventId, String userID, Date dateCreation, Date dateCompletion, String district, String description, String status,
			double latitude, double longitude, String city, boolean watchIt, double usrLat, double usrLong, String outcome, String resolventUser) {
		super();
		this.eventId = eventId;
		this.userID = userID;
		this.dateCreation = dateCreation;
		this.dateCompletion = dateCompletion;
		this.district = district;
		this.description = description;
		this.status = status;
		this.lat = latitude;
		this.lon = longitude;
		this.city = city;
		this.watchIt = watchIt;
		this.usrLat = usrLat;
		this.usrLong = usrLong;
		this.outcome = outcome;
		this.resolventUser = resolventUser;
		this.setEventName(userID + " on " + dateCreation + " - " + description + " " + district + " " + latitude + " " + longitude);
	}
	
	/**
	 * Returns an integer representing the eventID number.
	 * @return the eventID number.
	 */
	public int getEventId() {
		return eventId;
	}
	/**
	 * Sets the integer eventID.
	 * @param eventId
	 */
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	/**
	 * Returns the userID.
	 * @return the userID.
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
	 * Returns the event data of creation.
	 * @return the date of creation.
	 */
	public Date getDateCreation() {
		return dateCreation;
	}
	/**
	 * Sets the date of creation.
	 * @param dateCreation
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	/**
	 * Returns the Event date of completion.
	 * @return the date of completion.
	 */
	public Date getDateCompletion() {
		return dateCompletion;
	}
	/**
	 * Sets the date of completion.
	 * @param dateCompletion
	 */
	public void setDateCompletion(Date dateCompletion) {
		this.dateCompletion = dateCompletion;
	}
	/**
	 * Returns the district name of the event.
	 * @return the district name of the event.
	 */
	public String getDistrict() {
		return district;
	}
	/**
	 * Sets the district name of the event.
	 * @param district The district name of the ongoing event.
	 */
	public void setDistrict(String district) {
		this.district = district;
	}
	/**
	 * Gives the description of the event.
	 * @return the description of the event.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Returns the event city name.
	 * @return the event city name.
	 */
	public String getCity() {
		return city;
	}
	/**
	 * Sets the city name.
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * Sets the description of the event.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Returns the status of the event.
	 * @return the status of the event.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Sets the status of the event.
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * Returns true if the event is managed by an user.
	 * @return true or false depending by the event status.
	 */
	public synchronized boolean isWatchIt() {
		return watchIt;
	}
	/**
	 * Sets if the event is managed by an user or not.
	 * @param watchIt
	 */
	public synchronized void setWatchIt(boolean watchIt) {
		this.watchIt = watchIt;
	}
	/**
	 * Returns the latitude of the district's user.
	 */
	public double getUsrLat() {
		return usrLat;
	}
	/**
	 * Set the user latitude.
	 */
	public void setUsrLat(double usrLat) {
		this.usrLat = usrLat;
	}
	/**
	 * Returns the longitude of the district's user.
	 */
	public double getUsrLong() {
		return usrLong;
	}
	/**
	 * Sets the user longitude.
	 */
	public void setUsrLong(double usrLong) {
		this.usrLong = usrLong;
	}
	/**
	 * Returns the latitude of the event.
	 * @return  the latitude of the event.
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * Sets the event latitude.
	 * @param latitude
	 */
	public void setLat(double latitude) {
		this.lat = latitude;
	}
	/**
	 * Returns the event longitude.
	 * @return the event longitude.
	 */
	public double getLon() {
		return lon;
	}
	/**
	 * Sets the longitude of the event.
	 * @param longitude
	 */
	public void setLon(double longitude) {
		this.lon = longitude;
	}
	/**
	 * Returns the event outcome description after closing the event.
	 * @return the event outcome description after closing the event.
	 */
	public String getOutcome() {
		return outcome;
	}
	/**
	 * Sets the event outcome description.
	 * @param outcome
	 */
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	/**
	 * Returns the userID of the person in charge of the event.
	 * @return the userID of the person in charge of the event.
	 */
	public String getResolventUser() {
		return resolventUser;
	}
	/**
	 * Sets the userID of the person who take in charge the event.
	 * @param resolventUser
	 */
	public void setResolventUser(String resolventUser) {
		this.resolventUser = resolventUser;
	}
	/**
	 * Returns the description of the event name.
	 * @return the description of the event name.
	 */
	public String getEventName() {
		return eventName;
	}
	/**
	 * Sets the event name description.
	 * @param eventName
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	@Override
	public String toString() {
		
		String latitude = (lat + "").substring(0, 6);
		String longitude = (lon + "").substring(0, 6);
		
		return "<html>" + eventId + " - " + description + " created on " + dateCreation
				+ "<br>" + "by " + userID + " in " + city +  " " + district 
				+ "<br>" + "position " + latitude + " - " + longitude
				+ "<br> managed by " + resolventUser + " on " + dateCompletion
				+ " with outcome " + outcome + " - " + status + "</html>";
	}
}