package com.uninsubria.watchneighbors.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.openstreetmap.gui.jmapviewer.JMapViewerTree;

import com.uninsubria.watchneighbors.controller.Watchneighbors;
import com.uninsubria.watchneighbors.model.Event;

/**
 * Provides the graphic interface used for outcome management.
 * @author alessandro, christian, denise, silvia
 *
 */
public class Outcome extends Watchneighbors {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static final String NOT_SELECTABLE_OPTION = "Select";
	private static final String[] OUTCOMES = {"Police intervention", "False alarm"};
	private JLabel lblMessage;
	private JButton btnCancel;
	private JLabel lblOutcome;
	private JButton btnConfirmCloseEvent;
	private static Outcome outcomeFrame;
	
	/**
	 * Used to open a new window where the user can select the event outcome.
	 * @param selectedEvent The selected event
	 * @param treeMap The map
	 */
	public static void showFrame(final Event selectedEvent, final JMapViewerTree treeMap) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					outcomeFrame = new Outcome(selectedEvent);
					outcomeFrame.setVisible(true);
					outcomeFrame.setLocationRelativeTo(treeMap);
					outcomeFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create a new panel.
	 * @param event An event
	 */
	public Outcome(final Event event) {

		setBounds(100, 100, 450, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(COLOR);
		
		final JComboBox<String> comboOutcome = new JComboBox<String>();
		
		comboOutcome.setModel(new DefaultComboBoxModel<String>() {
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
		    });
		comboOutcome.addItem(NOT_SELECTABLE_OPTION);
		for(String s : OUTCOMES) {
			comboOutcome.addItem(s);
		}
		comboOutcome.setBounds(105, 111, 240, 27);
		contentPane.add(comboOutcome);
		
		lblOutcome = new JLabel("Outcome");
		lblOutcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutcome.setBounds(110, 88, 235, 16);
		contentPane.add(lblOutcome);
		
		btnConfirmCloseEvent = new JButton("OK");
		btnConfirmCloseEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String selectedItem = comboOutcome.getSelectedItem().toString();
					
					if(!selectedItem.equalsIgnoreCase("select")) {
						event.setOutcome(comboOutcome.getSelectedItem().toString());
						event.setDateCompletion(new Date());
						event.setStatus("CLOSED");
						
						cli.setOutcomeAndClose(event);
						
						outcomeFrame.dispose();
					}
					
				} catch(NoSuchObjectException nsoe) {
					JOptionPane.showMessageDialog(contentPane, "Connection error!", "Warning", JOptionPane.ERROR_MESSAGE);
				} catch (RemoteException re) {
					re.printStackTrace();
				}
			}
		});
		btnConfirmCloseEvent.setBounds(237, 194, 110, 47);
		contentPane.add(btnConfirmCloseEvent);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outcomeFrame.dispose();
			}
		});
		btnCancel.setBounds(115, 194, 110, 47);
		contentPane.add(btnCancel);
		
		lblMessage = new JLabel("");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setBounds(105, 287, 240, 16);
		contentPane.add(lblMessage);
	}
}