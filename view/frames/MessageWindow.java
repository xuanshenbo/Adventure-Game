package view.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import interpreter.Translator.Command;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.styledComponents.HappinessButton;
import view.styledComponents.HappinessLabel;

/**
 * This displays a window to notify the user of something.
 * It has the theme of the game
 * @author flanagdonn
 *
 */
public class MessageWindow extends JFrame {

	private HappinessLabel messageLabel;

	private HappinessButton ok = new HappinessButton("OK");

	private Dialog dialog;

	private static String title = "Happiness Game";

	private JPanel buttonPanel = new JPanel();

	/**
	 * This constructor creates a message window for use
	 * in conjunction with a display inventory dialog
	 * @param msg The message to display
	 * @param d The dialog displaying the inventory
	 */
	public MessageWindow(String msg, Dialog d) {
		super(title);

		dialog = d;

		buttonPanel = new JPanel(new BorderLayout());

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		getContentPane().setLayout(new BorderLayout());

		messageLabel = new HappinessLabel(msg);

		//add(messageLabel, BorderLayout.NORTH);
		add(messageLabel);
		//add(buttonPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		addOKButton();

		displayWindow();

	}

/*	*//**
	 * This constructor creates a message window for use
	 * in any circumstance where the user needs to be notified
	 * of something.
	 *  @param msg The message to display to the user
	 *//*
	public MessageWindow(String msg) {
		super(title);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		messageLabel = new HappinessLabel(msg);

		//add(messageLabel, BorderLayout.NORTH);
		add(messageLabel);

		addOKButton();

		displayWindow();

	}*/

	private void displayWindow() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	//add a confirm button
	private void addOKButton() {
		ok = new HappinessButton("OK");

		// If this window is being used with the inventory/container dialog
		if(dialog != null){
			ok.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					//make the dialog visible again, so that you can select where to move the item
					dialog.setVisible(true);
					setVisible(false);
				}

			});
		}

		// else if this is just a message to display to the user
		// simply close the window when the ok button is presses
		else {
			ok.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}

			});
		}

		ok.setMnemonic(KeyEvent.VK_ENTER);

		int padding = (messageLabel.getPreferredSize().width - ok.getPreferredSize().width) /2;
		padding = GameFrame.BUTTON_PADDING_HORIZONTAL;

		buttonPanel.add(Box.createRigidArea(new Dimension(padding, 30)));

		ok.setSize(new Dimension(50, 30));

		buttonPanel.add(ok);
		buttonPanel.add(Box.createRigidArea(new Dimension(padding, 30)));

	}

	public static void main(String[] args){
		new MessageWindow("Testing", null);
	}

}
