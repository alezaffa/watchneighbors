package com.uninsubria.watchneighbors.controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.validator.routines.InetAddressValidator;

import com.uninsubria.watchneighbors.dao.WatchneighborsDAO;
import com.uninsubria.watchneighbors.model.Event;
import com.uninsubria.watchneighbors.model.User;
import com.uninsubria.watchneighbors.view.Table;

/**
 * Creates a graphic interface that allows the user to start the server.
 * @author alessandro, christian, denise, silvia
 *
 */

public class WatchneighborsServer extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final Color COLOR = new Color(0xE7, 0xE7, 0xE7);	
	private static String host;
	private JPanel contentPane;
	private JTextField textIPAddrress;
	private JLabel lblIpAddress;
	private JButton btnStart;
	private JPanel panelStartStop;
	private JLabel lblServerStatus;
	private static Server server;

	/**
	 * Launch the application.
	 * @param args that represent the IP address.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					host = (args.length < 1) ? InetAddress.getLocalHost().getHostAddress() : args[0];
					WatchneighborsServer frame = new WatchneighborsServer();
					frame.setVisible(true);
					boolean isValidAddress = InetAddressValidator.getInstance().isValid(host);
					
					if(isValidAddress) {
						server = new Server();
					} else {
						JOptionPane.showMessageDialog(frame, "Not a valid IP address", "Warning", JOptionPane.ERROR_MESSAGE);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the frame.
	 */
	public WatchneighborsServer() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(COLOR);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		initializeMenuBar();
		
		lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIpAddress.setBounds(21, 11, 106, 16);
		
		panelStartStop = new JPanel();
		panelStartStop.setBounds(6, 63, 488, 80);
		contentPane.add(panelStartStop);
		panelStartStop.setBackground(COLOR);
		panelStartStop.setLayout(null);
		panelStartStop.add(lblIpAddress);
		
		textIPAddrress = new JTextField();
		textIPAddrress.setEditable(false);
		textIPAddrress.setBounds(132, 5, 134, 28);
		panelStartStop.add(textIPAddrress);
		textIPAddrress.setColumns(10);
		textIPAddrress.setText(host);
		
		lblServerStatus = new JLabel("Server stopped");
		lblServerStatus.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblServerStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblServerStatus.setBounds(6, 58, 476, 16);
		panelStartStop.add(lblServerStatus);
		
		btnStart = new JButton("Start");
		btnStart.setBounds(278, 6, 153, 29);
		panelStartStop.add(btnStart);
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.setProperty("java.rmi.server.hostname", host);
				server.start();
				btnStart.setEnabled(false);
				lblServerStatus.setText("Server running");
				lblServerStatus.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				
			}
		});
		
		/**
		btnStop = new JButton("Stop");
		btnStop.setBounds(271, 5, 75, 29);
		panelStartStop.add(btnStop);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					server.stop();
					lblServerStatus.setText("Server stopped");
					lblServerStatus.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(contentPane, "Server not running", "Warning", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		*/
	}
	
	private void initializeMenuBar() {
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		JMenu mnView = new JMenu("View");
		JMenu mnAbout = new JMenu("?");
		
		JMenuItem mnClose = new JMenuItem("Quit");
		JMenuItem mnViewRegUsers = new JMenuItem("Registered users");
		JMenuItem mnView10resolvedEvent = new JMenuItem("Ten resolved events");
		
		/**
		 * Add menu to menuBar
		 */
		menuBar.add(mnFile);
		menuBar.add(mnView);
		menuBar.add(mnAbout);

		mnFile.add(mnClose);
		
		mnView.add(mnViewRegUsers);
		mnView.add(mnView10resolvedEvent);
		
		/**
		 * File menu
		 */
		mnClose.addActionListener(
		
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						System.exit(0);		
					}
				}	
		);
		
		mnViewRegUsers.addActionListener(
				
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						WatchneighborsDAO dao;
						List<User> list;
						try {
							dao = new WatchneighborsDAO();
							list = dao.getRegUsers();

							DefaultTableModel tableModel = new DefaultTableModel(new String[]{"City", "District", "User", "Name", "Surname"}, 0);
							
							new Table(list, tableModel);
							
						} catch (SQLException sqle) {
							sqle.printStackTrace();
						}
					}
				}
		);
		
		mnView10resolvedEvent.addActionListener(
				
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						WatchneighborsDAO dao;
						List<Event> list;
						try {
							dao = new WatchneighborsDAO();
							list = dao.get10History();

							DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "User", "Date creation", "Date completion", "City",
									"Status", "Description", "Latitude", "Longitude", "City", "Is watched?", "User latitude",
									"User longitude", "Outcome", "Resolvent user"}, 0);
							
							new Table(list, tableModel);
							
						} catch (SQLException sqle) {
							sqle.printStackTrace();
						}
					}
				}
		);
	}
}
