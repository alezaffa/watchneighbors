package com.uninsubria.watchneighbors.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import com.uninsubria.watchneighbors.model.Geoposition;
import com.uninsubria.watchneighbors.model.NullGeopositionException;

/**
 * Used to convert the address into coordinates.
 * @author alessandro, christian, denise, silvia
 *
 */
public class OpenStreetMapUtils {

    private static OpenStreetMapUtils instance = null;
    private JSONParser jsonParser;

    /**
     * Builds and sets a new JSONParser
     * @throws NullGeopositionException The exception thrown when the coordinates are null.
     */
    public OpenStreetMapUtils() throws NullGeopositionException {
        this.setJsonParser(new JSONParser());
    }

    /**
     * Returns a single instance of the class
     * @return instance of the class
     */
    public static OpenStreetMapUtils getInstance() {
        if (null == instance) {
            instance = new OpenStreetMapUtils();
        }
        return instance;
    }

    private String getRequest(String url) throws Exception {

        final URL obj = new URL(url);
        final HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        if (con.getResponseCode() != 200) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
     
    /**
     * Returns a map which contains the suburbs.
     * @param address The city.
     * @return Map A map with a string key and a string value containing the districts.
     */
    public Map<String, String> getSuburbs(String address) throws NullGeopositionException {
    	 Map<String, String> res;
         StringBuffer query;
         String[] split = address.split(" ");
         String queryResult = null;

         query = new StringBuffer();
         res = new HashMap<String, String>();

         query.append("http://nominatim.openstreetmap.org/search?q=");

         if (split.length == 0) {
             return null;
         }

         for (int i = 0; i < split.length; i++) {
             query.append(split[i]);
             if (i < (split.length - 1)) {
                 query.append("+");
             }
         }
         query.append("&format=json&addressdetails=1");

         try {
        	queryResult = getRequest(query.toString());
         } catch (Exception e) {
             System.err.println("Error when trying to get data with the following query " + query);
         }

         if (queryResult == null) {
             return null;
         }

         Object obj = JSONValue.parse(queryResult);

         if (obj instanceof JSONArray) {
             JSONArray array = (JSONArray) obj;
             
             if(array.size() > 0) {
             
            	 for (int i=0; i<array.size(); i++) {
            		 JSONObject jsonObject = (JSONObject) array.get(i);
            		 String s = (String) jsonObject.get("display_name");
            		 
            		 String[] suburb = s.split(",");         		 
            		 res.put("suburb_" + i, suburb[0]);
            	 }
             }
         }        
         return res;
    }

    /**
     * Returns the coordinates of the provided address.
     * @param address The address.
     * @return A map which contains the coordinates.
     */
    public Map<String, Double> getCoordinates(String address) throws NullGeopositionException {
        Map<String, Double> res;
        StringBuffer query;
        String[] split = address.split(" ");
        String queryResult = null;

        query = new StringBuffer();
        res = new HashMap<String, Double>();

        query.append("http://nominatim.openstreetmap.org/search?q=");

        if (split.length == 0) {
            return null;
        }

        for (int i = 0; i < split.length; i++) {
            query.append(split[i]);
            if (i < (split.length - 1)) {
                query.append("+");
            }
        }
        query.append("&format=json&addressdetails=1");

        try {
            queryResult = getRequest(query.toString());
        } catch (Exception e) {
            System.err.println("Error when trying to get data with the following query " + query);
        }

        if (queryResult == null) {
            return null;
        }

        Object obj = JSONValue.parse(queryResult);

        if (obj instanceof JSONArray) {
            JSONArray array = (JSONArray) obj;
            if (array.size() > 0) {
                JSONObject jsonObject = (JSONObject) array.get(0);

                String lat = (String) jsonObject.get("lat");
                String lon = (String) jsonObject.get("lon");
                               
                res.put("lon", Double.parseDouble(lon));
                res.put("lat", Double.parseDouble(lat));

            }
        }
        return res;
    }

    /**
     * After selecting city contact a web server in order to get its suburbs.
     * @param city the comboBox of the selected city.
     * @param district the target comboBox to be populated.
     * @return ActionListener The ActionListener object.
     */
    public static ActionListener onSelectionSetSuburbsFromJSON(final JComboBox<String> city, final JComboBox<String> district) {
		
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Map<String, String> namePlace;
					if(city.getSelectedItem().equals(Geoposition.COMO)) {
						namePlace = OpenStreetMapUtils.getInstance().getSuburbs("como italy suburbs");
						district.removeAllItems();
					    for(int i=0; i<namePlace.size(); i++) {
					    	district.addItem(namePlace.get("suburb_" + i));
					    }
					}
					if(city.getSelectedItem().equals(Geoposition.LUGANO)) {
						namePlace = OpenStreetMapUtils.getInstance().getSuburbs("lugano suburbs");
						district.removeAllItems();
						for(int i=0; i<namePlace.size(); i++) {
					    	district.addItem(namePlace.get("suburb_" + i));
					    }
					}
					if(city.getSelectedItem().equals(Geoposition.VARESE)) {
						namePlace = OpenStreetMapUtils.getInstance().getSuburbs("varese suburbs");
						district.removeAllItems();
						for(int i=0; i<namePlace.size(); i++) {
					    	district.addItem(namePlace.get("suburb_" + i));
					    }
					}
				} catch(NullPointerException npe) {
					JOptionPane.showMessageDialog(null, "Cannot retrieve data from database", "Warning", JOptionPane.ERROR_MESSAGE);					
				}
			}
		};
	}
    
    /**
     * Gets the JSON parser object
     * @return jsonParser object
     */
	public JSONParser getJsonParser() {
		return jsonParser;
	}

	/**
	 * Sets the JSON parser object
	 * @param jsonParser The JSONParser object.
	 */
	public void setJsonParser(JSONParser jsonParser) {
		this.jsonParser = jsonParser;
	}
}