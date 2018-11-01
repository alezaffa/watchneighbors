package com.uninsubria.watchneighbors.view;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.uninsubria.watchneighbors.controller.Watchneighbors;

/**
 * Shows the data of the logged user and allows him to modify his information.
 * @author alessandro, christian, denise, silvia
 *
 */
public class PanelModify {
	
	private JPanel panelModifyDeleteCancel;
	private JButton btnConfirm;
	private JButton btnDelete;
	private JButton btnCancel;

	public PanelModify() {
		
		panelModifyDeleteCancel = new JPanel();
		panelModifyDeleteCancel.setBounds(457, 40, 197, 287);
		panelModifyDeleteCancel.setBackground(Watchneighbors.COLOR);
		panelModifyDeleteCancel.setLayout(null);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(0, 0, 197, 48);
		panelModifyDeleteCancel.add(btnConfirm);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(0, 119, 197, 48);
		panelModifyDeleteCancel.add(btnDelete);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(0, 239, 197, 48);
		panelModifyDeleteCancel.add(btnCancel);
		
	}

	/**
	 * Returns a JPanel object that contains the data of the logged user
	 * and shows the buttons named "Confirm", "Delete" and "Cancel".
	 * @return JPanel
	 */
	public JPanel getPanelModifyDeleteCancel() {
		return panelModifyDeleteCancel;
	}

	/**
	 * Returns a JButton named "Confirm" used for confirm changes.
	 * @return JButton
	 */
	public JButton getBtnConfirm() {
		return btnConfirm;
	}

	/**
	 * Returns a JButton named "Delete" used for delete the account of the logged user.
	 * @return JButton
	 */
	public JButton getBtnDelete() {
		return btnDelete;
	}

	/**
	 * Returns a JButton named "Cancel" used to go back. 
	 * @return JButton
	 */
	public JButton getBtnCancel() {
		return btnCancel;
	}

}