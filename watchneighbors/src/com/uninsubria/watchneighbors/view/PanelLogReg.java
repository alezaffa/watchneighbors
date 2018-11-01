package com.uninsubria.watchneighbors.view;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.uninsubria.watchneighbors.controller.Watchneighbors;

/**
 * Creates the buttons used for login, registration and searching.
 * @author alessandro, christian, denise, silvia
 *
 */
public class PanelLogReg {

	private JPanel panelLogReg;
	private JButton btnLogin;
	private JButton btnRegister;
	private JButton btnSearch;

	/**
	 * Creates the panel and its buttons.
	 */
	public PanelLogReg() {
		
		panelLogReg = new JPanel();
		panelLogReg.setBounds(457, 40, 140, 287);
		panelLogReg.setBackground(Watchneighbors.COLOR);
		panelLogReg.setLayout(null);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(0, 18, 140, 39);
		panelLogReg.add(btnLogin);
		
		btnRegister = new JButton("Register");
		btnRegister.setBounds(0, 235, 140, 39);
		panelLogReg.add(btnRegister);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(0, 127, 140, 39);
		panelLogReg.add(btnSearch);
	
	}

	/**
	 * Returns a JPanel that contains the buttons "Login", "Search" and "Register".
	 * @return JPanel
	 */
	public JPanel getPanelLogReg() {
		return panelLogReg;
	}

	/**
	 * Returns a JButton named "Login" used for login.
	 * @return JButton
	 */
	public JButton getBtnLogin() {
		return btnLogin;
	}

	/**
	 * Returns a JButton named "Register" used for the registration.
	 * @return JButton
	 */
	public JButton getBtnRegister() {
		return btnRegister;
	}

	/**
	 * Returns a JButton named "Search" used for searching.
	 * @return JButton
	 */
	public JButton getBtnSearch() {
		return btnSearch;
	}
}