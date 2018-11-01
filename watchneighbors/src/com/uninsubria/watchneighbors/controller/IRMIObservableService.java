package com.uninsubria.watchneighbors.controller;

import java.rmi.RemoteException;

/**
 * The interface that the RMI server - the observable entity - must implement.
 * @author alessandro, christian, denise, silvia
 *
 */
public interface IRMIObservableService extends IServer {

	/**
	 * Adds an observer to a set of observers.
	 * @param o The observer to be added.
	 * @throws RemoteException
	 */
	void addObserver(IRemoteObserver o) throws RemoteException;
	
}