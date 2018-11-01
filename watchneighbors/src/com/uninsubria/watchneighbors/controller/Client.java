package com.uninsubria.watchneighbors.controller;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.uninsubria.watchneighbors.model.Event;
import com.uninsubria.watchneighbors.model.User;

/**
 * Allows the communication with the server.
 * @author alessandro, christian, denise, silvia
 *
 */
public class Client extends UnicastRemoteObject implements IServer, IRemoteObserver {
	
	private static final long serialVersionUID = 1L;
	private IServer stub;
	private IRMIObservableService remoteService;
	private transient Watchneighbors watchneighbors;
	
	/**
	 * Creates a Client object and adds it into a list of observers.
	 * @param watchneighbors
	 * @throws AccessException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public Client(Watchneighbors watchneighbors) throws AccessException, RemoteException, NotBoundException {
		
		this.watchneighbors = watchneighbors;
		String ipAddress = Watchneighbors.getIpAddress();
		
		Registry registry = LocateRegistry.getRegistry(ipAddress);
		stub = (IServer) registry.lookup("dbServer");
		remoteService = (IRMIObservableService) registry.lookup("dbServer");
		
		System.err.println("Active Client on " + ipAddress);
		
		remoteService.addObserver(this);
	}
	@Override
	public boolean login(User u) throws RemoteException {
		return stub.login(u);
	}
	@Override
	public boolean regUser(User u) throws RemoteException {
		return stub.regUser(u);
	}
	@Override
	public int regEvent(Event e) throws RemoteException {
		return stub.regEvent(e);
	}
	@Override
	public boolean deleteUser(User u) throws RemoteException {
		return stub.deleteUser(u);
	}
	@Override
	public void watchIt(Event e) throws RemoteException {
		stub.watchIt(e);
	}
	@Override
	public boolean isEventWatched(Event e) throws RemoteException {
		return stub.isEventWatched(e);
	}
	@Override
	public void setOutcomeAndClose(Event e) throws RemoteException {
		stub.setOutcomeAndClose(e);
	}
	@Override
	public List<Event> getActiveEvent(String district) throws RemoteException {
		return stub.getActiveEvent(district);
	}
	@Override
	public List<Event> getLastTenEventClose() throws RemoteException {
		return null;
//		return stub.getActiveEvent(district);
	}
	@Override
	public boolean updateUser(User u) throws RemoteException {
		return stub.updateUser(u);
	}
	@Override
	public User getUserFromDB(User u) throws RemoteException {
		return stub.getUserFromDB(u);
	}
	@Override
	public void remoteUpdate(Object observable, Event event) throws RemoteException {
		watchneighbors.update(event);
	}
	@Override
	public boolean setAndWatch(Event e, User loggedUser) throws RemoteException {
		return stub.setAndWatch(e, loggedUser);
	}
}