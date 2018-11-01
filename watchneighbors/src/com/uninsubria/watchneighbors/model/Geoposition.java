package com.uninsubria.watchneighbors.model;

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

/**
 * Represents the coordinates of the event.
 * @author alessandro, christian, denise, silvia
 *
 */
public class Geoposition implements ICoordinate {
	
	public static final String COMO = "Como";
	public static final String LUGANO = "Lugano";
	public static final String VARESE = "Varese";
	public static String[] cities = {COMO, LUGANO, VARESE};
	
	private double latitude, longitude;
	private MapMarkerDot marker;
	
	/**
	 * Create a Geoposition object with latitude, longitude and MapMarkerDot.
	 * @param latitude The latitude of the marker.
	 * @param longitude The longitude of the marker.
	 * @param marker It is used to highlight an event on a map.
	 * @throws NullGeopositionException if the latitude and longitude are null.
	 */
	public Geoposition(Double latitude, Double longitude, MapMarkerDot marker) throws NullGeopositionException {
		if(null == latitude || null == longitude) {
        	throw new NullGeopositionException("This address does not exists");
        }
		this.latitude = latitude;
		this.longitude = longitude;
		this.marker = marker;
	}
	/**
	 * Create a Geoposition object with latitude, longitude.
	 * @param latitude The latitude of the marker.
	 * @param longitude The longitude of the marker.
	 * @throws NullGeopositionException if the latitude and longitude are null.
	 */
	public Geoposition(Double latitude, Double longitude) throws NullGeopositionException {
		if(null == latitude || null == longitude) {
        	throw new NullGeopositionException("This address does not exists");
        }
		this.latitude = latitude;
		this.longitude = longitude;
	}
	/**
	 * Returns the MapMarkerDot.
	 * @return MapMarkerDot The marker representing latitude and longitude.
	 */
	public MapMarkerDot getMarker() {
		return marker;
	}
	/**
	 * Sets the MapMarkerDot representing the latitude and longitude of the event.
	 * @param marker It is used to highlight an event on a map.
	 */
	public void setMarker(MapMarkerDot marker) {
		this.marker = marker;
	}
	/**
	 * Returns the latitude.
	 * @return the latitude.
	 */
	public double getLat() {
		return latitude;
	}
	/**
	 * Returns the longitude.
	 * @return the longitude.
	 */
	public double getLon() {
		return longitude;
	}
	/**
	 * Set latitude.
	 */
	public void setLat(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * Set longitude.
	 */
	public void setLon(double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return "Geoposition [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
}
