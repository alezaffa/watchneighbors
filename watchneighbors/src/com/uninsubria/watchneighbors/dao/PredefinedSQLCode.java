package com.uninsubria.watchneighbors.dao;

/**
 * Provides the statements used to query the database.
 * @author alessandro, christian, denise, silvia
 *
 */
public class PredefinedSQLCode {
	
	 public static final String query_create_person_table=
		"CREATE TABLE IF NOT EXISTS Person ( " +
		"UserID Varchar(30) PRIMARY KEY," +
		"Password Varchar(30) NOT NULL," +
		"Name Varchar(30) NOT NULL," +
		"Surname Varchar(30) NOT NULL," +
		"Email Varchar(255) NOT NULL," +
		"City Varchar(20) NOT NULL," +
		"Address Varchar(30) NOT NULL," +
		"Latitude numeric NOT NULL," +
		"Longitude numeric NOT NULL," +
		"District Varchar(20) NOT NULL)";

	 public static final String query_create_event_table=
		"CREATE TABLE IF NOT EXISTS Event ( " +
		"EventID BIGSERIAL NOT NULL," +
		"EventDescription Varchar(30) NOT NULL," +
		"DistrictName Varchar(20) NOT NULL, " +
		"CityName Varchar(20) NOT NULL," +
		"WatchIT Boolean NOT NULL," +
		"EventLat numeric NOT NULL," +
		"EventLng numeric NOT NULL," +
		"TimeStampOpen TimeStamp NOT NULL," +
		"TimeStampClose TimeStamp," +
		"UserID Varchar(30) NOT NULL REFERENCES Person(UserId) ON DELETE CASCADE," +
		"UserLat numeric NOT NULL," +
		"UserLng numeric NOT NULL," +
		"Status Varchar(30) NOT NULL," +
		"Outcome Varchar(40)," +
		"Resolventuser Varchar(30) REFERENCES Person(UserID) ON DELETE CASCADE," +
		"PRIMARY KEY (EventID))";
	
	/**
	 * Insert a new user into table Person 
	 */
	public static final String query_insert_user=
			"INSERT INTO Person VALUES(?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * Updates the user.
	 */
	public static final String query_update_user=
			"UPDATE Person SET Password=? ,Name=? ,Surname=? ,"
			+ "Email=? ,City=? ,Address=? ,Latitude=? ,Longitude=? ,District=? WHERE UserID=?";
	
	/**
	 * Insert a new event.
	 */
	public static final String query_insert_event=
			"INSERT INTO Event(EventDescription, DistrictName, "
			+ "CityName, WatchIT, EventLat, EventLng, "
			+ "TimeStampOpen, TimeStampClose, UserID, userLat, userLng, status) VALUES(?,?,?,?,?,?,?,?,?,?,?,?) "
			+ "returning eventid";
	
	/**
	 * Update the event.
	 */
	public static final String query_update_event=
			"UPDATE Event SET ResolventUser = ? WHERE EventID = ?";
	
	/**
	 * Used to modify the wathcIt field.
	 */
	public static final String query_watch_event=
			"UPDATE Event SET WatchIt = ?, resolventUser = ? WHERE eventid = ?";
	
	/**
	 * Used to check if an event is watched or not.
	 */
	public static final String query_is_event_watched=
			"SELECT WatchIt FROM Event WHERE eventID = ?";
	
	/**
	 * Used to set to close an event.
	 */
	public static final String query_close_event=
			"UPDATE Event SET Outcome = ?, timestampclose = ?, status = ? WHERE eventid = ?";
	
	/**
	 * Used to create a custom view.
	 */
	public static final String query_create_view=
			"CREATE VIEW reg_users AS SELECT City, District, UserID,"
			+ "Name, Surname FROM Person";
	
	/**
	 * Used to select all the registered users.
	 */
	public static final String query_reg_users=
			"SELECT * FROM reg_users";
	
	/**
	 * Returns the last ten resolved events.
	 */
	public static final String query_storico_10=
			"SELECT * FROM Event WHERE status='CLOSED' ORDER BY TimeStampClose DESC LIMIT 10";
	
	/**
	 * Returns the user's credentials.
	 */
	public static final String query_get_credentials=
			"SELECT * FROM Person WHERE UserID=? AND Password=?";
	
	/**
	 * Deletes the logged user.
	 */
	public static final String query_delete_row=
			"DELETE FROM Person WHERE UserID=?";
	
	/**
	 * Gets all the active events for the selected district.
	 */
	public static final String query_get_active_event=
			"SELECT * FROM Event WHERE Status=? AND DistrictName=?";

}