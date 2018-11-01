package com.uninsubria.watchneighbors.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

import com.uninsubria.watchneighbors.controller.Watchneighbors;
import com.uninsubria.watchneighbors.model.Event;

import javax.swing.ListSelectionModel;

/**
 * Creates a panel in which the users can manage an event and edit their data.
 * @author alessandro, christian, denise, silvia
 *
 */
public class PanelDashboard {

	private JPanel panelDashboard;
	private JButton btnEdit;
	private JLabel lblLatitude;
	private JLabel lblLongitude;
	private JLabel lblEventLatitude;
	private JLabel lblEventLongitude;
	private JButton btnWatchIt;
	private JPanel panelNewEvent;
	private JButton btnNewEvent;
	private JLabel lblEventUserID;
	private JLabel lblEventDescription;
	private JLabel lblEventTimeStamp;
	private JLabel lblEventLatLng;
	private JLabel lblUserLatLng;
	private JLabel lblUsertLat;
	private JLabel lblUserLng;
	private JButton btnDashboardLogin;
	private JButton btnDashboardLogout;
	private JScrollPane scrollPane;
	private JList<Event> jList;
	private JComboBox<String> comboBoxEventDescription;
	private JButton btnCloseEvent;
	private JLabel lblUserCityDistrict;
	protected static final String[] EVENT_TYPES = new String[] {"Robbery in progress", "Suspect person", "Gas leak", "Fire"};
	public static final String NOT_SELECTABLE_OPTION = "Select";
	private JLabel lblCoordinates;
	
	/**
	 * Create a frame with fields in which the users can manage an event.
	 */
	public PanelDashboard() {
		
		panelDashboard = new JPanel();
		panelDashboard.setBounds(593, 0, 358, 626);
		panelDashboard.setBackground(Watchneighbors.COLOR);
		panelDashboard.setLayout(null);
		
		btnEdit = new JButton("Edit");
		btnEdit.setBounds(206, 20, 134, 29);
		panelDashboard.add(btnEdit);
		
		jList = new JList<Event>();
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList.setBounds(6, 142, 346, 265);
		jList.setFixedCellHeight(135);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 142, 346, 265);
		scrollPane.setViewportView(jList);
		panelDashboard.add(scrollPane);
		
		lblLatitude = new JLabel("Latitude:");
		lblLatitude.setBounds(6, 80, 165, 16);
		panelDashboard.add(lblLatitude);
		
		lblLongitude = new JLabel("Longitude:");
		lblLongitude.setBounds(6, 108, 165, 16);
		panelDashboard.add(lblLongitude);
		
		lblEventLatitude = new JLabel("-");
		lblEventLatitude.setEnabled(false);
		lblEventLatitude.setBounds(279, 80, 61, 16);
		panelDashboard.add(lblEventLatitude);
		
		lblEventLongitude = new JLabel("-");
		lblEventLongitude.setEnabled(false);
		lblEventLongitude.setBounds(279, 108, 61, 16);
		panelDashboard.add(lblEventLongitude);
		
		btnWatchIt = new JButton("Watch It!");
		btnWatchIt.setBounds(206, 419, 134, 29);
		panelDashboard.add(btnWatchIt);
		
		panelNewEvent = new JPanel();
		panelNewEvent.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelNewEvent.setBounds(6, 449, 346, 171);
		panelNewEvent.setBackground(Watchneighbors.COLOR);
		panelDashboard.add(panelNewEvent);
		panelNewEvent.setLayout(null);
		
		btnNewEvent = new JButton("New event");
		btnNewEvent.setBounds(6, 114, 334, 29);
		panelNewEvent.add(btnNewEvent);
		
		lblEventUserID = new JLabel("");
		lblEventUserID.setHorizontalAlignment(SwingConstants.LEFT);
		lblEventUserID.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblEventUserID.setBounds(6, 55, 174, 28);
		panelNewEvent.add(lblEventUserID);
		
		lblEventDescription = new JLabel("Event description");
		lblEventDescription.setBounds(6, 16, 280, 16);
		panelNewEvent.add(lblEventDescription);
		
		lblEventTimeStamp = new JLabel();
		lblEventTimeStamp.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEventTimeStamp.setEnabled(false);
		lblEventTimeStamp.setBounds(6, 149, 334, 16);
		panelNewEvent.add(lblEventTimeStamp);
		
		// add clock
		Timer timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblEventTimeStamp.setText(new Date().toString()); 
			}
        });
		timer.start();
		
		comboBoxEventDescription = new JComboBox<String>();
		comboBoxEventDescription.setModel(setComboBoxDummyText());
		comboBoxEventDescription.addItem(NOT_SELECTABLE_OPTION);
		for(String s : EVENT_TYPES) { comboBoxEventDescription.addItem(s);}
		comboBoxEventDescription.setBounds(6, 32, 334, 27);
		panelNewEvent.add(comboBoxEventDescription);
		
		lblUserCityDistrict = new JLabel();
		lblUserCityDistrict.setHorizontalAlignment(SwingConstants.LEFT);
		lblUserCityDistrict.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblUserCityDistrict.setBounds(6, 84, 148, 28);
		panelNewEvent.add(lblUserCityDistrict);
		
		lblEventLatLng = new JLabel("Event");
		lblEventLatLng.setBounds(279, 52, 61, 16);
		panelDashboard.add(lblEventLatLng);
		
		lblUserLatLng = new JLabel("User");
		lblUserLatLng.setBounds(183, 52, 61, 16);
		panelDashboard.add(lblUserLatLng);
		
		lblUsertLat = new JLabel("-");
		lblUsertLat.setEnabled(false);
		lblUsertLat.setBounds(183, 80, 61, 16);
		panelDashboard.add(lblUsertLat);
		
		lblUserLng = new JLabel("-");
		lblUserLng.setEnabled(false);
		lblUserLng.setBounds(183, 108, 61, 16);
		panelDashboard.add(lblUserLng);
		
		btnDashboardLogin = new JButton("Login");
		btnDashboardLogin.setBounds(6, 20, 134, 29);
		panelDashboard.add(btnDashboardLogin);
		
		btnDashboardLogout = new JButton("Logout");
		btnDashboardLogout.setBounds(6, 20, 134, 29);
		panelDashboard.add(btnDashboardLogout);
		
		btnCloseEvent = new JButton("Close It!");
		btnCloseEvent.setBounds(6, 419, 134, 29);
		panelDashboard.add(btnCloseEvent);
		
		lblCoordinates = new JLabel("COORDINATES");
		lblCoordinates.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblCoordinates.setBounds(6, 52, 134, 16);
		panelDashboard.add(lblCoordinates);
		btnCloseEvent.setVisible(false);
	}

	/**
	 * Shows city and district of the user.
	 * @return JLabel 
	 */
	public JLabel getlblUserCityDistrict() {
		return lblUserCityDistrict;
	}

	/**
	 * Shows a panel where the user can manage the events.
	 * @return JPanel
	 */
	public JPanel getPanelDashboard() {
		return panelDashboard;
	}

	/**
	 * Returns the JButton object used to edit the user data.
	 * @return JButton
	 */
	public JButton getBtnEdit() {
		return btnEdit;
	}

	/**
	 * Returns a JList of events.
	 * @return JList
	 */
	public JList<Event> getJlist() {
		return jList;
	}

	/**
	 * Returns the JButton object used to take in charge an event.
	 * @return JButton
	 */
	public JButton getBtnWatchIt() {
		return btnWatchIt;
	}

	/**
	 * Returns the JButton object used to close an event.
	 * @return JButton
	 */
	public JButton getBtnCloseEvent() {
		return btnCloseEvent;
	}

	/**
	 * Returns the JButton object used to add a new event.
	 * @return JButton
	 */
	public JButton getBtnNewEvent() {
		return btnNewEvent;
	}

	/**
	 * Returns the JButton object used to login.
	 * @return JButton
	 */
	public JButton getBtnDashboardLogin() {
		return btnDashboardLogin;
	}

	/**
	 * Returns the JButton object used to logout.
	 * @return JButton
	 */
	public JButton getBtnDashboardLogout() {
		return btnDashboardLogout;
	}

	/**
	 * Returns a JComboBox object that contains the description of every event.
	 * @return JComboBox
	 */
	public JComboBox<String> getComboBoxEventDescription() {
		return comboBoxEventDescription;
	}

	/**
	 * Gets the new event panel.
	 * @return JPanel
	 */
	public JPanel getPanelNewEvent() {
		return panelNewEvent;
	}

	/**
	 * Gets a JLabel object that represents the latitude of the event.
	 * @return JLabel
	 */
	public JLabel getLblEventLatitude() {
		return lblEventLatitude;
	}

	/**
	 * Gets a JLabel object that represents the longitude of the event.
	 * @return JLabel
	 */
	public JLabel getLblEventLongitude() {
		return lblEventLongitude;
	}

	/**
	 * Gets a JLabel object that represents the user's name and surname.
	 * @return JLabel
	 */
	public JLabel getLblEventUserID() {
		return lblEventUserID;
	}

	/**
	 * Gets a JLabel object that represents the description of the event.
	 * @return JLabel
	 */
	public JLabel getLblEventDescription() {
		return lblEventDescription;
	}

	/**
	 * Gets a JLabel object that represents the timestamp of the event.
	 * @return JLabel
	 */
	public JLabel getLblEventTimeStamp() {
		return lblEventTimeStamp;
	}

	/**
	 * Gets a JLabel object that represents the latitude of the user who has created the event.
	 * @return JLabel
	 */
	public JLabel getLblUserLat() {
		return lblUsertLat;
	}

	/**
	 * Gets a JLabel object that represents the longitude of the user who has created the event.
	 * @return JLabel
	 */
	public JLabel getLblUserLng() {
		return lblUserLng;
	}

	/**
	 * Returns a JScrollPane used to surf the list of the events.
	 * @return JScrollPane
	 */
	public JScrollPane getScrollPane() {
		return scrollPane;
	}
	
	private DefaultComboBoxModel<String> setComboBoxDummyText() {
		return new DefaultComboBoxModel<String>() {
		      private static final long serialVersionUID = 1L;
		      boolean selectionAllowed = true;
		      
		      @Override
		      public void setSelectedItem(Object anObject) {
		        if (!NOT_SELECTABLE_OPTION.equals(anObject)) {
		          super.setSelectedItem(anObject);
		        } else if (selectionAllowed) {
		          // Allow this just once
		          selectionAllowed = false;
		          super.setSelectedItem(anObject);
		        }
		      }
		    };
	}
}