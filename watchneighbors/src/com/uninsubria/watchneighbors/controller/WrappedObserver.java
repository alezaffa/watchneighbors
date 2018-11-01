package com.uninsubria.watchneighbors.controller;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

import com.uninsubria.watchneighbors.model.Event;

/**
 * Wraps an observer on server side.
 * @author alessandro, christian, denise, silvia
 *
 */
public class WrappedObserver implements Observer, Serializable {

	private static final long serialVersionUID = 1L;
	private IRemoteObserver remoteClient = null;
	
	/**
	 * Creates a wrapped observer that contains an observer on server side.
	 * @param remoteClient It is the remote observer.
	 */
	public WrappedObserver(IRemoteObserver remoteClient) {
		super();
		this.remoteClient = remoteClient;
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			remoteClient.remoteUpdate(o.toString(), (Event)arg);
		} catch(RemoteException e) {
			System.out.println("Remote exception removing observer: " + this);
			o.deleteObserver(this);
		}
	}

}