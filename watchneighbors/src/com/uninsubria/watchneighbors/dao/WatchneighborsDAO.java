package com.uninsubria.watchneighbors.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Properties;

import org.postgresql.util.PSQLException;

import com.uninsubria.watchneighbors.model.Event;
import com.uninsubria.watchneighbors.model.User;

/**
 * Manages and execute the queries
 * @author alessandro, christian, denise, silvia
 */
public class WatchneighborsDAO {
	
	protected static String url = "jdbc:postgresql://localhost/watchneighborsdb";
	protected static String usr = "postgres";
	protected static String pwd = "postgres";
	private static Connection con;
	
	/**
	 * Used to throw an exception and print the error message
	 * @param ex The SQLException
	 */
	public static void printSQLException(SQLException ex) {
		
		System.err.println("SQLState:" + ex.getSQLState());
		System.err.println("Error code:" + ex.getErrorCode());
		System.err.println("Messages:" + ex.getMessage());
	}
	
	private Connection openConnection(String url, String usr, String pwd) throws SQLException {
		
		Properties props = new Properties();
		props.setProperty("user", usr);
		props.setProperty("password", pwd);
		
		Connection conn = DriverManager.getConnection(url, props);
		
		return conn;
		
	}
	
	/**
	 * Create and open a new connection with the database
	 * @throws SQLException
	 */
	public WatchneighborsDAO() throws SQLException {
		WatchneighborsDAO.con = openConnection(url, usr, pwd);
	}
	
	/**
	 * Execute a generic query.
	 * @param sqlCode the string representing the query to be executed.
	 * @throws SQLException the sql exception
	 */
	public void executeDDquery(String sqlCode) throws SQLException {
		
		Statement stmt = null;
		
		try {
		
			stmt = con.createStatement();	
			stmt.executeUpdate(sqlCode);
		
		} catch(SQLException e) {			
			
			e.printStackTrace();
			printSQLException(e);
			
		} finally {
			
			if(stmt!=null) {
				stmt.close();
			}
		}
	}
	
	/**
	 * Returns the connection
	 * @return con
	 */
	public Connection getCon() {
		return con;
	}
	
	/**
	 * Inserts a new user
	 * @param u the user to insert
	 * @return boolean true if registered with success, false otherwise
	 * @throws SQLException the SQLException
	 */
	public boolean executeInsertUser(User u) throws SQLException {
	
		boolean registered = false;
		PreparedStatement stmt = null;

		try {
			
			stmt = con.prepareStatement(PredefinedSQLCode.query_insert_user);
			
			stmt.setString(1, u.getUserID());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getName());
			stmt.setString(4, u.getSurname());
			stmt.setString(5, u.getEmail());
			stmt.setString(6, u.getCity());
			stmt.setString(7, u.getAddress());
			stmt.setDouble(8, u.getUsrLat());
			stmt.setDouble(9, u.getUsrLng());
			stmt.setString(10, u.getDistrict());

			stmt.executeUpdate();
			registered = true;
			
		} catch(SQLException e) {
			if(e instanceof PSQLException) {
				e.printStackTrace();
				registered = false;
				return registered;
			}
			e.printStackTrace();
			printSQLException(e);
		} finally {
			if(stmt!=null) {
				stmt.close(); 
			}
			if(null!=con) {
				con.close();
			}
		}
		return registered;
	}
	
	/**
	 * Inserts a new event
	 * @param e
	 * @return int
	 * @throws SQLException
	 */
	public int executeInsertEvent(Event e) throws SQLException {
		
		int idEvent = -1;
		PreparedStatement stmt = null;
		Timestamp sqlDateCreation = new Timestamp(e.getDateCreation().getTime());
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(PredefinedSQLCode.query_insert_event);
			
			stmt.setString(1, e.getDescription());
			stmt.setString(2, e.getDistrict());
			stmt.setString(3, e.getCity());
			stmt.setBoolean(4, e.isWatchIt());
			stmt.setDouble(5, e.getLat());
			stmt.setDouble(6, e.getLon());
			stmt.setTimestamp(7, sqlDateCreation);
			stmt.setTimestamp(8, null);
			stmt.setString(9, e.getUserID());
			stmt.setDouble(10, e.getUsrLat());
			stmt.setDouble(11, e.getUsrLong());
			stmt.setString(12, e.getStatus());
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				idEvent = rs.getInt(1);
			}
			
		} catch(SQLException sqle) {
			if(sqle instanceof PSQLException) {
				sqle.printStackTrace();
				idEvent = -1;
				return idEvent;
			}
			sqle.printStackTrace();
			printSQLException(sqle);
		} finally {
			if(null != rs) {
				rs.close();
			}
			if(null != stmt) {
				stmt.close(); 
			}
			if(null != con) {
				con.close();
			}
		}
		return idEvent;
	}
	
	/**
	 * Returns a List containing the registered users
	 * @return List
	 * @throws SQLException
	 */
	public List<User> getRegUsers() throws SQLException {
		
		PreparedStatement stmt = null;
		User user = null;
		List<User> regUsersList = new ArrayList<>();
		ResultSet rs = null;
		
		try {
		
			stmt = con.prepareStatement(PredefinedSQLCode.query_reg_users);
			rs = stmt.executeQuery();
		
			while(rs.next()) {
				
				user = new User(rs.getString(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5));
				
				regUsersList.add(user);
			}
		
		} catch(SQLException e) {		
			
			e.printStackTrace();
			printSQLException(e);
			
		} finally {
			if (null != rs) {
				rs.close();
			}
			if(stmt!=null) {
				stmt.close(); 
			}
			if(null!=con) {
				con.close();
			}
		}
		return regUsersList;
		
	}
	
	/**
	 * Get the last ten resolved events
	 * @return List
	 * @throws SQLException
	 */
	public List<Event> get10History() throws SQLException {
		
		PreparedStatement stmt = null;
		List<Event> eventList = new ArrayList<>();
		ResultSet rs = null;
		
		try {
		
			stmt = con.prepareStatement(PredefinedSQLCode.query_storico_10);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				int eventID = rs.getInt(1); 
				String description = rs.getString(2);
				String district = rs.getString(3);
				String city = rs.getString(4);
				boolean watchIt = rs.getBoolean(5);
				double latitude = rs.getDouble(6);
				double longitude = rs.getDouble(7);
				Date dateCreation = rs.getTimestamp(8);
				Date dateCompletion = rs.getTimestamp(9);
				String userID = rs.getString(10);
				double usrLat = rs.getDouble(11);
				double usrLong = rs.getDouble(12);
				String status = rs.getString(13);
				String outcome = rs.getString(14);
				String resolventUser = rs.getString(15);
							
				Event event = new Event(eventID, userID, dateCreation, dateCompletion, district, description, status, latitude, 
						longitude, city, watchIt, usrLat, usrLong, outcome, resolventUser);
				
				eventList.add(event);
				
			}
		} catch(SQLException e) {		
			
			e.printStackTrace();
			printSQLException(e);
			
		} finally {
			if (null != rs) {
				rs.close();
			}
			if(stmt!=null) {
				stmt.close(); 
			}
			if(null!=con) {
				con.close();
			}
		}
		return eventList;
		
	}
	
	/**
	 * Returns all the user's informations
	 * @param u
	 * @return User
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public User executeGetUser(User u) throws SQLException, NullPointerException {
		
		User user = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
		
			stmt = con.prepareStatement(PredefinedSQLCode.query_get_credentials);
			stmt.setString(1, u.getUserID());
			stmt.setString(2, u.getPassword());

			rs = stmt.executeQuery();

			while(rs.next()) {
				user = new User(rs.getString(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6), 
						rs.getString(7), 
						rs.getDouble(8), 
						rs.getDouble(9), 
						rs.getString(10));
				
				return user;
			}
				
		} catch(SQLException e) {
			
			e.printStackTrace();
			printSQLException(e);
			
		} finally {
         if(null != rs) {
         	rs.close();
         }
			if(stmt!=null) {
				stmt.close(); 
			}
			if(null!=con) {
				con.close();
			}
		}
		return user;
		
	}
	
	/**
	 * Deletes the user and returns a boolean: true if success, false otherwise
	 * @param u
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean executeDeleteUser(User u) throws SQLException {
		boolean isDeletionSuccess = false;
		PreparedStatement stmt = null;
		
		try {
		
			stmt = con.prepareStatement(PredefinedSQLCode.query_delete_row);
			stmt.setString(1, u.getUserID());

			isDeletionSuccess = true;
			stmt.executeQuery();
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			printSQLException(sqle);
		} finally {
			if(stmt!=null) {
				stmt.close(); 
			}
			if(null!=con) {
				con.close();
			}
		}
		return isDeletionSuccess;
	}
	
	/**
	 * Returns true if the event is in charge, false otherwise
	 * @param e
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isEventWatched(Event e) throws SQLException {
		boolean isEventWatched = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(PredefinedSQLCode.query_is_event_watched);
			
			stmt.setInt(1, e.getEventId());
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				isEventWatched = rs.getBoolean(1);
			}
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			printSQLException(sqle);
		} finally {
			if(null != rs) {
				rs.close();
			}
			if(stmt!=null) {
				stmt.close(); 
			}
			if(null!=con) {
				con.close();
			}
		}
		
		return isEventWatched;
	}
	
	/**
	 * Sets an event: it can be watched or not
	 * @param e
	 * @throws SQLException
	 */
	public void watchIt(Event e) throws SQLException {
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(PredefinedSQLCode.query_watch_event);
			
			stmt.setBoolean(1, e.isWatchIt());
			stmt.setString(2, e.getResolventUser());
			stmt.setInt(3, e.getEventId());
			
			stmt.executeQuery();
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			printSQLException(sqle);
		} finally {
			if(stmt!=null) {
				stmt.close(); 
			}
			if(null!=con) {
				con.close();
			}
		}
	}
	
	/**
	 * Checks if the event is already taken and closed
	 * @param e
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isEventSetAndClosed(Event e) throws SQLException {
		
		boolean isEventSetAndClosed = false;
		PreparedStatement stmt = null;
		Timestamp sqlDateCompletion = new Timestamp(e.getDateCompletion().getTime());
		
		try {
			stmt = con.prepareStatement(PredefinedSQLCode.query_close_event);
			
			stmt.setString(1, e.getOutcome());
			stmt.setTimestamp(2, sqlDateCompletion);
			stmt.setString(3, e.getStatus());
			stmt.setInt(4, e.getEventId());
			
			isEventSetAndClosed = true;
			stmt.executeQuery();
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			printSQLException(sqle);
		} finally {
			if(stmt!=null) {
				stmt.close(); 
			}
			if(null!=con) {
				con.close();
			}
		}
		
		return isEventSetAndClosed;
	}
	
	/**
	 * Gets an arrayList containing the active events
	 * @param userDistrict
	 * @return arrayList
	 * @throws SQLException
	 */
	public List<Event> executeGetActiveEvent(String userDistrict) throws SQLException {
		
		PreparedStatement stmt = null;
		List<Event> activeEvents = new ArrayList<>();
		ResultSet rs = null;
		
		try {
			
			stmt = con.prepareStatement(PredefinedSQLCode.query_get_active_event);
			stmt.setString(1, "OPEN");
			stmt.setString(2, userDistrict);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				int eventID = rs.getInt(1); 
				String userID = rs.getString(10);
				Date dateCreation = rs.getTimestamp(8);
				Date dateCompletion = rs.getTimestamp(9);
				String district = rs.getString(3);
				String status = rs.getString(13);
				String description = rs.getString(2);
				double latitude = rs.getDouble(6);
				double longitude = rs.getDouble(7);
				String city = rs.getString(4);
				boolean watchIt = rs.getBoolean(5);
				double usrLat = rs.getDouble(11);
				double usrLong = rs.getDouble(12);
				String outcome = rs.getString(14);
				String resolventUser = rs.getString(15);
							
				Event event = new Event(eventID, userID, dateCreation, dateCompletion, district, description, status, latitude, 
						longitude, city, watchIt, usrLat, usrLong, outcome, resolventUser);
				
				activeEvents.add(event);
			}
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			printSQLException(sqle);
		} finally {
			if(null!=rs) {
				rs.close();
			}
			if(stmt!=null) {
				stmt.close(); 
			}
			if(null!=con) {
				con.close();
			}
		}
		return activeEvents;
	}

	/**
	 * Updates the user
	 * @param u
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean executeUpdateUser(User u) throws SQLException {
		
		boolean isUpdated = false;
		PreparedStatement stmt = null;

		try {
			
			stmt = con.prepareStatement(PredefinedSQLCode.query_update_user);
			
			stmt.setString(1, u.getPassword());
			stmt.setString(2, u.getName());
			stmt.setString(3, u.getSurname());
			stmt.setString(4, u.getEmail());
			stmt.setString(5, u.getCity());
			stmt.setString(6, u.getAddress());
			stmt.setDouble(7, u.getUsrLat());
			stmt.setDouble(8, u.getUsrLng());
			stmt.setString(9, u.getDistrict());
			stmt.setString(10, u.getUserID());
			
			isUpdated = true;
			stmt.executeUpdate();
			
		} catch(SQLException e) {
			if(e instanceof PSQLException) {
				e.printStackTrace();
				printSQLException(e);
				isUpdated = false;
				return isUpdated;
			}
			e.printStackTrace();
			printSQLException(e);
		} finally {
			if(stmt!=null) {
				stmt.close(); 
			}
			if(null!=con) {
				con.close();
			}
		}
		return isUpdated;
	}
}