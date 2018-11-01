package com.uninsubria.watchneighbors.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.uninsubria.watchneighbors.model.Event;

/**
 * The interface that the RMI client - the observer entity - must implement.
 * @author alessandro, christian, denise, silvia
 *
 */
public interface IRemoteObserver extends Remote {
	
	/**
	 * Allows the clients to be notified.
	 * @param observable The observable entity that sends the message.
	 * @param event An event that is just created or modified.
	 * @throws RemoteException
	 */
	void remoteUpdate(Object observable, Event event) throws RemoteException;

}