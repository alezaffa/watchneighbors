package com.uninsubria.watchneighbors.controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Observable;

import com.uninsubria.watchneighbors.dao.WatchneighborsDAO;
import com.uninsubria.watchneighbors.model.Event;
import com.uninsubria.watchneighbors.model.User;

/**
 * Allows the communication with the client.
 * @author alessandro, christian, denise, silvia
 *
 */
public class Server extends Observable implements IRMIObservableService {

	private Hashtable<String, Event> hashtableEvent = new Hashtable<String, Event>();
	private Hashtable<String, User> hashtableUser = new Hashtable<String, User>();
	private Registry registry;
	private Server obj;
	private IRMIObservableService stub;
	private WatchneighborsDAO qe;
	
	/**
	 * Used to start the server.
	 */
	public void start() {
		try {
			obj = new Server();
			stub = (IRMIObservableService) UnicastRemoteObject.exportObject(obj, 0);
			
			registry = LocateRegistry.createRegistry(1099);
			registry.bind("dbServer", stub);
			
			qe = new WatchneighborsDAO();
			
			System.err.println("Server ready");
		} catch(ExportException ee) {
			try {
				registry = LocateRegistry.getRegistry(1099);
				registry.rebind("dbServer", stub);
				
				System.err.println("Server ready");
			} catch (RemoteException re) {
				System.err.println("Server exception during rebinding: " + re.toString());
				re.printStackTrace();
			}
		} catch(Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	private boolean setNewUser(User u) throws RemoteException {
		User user = hashtableUser.get(u.getUserID());
		if(null == user) {
			System.err.println(u.getUserID() + " does not exist");
			return false;
		}
		return true;
	}
	
	private boolean setNewEvent(Event e) throws RemoteException {
		Event event = hashtableEvent.get(e.getEventName());
		if(null == event) {
			System.err.println(e.getEventName() + " does not exist");
			return false;
		}
		return true;
	}
	
	public boolean regUser(User u) throws RemoteException {
		boolean isInserted = false;
		try {
			qe = new WatchneighborsDAO();
			isInserted = qe.executeInsertUser(u);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		hashtableUser.put(u.getUserID(), u);
		System.out.println("New user " + u.getUserID() + " added");
		System.out.println(u.toString() + "");
		
		return isInserted;
	}
	
	public int regEvent(Event e) throws RemoteException {
		int idEvent = -1;
		if (hashtableUser.containsKey(e.getEventName())) {
			setNewEvent(e);
			System.out.println("Event created by " + e.getUserID() + " updated");
		} else {
			try {
				qe = new WatchneighborsDAO();
				idEvent = qe.executeInsertEvent(e);
				e.setEventId(idEvent);
				
				setChanged();
				notifyObservers(e);
				
				System.out.println("Clients notified by Server");
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			hashtableEvent.put(e.getEventName(), e);
			System.out.println("New event " + e.getEventName() + " added");
			System.out.println(e.toString() + "");
		}
		return idEvent;
	}
	
	public List<Event> getLastTenEventClose() throws RemoteException {
		List<Event> closedEventList = new ArrayList<Event>();
		try {
			qe = new WatchneighborsDAO();
			closedEventList = qe.get10History();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return closedEventList;
	}
	
	public boolean login(User u) throws RemoteException {
		boolean isLoginSuccess = false;
		try {
			qe = new WatchneighborsDAO();
			User user = qe.executeGetUser(u);
			
			if(user.getUserID().equals(u.getUserID()) &
					user.getPassword().equals(u.getPassword())) {
				isLoginSuccess = true;
				hashtableUser.put(u.getUserID(), qe.executeGetUser(u));
				System.out.println(user.getName() + " logged in");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isLoginSuccess;
	}
	
	public boolean deleteUser(User u) throws RemoteException {
		boolean isDeletionSuccess = false;
		try {
			qe = new WatchneighborsDAO();
			if(qe.executeDeleteUser(u) == true) {
				hashtableUser.clear();
				System.out.println(u.getUserID() + " deleted from DB");
				isDeletionSuccess = true;
			} else
				isDeletionSuccess = false;
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return isDeletionSuccess;
	}
	
	public void watchIt(Event e) throws RemoteException {
		try {
			qe = new WatchneighborsDAO();
			qe.watchIt(e);
			
			setChanged();
			notifyObservers(e);
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public boolean isEventWatched(Event e) throws RemoteException {
		boolean isWatched = false;
		try {
			qe = new WatchneighborsDAO();
			isWatched = qe.isEventWatched(e);
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return isWatched;
	}
	
	public synchronized boolean setAndWatch(Event e, User loggedUser) throws RemoteException {

		boolean isEventWatched = isEventWatched(e);

		if(!isEventWatched || (loggedUser.getUserID().equals(e.getResolventUser()) && (null == e.getOutcome()))) {

			e.setWatchIt(true);
			e.setResolventUser(loggedUser.getUserID());
			watchIt(e);
			return true;

		} else {
			return false;
		}
	}
	
	public void setOutcomeAndClose(Event e) throws RemoteException {
		try {
			qe = new WatchneighborsDAO();
			qe.isEventSetAndClosed(e);
			
			setChanged();
			notifyObservers(e);
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public List<Event> getActiveEvent(String district) throws RemoteException {
		List<Event> activeEvent = new ArrayList<Event>();
		try {
			qe = new WatchneighborsDAO();
			activeEvent = qe.executeGetActiveEvent(district);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return activeEvent;
	}
	
	public boolean updateUser(User u) throws RemoteException {
		boolean isUpdateSuccess = false;
		try {
			qe = new WatchneighborsDAO();
			
			if(hashtableUser.containsKey(u.getUserID())) {
				setNewUser(u);
				
				isUpdateSuccess = qe.executeUpdateUser(u);
				
				if(isUpdateSuccess) {
					System.out.println("User " + u.getUserID() + " updated");
					System.out.println(u.toString());
				}
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return isUpdateSuccess;
	}

	public User getUserFromDB(User u) throws RemoteException {
		User user = null;
		try {
			qe = new WatchneighborsDAO();
			user = qe.executeGetUser(u);
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Add a new observer.
	 */
	public void addObserver(IRemoteObserver o) { 
		WrappedObserver mo = new WrappedObserver(o);
		addObserver(mo);
		System.out.println("Added observer:" + mo);
	}
	
}
