package com.uninsubria.watchneighbors.controller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;

import com.uninsubria.watchneighbors.model.Event;
import com.uninsubria.watchneighbors.model.Geoposition;
import com.uninsubria.watchneighbors.model.NullGeopositionException;
import com.uninsubria.watchneighbors.model.User;
import com.uninsubria.watchneighbors.util.MailUtilities;
import com.uninsubria.watchneighbors.util.OpenStreetMapUtils;
import com.uninsubria.watchneighbors.view.CustomMapController;
import com.uninsubria.watchneighbors.view.Outcome;
import com.uninsubria.watchneighbors.view.PanelDashboard;
import com.uninsubria.watchneighbors.view.PanelInsertUserData;
import com.uninsubria.watchneighbors.view.PanelLogReg;
import com.uninsubria.watchneighbors.view.PanelModify;
import com.uninsubria.watchneighbors.view.PanelSearchViewMap;

/**
 * Creates and shows a graphic interface which allows the users to use the application.
 * @author alessandro, christian, denise, silvia
 *
 */
public class Watchneighbors extends JFrame implements JMapViewerEventListener {

	private static final long serialVersionUID = 1L;
	/**
	 * The GUI color.
	 */
	public static final Color COLOR = new Color(0xE7, 0xE7, 0xE7);	
	private static final Coordinate DEFAULT_COORDINATE = new Coordinate(45.9, 8.9);
	private static final int YES_ANSWER = 0;
	private final JMapViewerTree treeMap;
	private JFrame frame;
	private CardLayout cardLayout;
	private JPanel panelContainer;
	private JPanel panelMain;
	private JPanel panelViewMap;
	private JPanel panelEdit;
	private PanelDashboard panelDashboard;
	private PanelInsertUserData panelInsertUserData;
	private PanelSearchViewMap panelSearchAndView;
	private PanelModify panelModify;
	private PanelLogReg panelLogReg;
	private Geoposition geoposition;
	private User loggedUser;
	private	List<Event> newEventList;
	private Event selectedEvent;
	private MailUtilities createValidMail;
	private boolean isNewEventPressed;
	private boolean isEventSelected;
	protected static Client cli;
	private DefaultListModel<Event> model;
	private static String ipAddress;
	

	/**
	 * Checks the IP address and instantiates the client.
	 * @param args that represent the IP address.
	 */
	public static void start(final String[] args) {	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Watchneighbors window = null;
				try {
					
					if(args.length > 0) {
						setIpAddress(args[0]);
					} else {
						setIpAddress(InetAddress.getLocalHost().getHostAddress());
					}
					window = new Watchneighbors();
					cli = new Client(window);
					window.frame.setVisible(true);
					
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "Not valid IP address", "Warning", JOptionPane.ERROR_MESSAGE);
				} catch(ConnectException ce) {
					JOptionPane.showMessageDialog(null, "Server not reachable", "Warning", JOptionPane.ERROR_MESSAGE);
					window.frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Watchneighbors() {
		treeMap = new JMapViewerTree("Zones");
		treeMap.getViewer().setBounds(0, 0, 588, 541);
		treeMap.setBackground(COLOR);
		treeMap.setBounds(0, 79, 588, 547);
		treeMap.getViewer().setLayout(null);
		treeMap.setLayout(null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame(); 
		frame.getContentPane().setBackground(COLOR);
		frame.setBounds(100, 100, 967, 677);
		frame.setBackground(COLOR);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		newEventList = new ArrayList<Event>();
		model = new DefaultListModel<Event>();
		cardLayout = new CardLayout();
		panelInsertUserData = new PanelInsertUserData();
		
		/*
		 * Login
		 */
		panelLogReg = new PanelLogReg();
		panelLogReg.getBtnLogin().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					User u = null;
					
					if(!(panelInsertUserData.getTextFieldUserID().getText().isEmpty()) || 
							!(new String(panelInsertUserData.getPasswordField().getPassword()).isEmpty())) {
						u = new User(panelInsertUserData.getTextFieldUserID().getText(), 
								new String(panelInsertUserData.getPasswordField().getPassword()));

						if(cli.login(u)) {
							onLoginShowControlPanel(u);
							onLoginSetEvents(loggedUser.getDistrict());
							panelSearchAndView.getComboBoxViewMapCity().setSelectedItem(loggedUser.getCity());
							panelSearchAndView.getComboBoxViewMapDistrict().setSelectedItem(loggedUser.getDistrict());
						} else {
							JOptionPane.showMessageDialog(null, "Connection error!", "Warning", JOptionPane.ERROR_MESSAGE);
						}

					} else {
						JOptionPane.showMessageDialog(null, "Empty fields!", "Warning", JOptionPane.WARNING_MESSAGE);
					}
					
				} catch(NullPointerException npe) {
					npe.printStackTrace();
					JOptionPane.showMessageDialog(null, "Check your data, not a valid userID", "Warning", JOptionPane.WARNING_MESSAGE);
				} catch(SQLException sqle) {
					sqle.printStackTrace();
				} catch(Exception e) {
					e.printStackTrace();
				} 
			}
		});
		
		/*
		 *  Registration
		 */
		panelLogReg.getBtnRegister().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					User u = null;
					if(!(panelInsertUserData.getTextFieldUserID().getText().equals("") ||
							panelInsertUserData.getPasswordField().getPassword().toString().equals("") ||
							panelInsertUserData.getTextFieldName().getText().equals("") ||
							panelInsertUserData.getTextFieldSurname().getText().equals("") ||
							panelInsertUserData.getTextFieldAddress().getText().equals("") ||
							panelInsertUserData.getComboBoxCity().getSelectedItem().equals("Select") ||
							panelInsertUserData.getTextFieldMail().getText().equals(""))) {
								
						geoposition = onSelectionGetGeopositions(panelInsertUserData.getComboBoxCity(), 
								panelInsertUserData.getComboBoxDistrict());
						createValidMail = new MailUtilities(new String[] { 
								panelInsertUserData.getTextFieldMail().getText().toString() 
						});
						
						u = new User(panelInsertUserData.getTextFieldUserID().getText(),
										new String(panelInsertUserData.getPasswordField().getPassword()),
										panelInsertUserData.getTextFieldName().getText(),
										panelInsertUserData.getTextFieldSurname().getText(), 
										panelInsertUserData.getTextFieldMail().getText(),
										panelInsertUserData.getComboBoxCity().getSelectedItem().toString(),
										panelInsertUserData.getTextFieldAddress().getText().concat(", " + panelInsertUserData.getTextFieldNumber().getText()),
										geoposition.getLat(), 
										geoposition.getLon(), 
										panelInsertUserData.getComboBoxDistrict().getSelectedItem().toString());
						
						String cityAddress = u.getCity() + " " + u.getAddress();
						
						Double lat = OpenStreetMapUtils.getInstance().getCoordinates(cityAddress).get("lat");
						Double lon = OpenStreetMapUtils.getInstance().getCoordinates(cityAddress).get("lon");
						
						Geoposition myAddressGeoposition = new Geoposition(lat, lon);
						Geoposition myDistrictGeoposition = onSelectionGetGeopositions(panelInsertUserData.getComboBoxCity(), 
								panelInsertUserData.getComboBoxDistrict());
						
						if(isAddressInsideDistrict(myDistrictGeoposition, myAddressGeoposition)) {
							if(cli.regUser(u)) {
						
								createValidMail.sendConfirmRegistrationByMail(u.getUserID(), u.getPassword());
								onLoginShowControlPanel(u);
								onLoginSetEvents(loggedUser.getDistrict());
							} else {
								JOptionPane.showMessageDialog(null, "User already registred or database connection error", "Warning", JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Not a valid city address", "Warning", JOptionPane.WARNING_MESSAGE);
						}
						
					} else {
						JOptionPane.showMessageDialog(null, "Empty fields!", "Warning", JOptionPane.WARNING_MESSAGE);
					}
				
				} catch(NullGeopositionException nge) {
					nge.printStackTrace();
					JOptionPane.showMessageDialog(null, "Not a valid city address", "Warning", JOptionPane.WARNING_MESSAGE);
				} catch(NullPointerException npe) {
					npe.printStackTrace();
					JOptionPane.showMessageDialog(null, "Check your data", "Warning", JOptionPane.WARNING_MESSAGE);
				} catch(SQLException sqle) {
					sqle.printStackTrace();		
				} catch(AddressException ae) {
					JOptionPane.showMessageDialog(null, "Insert a valid mail", "Warning", JOptionPane.WARNING_MESSAGE);
				} catch (MessagingException e) {
					JOptionPane.showMessageDialog(null, "Check your internet connection", "Warning", JOptionPane.ERROR_MESSAGE);
				} catch(Exception e) {
					e.printStackTrace();
				} 	
			}
		});
		
		/*
		 *  Search
		 */
		panelLogReg.getBtnSearch().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				panelDashboard.getBtnDashboardLogin().setVisible(true);
				panelDashboard.getBtnDashboardLogout().setVisible(false);
				panelDashboard.getBtnEdit().setVisible(false);
				panelDashboard.getBtnWatchIt().setVisible(false);
				panelDashboard.getBtnNewEvent().setVisible(false);
				panelDashboard.getPanelNewEvent().setVisible(false);
				panelViewMap.add(panelSearchAndView.getPanelSearchViewMap());
				cardLayout.show(panelContainer, "3");
			}
		});
		
		/*
		 * Shows modify panel.
		 */
		panelModify = new PanelModify();
		panelModify.getBtnCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContainer, "3");
			}
		});
		
		/*
		 *  Confirms modify.
		 */
		panelModify.getBtnConfirm().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isUpdated;
				try {
					
					geoposition = onSelectionGetGeopositions(panelInsertUserData.getComboBoxCity(),
							panelInsertUserData.getComboBoxDistrict());
					
					User modifiedUser = new User(panelInsertUserData.getTextFieldUserID().getText(), 
							new String(panelInsertUserData.getPasswordField().getPassword()), 
							panelInsertUserData.getTextFieldName().getText(), 
							panelInsertUserData.getTextFieldSurname().getText(), 
							panelInsertUserData.getTextFieldMail().getText(), 
							panelInsertUserData.getComboBoxCity().getSelectedItem().toString(), 
							panelInsertUserData.getTextFieldAddress().getText() + ", " +
							panelInsertUserData.getTextFieldNumber().getText(), 
							geoposition.getLat(), 
							geoposition.getLon(), 
							panelInsertUserData.getComboBoxDistrict().getSelectedItem().toString());
					
					String cityAddress = modifiedUser.getCity() + " " + modifiedUser.getAddress();

					Double lat = OpenStreetMapUtils.getInstance().getCoordinates(cityAddress).get("lat");
					Double lon = OpenStreetMapUtils.getInstance().getCoordinates(cityAddress).get("lon");

					Geoposition myAddressGeoposition = new Geoposition(lat, lon);
					Geoposition myDistrictGeoposition = onSelectionGetGeopositions(panelInsertUserData.getComboBoxCity(), 
							panelInsertUserData.getComboBoxDistrict());
					
					if(!modifiedUser.getUserID().equals(loggedUser.getUserID())) {
						JOptionPane.showMessageDialog(panelMain, "You cannot modify your UserId");
					} else if(!modifiedUser.getPassword().equals(loggedUser.getPassword()) ||
							!modifiedUser.getName().equals(loggedUser.getName()) ||
							!modifiedUser.getSurname().equals(loggedUser.getSurname()) ||
							!modifiedUser.getEmail().equals(loggedUser.getEmail()) ||
							!modifiedUser.getCity().equals(loggedUser.getCity()) ||
							!modifiedUser.getAddress().equals(loggedUser.getAddress()) ||
							modifiedUser.getUsrLat() != loggedUser.getUsrLat() ||
							modifiedUser.getUsrLng() != loggedUser.getUsrLng() ||
							!modifiedUser.getDistrict().equals(loggedUser.getDistrict())) {

							if(isAddressInsideDistrict(myDistrictGeoposition, myAddressGeoposition)) {
								createValidMail = new MailUtilities(new String[] { 
										panelInsertUserData.getTextFieldMail().getText().toString() 
								});
								isUpdated = cli.updateUser(modifiedUser);
								
								if(isUpdated) {
									JOptionPane.showMessageDialog(panelMain, "User updated successfully - Please login again", "Information", JOptionPane.INFORMATION_MESSAGE);
									onLogoutReset();
								} else {
									JOptionPane.showMessageDialog(panelMain, "User not updated", "Information", JOptionPane.INFORMATION_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "Not a valid city address", "Warning", JOptionPane.WARNING_MESSAGE);
							}
						
					} else {
						JOptionPane.showMessageDialog(panelMain, "No changes has been applied", "Information", JOptionPane.INFORMATION_MESSAGE);
					}
					
				} catch(NullGeopositionException nge) {
					JOptionPane.showMessageDialog(null, "Not a valid city address", "Warning", JOptionPane.WARNING_MESSAGE);
				} catch (RemoteException re) {
					re.printStackTrace();
				} catch(AddressException ae) {
					JOptionPane.showMessageDialog(null, "Insert a valid mail", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		/*
		 *  Deletes user.
		 */
		panelModify.getBtnDelete().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isDeleted;
				try {
					int reply = JOptionPane.showConfirmDialog(
							panelEdit,
				            "Are you sure you want to delete your account?",
				            "Watchneighbors",
				            JOptionPane.CANCEL_OPTION,
				            JOptionPane.INFORMATION_MESSAGE
				            );
					if(reply == YES_ANSWER) { 
						isDeleted = cli.deleteUser(loggedUser);
						if(isDeleted == true) {
							JOptionPane.showMessageDialog(panelMain, "User deleted successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
							onLogoutReset();
						} 
						if(isDeleted == false) {
							JOptionPane.showMessageDialog(panelMain, "User not deleted", "Information", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		/*
		 *  Panel search and view map - gets the position based on city and district selection and set map position.
		 */
		panelSearchAndView = new PanelSearchViewMap();
		panelSearchAndView.getBtnSearchViewMap().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(panelSearchAndView.getComboBoxViewMapCity().getSelectedItem().equals("Select")) {
					JOptionPane.showMessageDialog(panelMain, "Please select a city!", "Information", JOptionPane.INFORMATION_MESSAGE);
				} else {
					geoposition = onSelectionGetGeopositions(panelSearchAndView.getComboBoxViewMapCity(), 
							panelSearchAndView.getComboBoxViewMapDistrict());
					model.clear();
					panelDashboard.getJlist().setModel(model);
					if(geoposition == null) {
						map().setDisplayPosition(DEFAULT_COORDINATE, 9);
					} else {
						map().setDisplayPosition(geoposition.getMarker(), 17);
						onLoginSetEvents(panelSearchAndView.getComboBoxViewMapDistrict().getSelectedItem().toString());
					}
				}
			}
		});
		
		/*
		 *  Shows to the user the login GUI after pressing login button while in search mode.
		 */
		panelDashboard = new PanelDashboard();
		panelDashboard.getBtnDashboardLogin().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onLogoutReset();
				cardLayout.show(panelContainer, "1");
			}
		});
		
		/*
		 *  When user clicks on button logout, reset all fields and shows the main GUI.
		 */
		panelDashboard.getBtnDashboardLogout().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onLogoutReset();
			}
		});
		
		/*
		 *  Shows edit menu.
		 */
		panelDashboard.getBtnEdit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelEdit.add(panelInsertUserData.getPanelInsertUserData());
				cardLayout.show(panelContainer, "2");	
			}
		});
		
		/*
		 *  Enable a new event creation
		 */
		panelDashboard.getBtnNewEvent().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(panelDashboard.getComboBoxEventDescription().getSelectedItem().equals(PanelDashboard.NOT_SELECTABLE_OPTION)) {
					JOptionPane.showMessageDialog(panelDashboard.getPanelDashboard(), "Please select a valid event description");
				} else {
					int reply = JOptionPane.showConfirmDialog(
							panelDashboard.getPanelDashboard(),
				            "Add your event on map by clicking on it",
				            "Watchneighbors",
				            JOptionPane.CANCEL_OPTION,
				            JOptionPane.INFORMATION_MESSAGE
				            );
					if(reply == YES_ANSWER) { 
						isNewEventPressed = true;
					}
				}
			}
		});
		
		/* 
		 * If the user press on watch it button and an event is selected from the map
		 * then he will be able to manage it
		 */
		panelDashboard.getBtnWatchIt().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					if(isEventSelected) {
						selectedEvent = panelDashboard.getJlist().getSelectedValue();
						
						if(loggedUser.getDistrict().equals(selectedEvent.getDistrict())) {
							
							if(!cli.setAndWatch(selectedEvent, loggedUser)) {
								
								JOptionPane.showMessageDialog(panelDashboard.getPanelDashboard(), "Event already taken");
								panelDashboard.getBtnCloseEvent().setVisible(false);
								panelDashboard.getBtnWatchIt().setVisible(true);
								
							} else {
								
								JOptionPane.showMessageDialog(panelDashboard.getPanelDashboard(), "You are now in charge of this event");
								panelDashboard.getBtnCloseEvent().setVisible(true);
								panelDashboard.getBtnWatchIt().setVisible(false);
							}
							
						} else {
							JOptionPane.showMessageDialog(panelDashboard.getPanelDashboard(), "Event not in your district or already taken");
							panelDashboard.getBtnCloseEvent().setVisible(false);
							panelDashboard.getBtnWatchIt().setVisible(true);
						}
						
					} else {
						JOptionPane.showMessageDialog(panelDashboard.getPanelDashboard(), "Please select an event from map or create an event before");
					}
					
				} catch (RemoteException re) {
					re.printStackTrace();
				}
			}
		});
		
		/*
		 *  Open a new window with the possibility to close the event
		 */
		panelDashboard.getBtnCloseEvent().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Outcome.showFrame(selectedEvent, treeMap);
			}
		});
		
		panelContainer = new JPanel();
		panelContainer.setBackground(COLOR);	
		panelContainer.setBounds(6, 6, 957, 632);
		frame.getContentPane().add(panelContainer);
		panelContainer.setLayout(cardLayout);
		
		panelMain = new JPanel();
		panelMain.setBackground(COLOR);
		panelMain.setLayout(null);
		panelMain.add(panelInsertUserData.getPanelInsertUserData());
		panelMain.add(panelLogReg.getPanelLogReg());
		
		panelViewMap = new JPanel();
		panelViewMap.setBackground(COLOR);
		panelViewMap.setLayout(null);
		panelViewMap.add(treeMap);
		panelViewMap.add(panelDashboard.getPanelDashboard());
		panelViewMap.add(panelSearchAndView.getPanelSearchViewMap());
		
		panelEdit = new JPanel();
		panelEdit.setBackground(COLOR);
		panelEdit.setLayout(null);
		panelEdit.add(panelModify.getPanelModifyDeleteCancel());
		
		panelContainer.add(panelMain, "1");
		panelContainer.add(panelEdit, "2");
		panelContainer.add(panelViewMap, "3");
		
		cardLayout.show(panelContainer, "1");
		
		map().setDisplayPosition(DEFAULT_COORDINATE, 9);
        map().addJMVListener(this);
        map().addMouseListener(new CustomMapController(map()) {

			@Override
            public void mouseClicked(MouseEvent e) {
				try {
					
            		if(e.getClickCount() == 1 && 
            				e.getButton() == MouseEvent.BUTTON1  && 
            				isNewEventPressed == true && 
            				!panelDashboard.getComboBoxEventDescription().getSelectedItem().equals(PanelDashboard.NOT_SELECTABLE_OPTION)) {
        				
            			if(isMouseClickInsideDistrict(e, loggedUser.getGeoposition())) {
            				Event currentEvent = onClickAddEvent(e, panelDashboard, loggedUser);
            				int id = cli.regEvent(currentEvent);
            				currentEvent.setEventId(id);

    					} else  {
    						JOptionPane.showMessageDialog(treeMap, "You cannot insert an event outside your district!", "Warning", JOptionPane.WARNING_MESSAGE);
    						isNewEventPressed = false;
    					}
            			
    				} else {
    					isEventSelected = isEventSelected(e, panelDashboard);
    				}
				} catch (RemoteException re) {
					re.printStackTrace();
				} finally {
        			isNewEventPressed = false;
				}
            }
        });
	}

	/*
	 *  Checks if a geoposition (x, y) is inside a circle with center in (centerX, centerY).
	 */
	private boolean isAddressInsideDistrict(Geoposition center, Geoposition coordinate) {
		
		boolean isInisdeDistrict = false;
		
		double x = center.getLat();
		double y = center.getLon();
		double centerX = coordinate.getLat();
		double centerY = coordinate.getLon();
		
		if (Math.sqrt( (((x-centerX)*(x-centerX)) + (y-centerY)*(y-centerY))) < 0.005) {
			isInisdeDistrict = true;
		}
		return isInisdeDistrict;
	}
	
	/**
	 * It shows the control panel with map and dashboard.
	 */
	private void onLoginShowControlPanel(User u) throws SQLException, RemoteException {
		
		loggedUser = cli.getUserFromDB(u);
		
		String[] splittedAddress = loggedUser.getAddress().split(", ");
		String lat = (loggedUser.getUsrLat() + "").substring(0, 6);
		String lon = (loggedUser.getUsrLng() + "").substring(0, 6);
		
		panelDashboard.getlblUserCityDistrict().setText(loggedUser.getCity() + " " + loggedUser.getDistrict());
		panelDashboard.getLblUserLat().setText(lat);
		panelDashboard.getLblUserLng().setText(lon);
		panelDashboard.getLblEventUserID().setText(loggedUser.getUserID());
		panelDashboard.getBtnDashboardLogin().setVisible(false);
		panelDashboard.getBtnDashboardLogout().setVisible(true);
		panelDashboard.getBtnEdit().setVisible(true);
		panelDashboard.getBtnNewEvent().setVisible(true);
		panelDashboard.getBtnWatchIt().setVisible(true);
		panelDashboard.getPanelNewEvent().setVisible(true);
		
		cardLayout.show(panelContainer, "3");
		
		ICoordinate coordinate = loggedUser.getGeoposition();
		map().setDisplayPosition(coordinate, 17);
		
		panelInsertUserData.getTextFieldName().setText(loggedUser.getName());
		panelInsertUserData.getTextFieldSurname().setText(loggedUser.getSurname());
		panelInsertUserData.getTextFieldAddress().setText(splittedAddress[0]);
		if(splittedAddress.length > 1) {
			panelInsertUserData.getTextFieldNumber().setText(splittedAddress[1]);
		}
		panelInsertUserData.getTextFieldMail().setText(loggedUser.getEmail());
		panelInsertUserData.getComboBoxCity().setSelectedItem(loggedUser.getCity());
		panelInsertUserData.getComboBoxDistrict().setSelectedItem(loggedUser.getDistrict());
	}
	
	/**
	 * When the user select from comboBox it returns a geoposition object which specifies the 
	 * selected position.
	 */
	private Geoposition onSelectionGetGeopositions(JComboBox<String> comboBoxCity, JComboBox<String> comboBoxDistrict) {
		
		try {
			String address = comboBoxCity.getSelectedItem() + " " + comboBoxDistrict.getSelectedItem();
			Double lat = OpenStreetMapUtils.getInstance().getCoordinates(address).get("lat");
			Double lon = OpenStreetMapUtils.getInstance().getCoordinates(address).get("lon");
			
			return new Geoposition(lat, lon, new MapMarkerDot(lat, lon));
			
		} catch(NullPointerException nge) {
			JOptionPane.showMessageDialog(treeMap, "Error retrieving map information", "Warning", JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
	}
	
	/*
	 * On login sets a list of event and adds the relative markers on map.
	 */
	private void onLoginSetEvents(String district) {
		
		try {
			newEventList = cli.getActiveEvent(district);
			
			if(newEventList.size() <= 0) {
				JOptionPane.showMessageDialog(null, "No active events for the selected area right now", "Warning", JOptionPane.WARNING_MESSAGE);
			} else {
				
				for(Event ev : newEventList) {

					model.addElement(ev);
					MapMarkerDot marker = new MapMarkerDot(ev.getLat(), ev.getLon());

					if(ev.isWatchIt()) {
						marker.setBackColor(Color.ORANGE);
					} else {
						marker.setBackColor(Color.RED);
					}
					map().addMapMarker(marker);

				}
				panelDashboard.getJlist().setModel(model);
				panelDashboard.getJlist().setSelectedIndex(0);
				panelDashboard.getJlist().setFixedCellHeight(135);

			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	
	/*
	 *  On logout reset.
	 */
	private void onLogoutReset() {
		loggedUser = null;
		map().removeAllMapMarkers();
		map().setDisplayPosition(DEFAULT_COORDINATE, 9);
		panelInsertUserData.clearFields();
		panelSearchAndView.clearFields();
		model.clear();
		panelDashboard.getLblUserLat().setText("-");
		panelDashboard.getLblUserLng().setText("-");
		panelDashboard.getLblEventLatitude().setText("-");
		panelDashboard.getLblEventLongitude().setText("-");
		panelDashboard.getBtnCloseEvent().setVisible(false);
		panelDashboard.getJlist().setModel(model);
		panelMain.add(panelInsertUserData.getPanelInsertUserData());
		cardLayout.show(panelContainer, "1");
	}
	
	/*
	 * Return the current map.
	 */
	private JMapViewer map() {
        return treeMap.getViewer();
    }

	/**
	 * Method used to notify all the observers.
	 * @param e
	 */
	public void update(Event e) {
		if((e.getCity() + " " + e.getDistrict()).equals(
				panelDashboard.getlblUserCityDistrict().getText().toString())) {
		
			MapMarkerDot marker = new MapMarkerDot(e.getLat(),e.getLon());
			
			if(e.getStatus().equals("CLOSED")) {
				
				panelDashboard.getBtnCloseEvent().setVisible(false);
				panelDashboard.getBtnWatchIt().setVisible(true);
				
				boolean foundIt = false;
				int i = 0;
				
				while(!foundIt && i<model.size()) {
					
					if(model.getElementAt(i).getEventId() == e.getEventId()) {
						foundIt = true;
						break;
					}
					i++;
				}
				
				if(foundIt) {
					
					marker.setBackColor(Color.GREEN);
					map().addMapMarker(marker);
					
					model.getElementAt(i).setStatus(e.getStatus());
					model.getElementAt(i).setOutcome(e.getOutcome());
					model.getElementAt(i).setDateCompletion(new Date());
					model.getElementAt(i).setResolventUser(e.getResolventUser());
					
				}
				
			} else if(e.isWatchIt()) {
				
				boolean foundIt = false;
				int i = 0;
				
				while(!foundIt && i<model.size()) {
					
					if(model.getElementAt(i).getEventId() == e.getEventId()) {
						foundIt = true;
						break;
					}
					i++;
				}
				
				if(foundIt) {
					Event watchedEvent = model.remove(i);
					
					marker.setBackColor(Color.ORANGE);
					map().addMapMarker(marker);
					
					watchedEvent.setWatchIt(e.isWatchIt());
					watchedEvent.setResolventUser(e.getResolventUser());
					watchedEvent.setDateCompletion(new Date());
					
					model.addElement(watchedEvent);
					
				}
				
			} else {
				
				marker.setBackColor(Color.BLUE);
				map().addMapMarker(marker);
				model.addElement(e);

			}
			panelDashboard.getJlist().setModel(model); 
		}
	}
	
	@Override
    public void processCommand(JMVCommandEvent command) {
        if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
                command.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
        }
    }

	/**
	 * Returns a string representing the IP address.
	 * @return String represents a the IP address
	 */
	public static String getIpAddress() {
		return ipAddress;
	}

	/**
	 * Sets the IP address used for the connection to the server.
	 * @param ipAddress represents the IP address
	 */
	public static void setIpAddress(String ipAddress) {
		Watchneighbors.ipAddress = ipAddress;
	}
}