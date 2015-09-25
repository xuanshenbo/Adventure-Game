package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import state.Item;

/**
 * A subclass of JDialog which presents options to the player and displays warning messages *
 */
public class Dialog extends JDialog implements ActionListener {
	private GameFrame parentFrame;


	/**
	 * Creates a dialog with a message, and different behaviour depending on the state
	 * @param parent The parent frame of the dialog
	 * @param title The title of the Dialog to be passed to super constructor
	 * @param msg Message to display
	 * @param i The state of the Game
	 */
	public Dialog(GameFrame parent, String title, String msg, String state) {
		super(parent, title, true);

		getContentPane().setLayout( new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		parentFrame = parent;

		JPanel messagePane = new JPanel();
		messagePane.add(new JLabel(msg));
		getContentPane().add(messagePane);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		if(state.equals("inventory")){
			displayInventory();
		}

		//display the dialog
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}


	/*
	 * Retrieves the inventory from the GameFrame and displays a String description of each item on screen.
	 * Add pictures
	 */
	private void displayInventory() {
		if(parentFrame.getInventoryContents()!=null){

			for(Item i: parentFrame.getInventoryContents()){

				JLabel item = new JLabel(i.getDescription());
				Image image= i.getPicture();
				ImageIcon icon = new ImageIcon(image);
				item.setIcon(icon);


				add(item);

			}
		}

	}


	/**
	 * Called when the user clicks OK on the dialog
	 * Notifies different interpreters depending on what state the game is in.
	 */
	public void actionPerformed(ActionEvent e) {

	}

	/**	 *
	 * @param c TextField to store in a field
	 */
	public void setTextField(JTextField c){

	}
}