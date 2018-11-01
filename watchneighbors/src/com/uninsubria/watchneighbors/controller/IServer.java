package com.uninsubria.watchneighbors.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.uninsubria.watchneighbors.model.Event;
import com.uninsubria.watchneighbors.model.User;

/**
 * The interface implemented by Client and Server.
 * Provides the methods that will be implemented by both Client and Server in different ways.
 * @author alessandro, christian, denise, silvia
 *
 */
public interface IServer extends Remote {
	
	/**
	 * Allows users to register themselves.
	 * @param u An User object
	 * @return boolean: true if the user in already signed in, false otherwise
	 * @throws RemoteException
	 */
	public boolean regUser(User u) throws RemoteException;
	
	/**
	 * Allows users to add a new event.
	 * @param e An event
	 * @return int The ID of the added event
	 * @throws RemoteException
	 */
	public int regEvent(Event e) throws RemoteException;
	
	/**
	 * Returns the last ten closed event.
	 * @return List Contains the last ten closed event.
	 * @throws RemoteException
	 */
	public List<Event> getLastTenEventClose() throws RemoteException;
	
	/**
	 * Allows users to login.
	 * @param u An User object
	 * @return boolean: true if the user already exists, false otherwise
	 * @throws RemoteException
	 */
	public boolean login(User u) throws RemoteException;
	
	/**
	 * Gets the user from the database.
	 * @param u An User object
	 * @return User Represents the registered user
	 * @throws RemoteException
	 */
	public User getUserFromDB(User u) throws RemoteException;
	
	/**
	 * Deletes the registered user from the database.
	 * @param u An User object
	 * @return boolean: true if the deletion is successful
	 * @throws RemoteException
	 */
	public boolean deleteUser(User u) throws RemoteException;
	
	/**
	 * Checks if the event is already took in charge or not.
	 * @param e An event
	 * @return boolean: true if the event is already took in charge, false otherwise.
	 * @throws RemoteException
	 */
	public boolean isEventWatched(Event e) throws RemoteException;
	
	/**
	 * Sets took in charge.
	 * @param e An event
	 * @throws RemoteException
	 */
	public void watchIt(Event e) throws RemoteException;
	
	/**
	 * Sets the outcome and closes the event.
	 * @param e An event
	 * @throws RemoteException
	 */
	public void setOutcomeAndClose(Event e) throws RemoteException;
	
	/**
	 * Returns a list that contains the active events.
	 * @param district A string that represents a district.
	 * @return List
	 * @throws RemoteException
	 */
	public List<Event> getActiveEvent(String district) throws RemoteException;
	
	/**
	 * Updates the user information.
	 * @param u An User object.
	 * @return boolean: true if the update is success, false otherwise.
	 * @throws RemoteException
	 */
	public boolean updateUser(User u) throws RemoteException;
	
	/**
	 * Used to take in charge an event a set to wathced state.
	 * Used in the watchIt method.
	 * @param e The event 
	 * @param loggedUser
	 * @return boolean: true if set and watched, false otherwise.
	 * @throws RemoteException
	 */
	public boolean setAndWatch(Event e, User loggedUser) throws RemoteException;

}