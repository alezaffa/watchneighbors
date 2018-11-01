package com.uninsubria.watchneighbors.view;

import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.uninsubria.watchneighbors.controller.Watchneighbors;
import com.uninsubria.watchneighbors.util.OpenStreetMapUtils;

/**
 * Creates a panel in which the users can insert their data.
 * @author alessandro, christian, denise, silvia
 *
 */
public class PanelInsertUserData {
	
	private JTextField textFieldUserID;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JTextField textFieldAddress;
	private JTextField textFieldNumber;
	private JTextField textFieldMail;
	private JPasswordField passwordField;

	private JComboBox<String> comboBoxCity;
	private JComboBox<String> comboBoxDistrict;

	private JPanel panelInsertUserData;
	private JLabel lblUserID;
	private JLabel lblPassword;
	private JLabel lblName;
	private JLabel lblSurname;
	private JLabel lblAddress;
	private JLabel lblNumber;
	private JLabel lblCity;
	private JLabel lblDistrict;
	private JLabel lblEmail;

	protected static final String NOT_SELECTABLE_OPTION = "Select";
	protected static final String COMO = "Como";
	protected static final String LUGANO = "Lugano";
	protected static final String VARESE = "Varese";
	protected static final String[] cities = {COMO, LUGANO, VARESE};
	
	/**
	 * Create a frame with fields in which the users can insert their data.
	 */
	public PanelInsertUserData() {
		
		panelInsertUserData = new JPanel();
		panelInsertUserData.setBounds(40, 40, 336, 274);
		panelInsertUserData.setBackground(Watchneighbors.COLOR);
		panelInsertUserData.setLayout(null);
		
		lblUserID = new JLabel("UserID");
		lblUserID.setBounds(0, 0, 148, 16);
		panelInsertUserData.add(lblUserID);
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(183, 0, 153, 16);
		panelInsertUserData.add(lblPassword);
		
		textFieldUserID = new JTextField();
		textFieldUserID.setBounds(0, 21, 148, 28);
		panelInsertUserData.add(textFieldUserID);
		textFieldUserID.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(183, 21, 153, 28);
		panelInsertUserData.add(passwordField);
		
		lblName = new JLabel("Name");
		lblName.setBounds(0, 54, 148, 16);
		panelInsertUserData.add(lblName);
		
		lblSurname = new JLabel("Surname");
		lblSurname.setBounds(183, 54, 153, 16);
		panelInsertUserData.add(lblSurname);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(0, 75, 148, 28);
		panelInsertUserData.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldSurname = new JTextField();
		textFieldSurname.setBounds(183, 75, 153, 28);
		panelInsertUserData.add(textFieldSurname);
		textFieldSurname.setColumns(10);
		
		lblAddress = new JLabel("Address");
		lblAddress.setBounds(0, 108, 148, 16);
		panelInsertUserData.add(lblAddress);
		
		lblNumber = new JLabel("Number");
		lblNumber.setBounds(183, 108, 153, 16);
		panelInsertUserData.add(lblNumber);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setBounds(0, 129, 148, 28);
		panelInsertUserData.add(textFieldAddress);
		textFieldAddress.setColumns(10);
		
		textFieldNumber = new JTextField();
		textFieldNumber.setBounds(183, 129, 153, 28);
		panelInsertUserData.add(textFieldNumber);
		textFieldNumber.setColumns(10);
		
		lblCity = new JLabel("City");
		lblCity.setBounds(0, 162, 148, 16);
		panelInsertUserData.add(lblCity);
		
		lblDistrict = new JLabel("District");
		lblDistrict.setBounds(183, 162, 153, 16);
		panelInsertUserData.add(lblDistrict);
		
		comboBoxCity = new JComboBox<String>();
		comboBoxCity.setBounds(0, 183, 148, 27);
		comboBoxCity.setModel(setComboBoxDummyText());
		comboBoxCity.addItem(NOT_SELECTABLE_OPTION);
		for(String s : cities) {
			comboBoxCity.addItem(s);
		}
		panelInsertUserData.add(comboBoxCity);
				
		comboBoxDistrict = new JComboBox<String>();
		comboBoxDistrict.setBounds(183, 183, 153, 27);
		comboBoxDistrict.setModel(setComboBoxDummyText());
		comboBoxDistrict.addItem(NOT_SELECTABLE_OPTION);
		panelInsertUserData.add(comboBoxDistrict);
		comboBoxCity.addActionListener(
				OpenStreetMapUtils.onSelectionSetSuburbsFromJSON(comboBoxCity, comboBoxDistrict)
		);
		
		lblEmail = new JLabel("e-mail");
		lblEmail.setBounds(0, 215, 148, 16);
		panelInsertUserData.add(lblEmail);
		
		textFieldMail = new JTextField();
		textFieldMail.setBounds(0, 236, 336, 28);
		panelInsertUserData.add(textFieldMail);
		textFieldMail.setColumns(10);
		
	}
	/**
	 * Gets the JTextField object of the userID.
	 * @return JTextField userID
	 */
	public JTextField getTextFieldUserID() {
		return textFieldUserID;
	}

	/**
	 * Sets the JTextField object of the userID.
	 * @param textFieldUserID It's the textField of UserID
	 */
	public void setTextFieldUserID(JTextField textFieldUserID) {
		this.textFieldUserID = textFieldUserID;
	}

	/**
	 * Gets the JTextField object of the user's name.
	 * @return JTextField It's the textField of the user's name
	 */
	public JTextField getTextFieldName() {
		return textFieldName;
	}

	/**
	 * Gets the JTextField object of the user's surname.
	 * @return JTextField It's the textField of the user's surname
	 */
	public JTextField getTextFieldSurname() {
		return textFieldSurname;
	}

	/**
	 * Gets the JTextField object of the user's address.
	 * @return JTextField It's the textField of the user's address
	 */
	public JTextField getTextFieldAddress() {
		return textFieldAddress;
	}

	/**
	 * Gets the JTextField object of the user's address number.
	 * @return JTextField It's the textField of the user's address number
	 */
	public JTextField getTextFieldNumber() {
		return textFieldNumber;
	}

	/**
	 * Gets the JTextField object of the user's email.
	 * @return JTextField It's the textField of the user's email
	 */
	public JTextField getTextFieldMail() {
		return textFieldMail;
	}

	/**
	 * Gets the JPasswordField object of the user's password.
	 * @return JPasswordField It's the passwordField of the user's password
	 */
	public JPasswordField getPasswordField() {
		return passwordField;
	}

	/**
	 * Gets the JPanel object of the user's data.
	 * @return JPanel
	 */
	public JPanel getPanelInsertUserData() {
		return panelInsertUserData;
	}
	
	/**
	 * Gets the JComboBox object which contains the cities list.
	 * @return JComboBox
	 */
	public JComboBox<String> getComboBoxCity() {
		return comboBoxCity;
	}

	/**
	 * Gets the JComboBox which contains the district list.
	 * @return JComboBox
	 */
	public JComboBox<String> getComboBoxDistrict() {
		return comboBoxDistrict;
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

	/**
	 * Set to empty all components in panel.
	 */
	public void clearFields() {
		for (Component C : panelInsertUserData.getComponents())
		{    
			if(C instanceof JTextField || C instanceof JTextArea) { ((JTextComponent) C).setText(""); }  
			if(C instanceof JComboBox) { 
				
				comboBoxCity.setModel(setComboBoxDummyText());
				comboBoxCity.addItem(NOT_SELECTABLE_OPTION);
				for(String s : cities) { comboBoxCity.addItem(s);}
				
				comboBoxDistrict.setModel(setComboBoxDummyText());
				comboBoxDistrict.addItem(NOT_SELECTABLE_OPTION);
			}
		}
	}
}