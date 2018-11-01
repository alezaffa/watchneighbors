package com.uninsubria.watchneighbors.view;

import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.uninsubria.watchneighbors.controller.Watchneighbors;
import com.uninsubria.watchneighbors.model.Geoposition;
import com.uninsubria.watchneighbors.util.OpenStreetMapUtils;

/**
 * Allows the user to search city and district, and then shows them on the map.
 * @author alessandro, christian, denise, silvia 
 *
 */
public class PanelSearchViewMap {
	
	private JPanel panelSearchViewMap;
	private JButton btnSearchViewMap;
	private JComboBox<String> comboBoxViewMapCity;
	private JComboBox<String> comboBoxViewMapDistrict;
	private JLabel lblViewMapCity;
	private JLabel lblViewMapNeighborhood;
	protected static final String NOT_SELECTABLE_OPTION = "Select";
	
	/**
	 * Creates a new panel which contains the button named "Search" 
	 * and the comboboxes of the cities and the districts.
	 */
	public PanelSearchViewMap() {
		
		panelSearchViewMap = new JPanel();
		panelSearchViewMap.setBounds(0, 0, 588, 78);
		panelSearchViewMap.setBackground(Watchneighbors.COLOR);
		panelSearchViewMap.setLayout(null);
		
		btnSearchViewMap = new JButton("Search");
		btnSearchViewMap.setBounds(468, 20, 117, 29);
		panelSearchViewMap.add(btnSearchViewMap);
		
		comboBoxViewMapCity = new JComboBox<String>();
		comboBoxViewMapCity.setModel(setComboBoxDummyText());
		comboBoxViewMapCity.addItem(NOT_SELECTABLE_OPTION);
		for(String s : Geoposition.cities) { 
			comboBoxViewMapCity.addItem(s);
			}
		comboBoxViewMapCity.setBounds(30, 21, 134, 27);
		panelSearchViewMap.add(comboBoxViewMapCity);
		
		comboBoxViewMapDistrict = new JComboBox<String>();
		comboBoxViewMapDistrict.setBounds(203, 21, 134, 27);
		comboBoxViewMapDistrict.setModel(setComboBoxDummyText());
		comboBoxViewMapCity.addActionListener(
				OpenStreetMapUtils.onSelectionSetSuburbsFromJSON(comboBoxViewMapCity, comboBoxViewMapDistrict)
		);
		comboBoxViewMapDistrict.addItem(NOT_SELECTABLE_OPTION);
		panelSearchViewMap.add(comboBoxViewMapDistrict);
		
		lblViewMapCity = new JLabel("City");
		lblViewMapCity.setBounds(34, 6, 61, 16);
		panelSearchViewMap.add(lblViewMapCity);
		
		lblViewMapNeighborhood = new JLabel("District");
		lblViewMapNeighborhood.setBounds(203, 6, 134, 16);
		panelSearchViewMap.add(lblViewMapNeighborhood);
	
	}
	
	/**
	 * Returns a JPanel that contains a button named "Search" and the comboboxes of the cities and the districts.
	 * @return JPanel
	 */
	public JPanel getPanelSearchViewMap() {
		return panelSearchViewMap;
	}

	/**
	 * Returns a JButton named "Search" used for searching city and district choosen by the user.
	 * @return JButton
	 */
	public JButton getBtnSearchViewMap() {
		return btnSearchViewMap;
	}
	
	/**
	 * Returns a JComboBox that allows to select the city.
	 * @return JComboBox
	 */
	public JComboBox<String> getComboBoxViewMapCity() {
		return comboBoxViewMapCity;
	}

	/**
	 * Return a JComboBox that allows to select the district.
	 * @return JComboBox
	 */
	public JComboBox<String> getComboBoxViewMapDistrict() {
		return comboBoxViewMapDistrict;
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
		//Note: "this" should be the Container that directly contains your components
		//(most likely a JPanel).
		//This won't work if you call getComponents on the top-level frame.
		for (Component C : panelSearchViewMap.getComponents())
		{    
			if(C instanceof JTextField || C instanceof JTextArea) { ((JTextComponent) C).setText(""); }  
			if(C instanceof JComboBox) { 
				
				comboBoxViewMapCity.setModel(setComboBoxDummyText());
				comboBoxViewMapCity.addItem(NOT_SELECTABLE_OPTION);
				for(String s : PanelInsertUserData.cities) { comboBoxViewMapCity.addItem(s);}
				
				comboBoxViewMapDistrict.setModel(setComboBoxDummyText());
				comboBoxViewMapDistrict.addItem(NOT_SELECTABLE_OPTION);
			}
		}
	}
}
