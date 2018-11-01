package com.uninsubria.watchneighbors.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.validator.routines.InetAddressValidator;

import com.uninsubria.watchneighbors.controller.Watchneighbors;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The main class that asks for the Server's IP address and connect the client to the server.
 */
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textIpAddress;
	private JPanel panel;
	private JLabel label;
	private JButton btnConnect;
	private static Main frame;
	
	/**
	 * Launch the application.
	 * @param args The arguments passed to the program. It should be null.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Main();
					frame.setVisible(true);
					frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(231, 231, 231));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(231, 231, 231));
		panel.setBounds(6, 98, 438, 43);
		contentPane.add(panel);
		
		label = new JLabel("IP Address");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBounds(21, 11, 75, 16);
		panel.add(label);
		
		textIpAddress = new JTextField();
		textIpAddress.setText((String) null);
		textIpAddress.setColumns(10);
		textIpAddress.setBounds(95, 5, 134, 28);
		panel.add(textIpAddress);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String host = textIpAddress.getText();
				boolean isValidAddress = InetAddressValidator.getInstance().isValid(host);
				
				if(isValidAddress || host.isEmpty()) {
					String[] ipAddresses = new String[] {host};
					Watchneighbors.start(ipAddresses);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame, "Not a valid IP address", "Warning", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConnect.setBounds(241, 6, 125, 29);
		panel.add(btnConnect);
	}
}
