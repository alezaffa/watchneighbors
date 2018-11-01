package com.uninsubria.watchneighbors.view;

import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.uninsubria.watchneighbors.model.Event;
import com.uninsubria.watchneighbors.model.User;

/**
 * Provides and manages the graphic interface that can show the passed information.
 * @author alessandro, christian, denise, silvia
 *
 */
public class Table extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private List<?> list;
	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPane;

    /**
     * Create a new graphic interface and shows a table.
     * @param list A generic list
     * @param tableModel A model that shows the type of information in the table
     */
    public Table(List<?> list, DefaultTableModel tableModel) {
    	
    	this.list = list;
    	this.tableModel = tableModel;

    	table = getJTable();

    	scrollPane = new JScrollPane(table);
     
      frame = new JFrame();
      frame.add(scrollPane);
      frame.setBounds(100, 100, 5400, 400);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setVisible(true);
       
    }
    
    private JTable getJTable() {
    	
    	JTable table = new JTable();

    	for(Object o : list) {
    		
    		if(o instanceof Event) {
	    		Event e = (Event) o;
	    		
	    		int eventID = e.getEventId();
				String userID = e.getUserID();
				Date dateCreation = e.getDateCreation();
				Date dateCompletion = e.getDateCompletion();
				String district = e.getDistrict();
				String status = e.getStatus();
				String description = e.getDescription();
				double latitude = e.getLat();
				double longitude = e.getLon();
				String city = e.getCity();
				boolean watchIt = e.isWatchIt();
				double usrLat = e.getUsrLat();
				double usrLong = e.getUsrLong();
				String outcome = e.getOutcome();
				String resolventUser = e.getResolventUser();
	    		
	    		tableModel.addRow(new Object[]{
	    				eventID,
	    				userID,
	    				dateCreation,
	    				dateCompletion,
	    				district,
	    				status,
	    				description,
	    				latitude,
	    				longitude,
	    				city,
	    				watchIt,
	    				usrLat,
	    				usrLong,
	    				outcome,
	    				resolventUser});
	    	}
    		
    		if(o instanceof User) {
    			User u = (User) o;
				
				String city = u.getCity();
				String district = u.getDistrict();
				String userID = u.getUserID();
				String name = u.getName();
				String surname = u.getSurname();
	    		
	    		tableModel.addRow(new Object[]{
	    				city,
	    				district,
	    				userID,
	    				name,
	    				surname});
    		}
    	}
    	
    	table.setModel(tableModel);
    	
    	return table;
    	
    }
}