package com.uninsubria.watchneighbors.model;

/**
 * Customized exception thrown when the coordinates are not found
 * @author alessandro, christian, denise, silvia
 *
 */

public class NullGeopositionException extends NullPointerException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Throws an exception if the geoposition returned is null
	 */
	public NullGeopositionException() {
        super();
    }
	
	/**
	 * Throws an exception if the geoposition returned is null
	 * @param message The message sent when the exception is raised.
	 */
	public NullGeopositionException(String message) {
        super(message + " - http://nominatim.openstreetmap.org/search.php");
    }

}
