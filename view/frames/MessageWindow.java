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

	private HappinessButton ok;

	private GameFrame gameFrame;

	private JPanel messagePanel;

	private String msg;

	private Dialog dialog;

	private static String title = "Happiness Game";

	private JPanel buttonPanel;

	/**
	 * This constructor creates a message window for use
	 * in conjunction with a display inventory dialog
	 * @param msg The message to display
	 * @param d The dialog displaying the inventory
	 */
	public MessageWindow(String msg, Dialog d, GameFrame g) {
		super(title);

		this.gameFrame = g;

		this.msg = msg;

		dialog = d;

		setUpWindow();

		displayWindow();

	}

	private void setUpWindow() {
		setLayout(new BorderLayout());

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		createMessageLabel();

		createOKButton();

		add(messagePanel, BorderLayout.NORTH);

		add(buttonPanel, BorderLayout.CENTER);

	}

	private void createMessageLabel() {

		messagePanel = new JPanel();

		messageLabel = new HappinessLabel(msg);

		messagePanel.add(messageLabel);

	}

	private void displayWindow() {
		pack();
		if(gameFrame == null){
			setLocationRelativeTo(dialog);
		}
		else{
			setLocationRelativeTo(gameFrame);
		}
		setVisible(true);
	}

	//add a confirm button
	private void createOKButton() {
		buttonPanel = new JPanel();

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

		int padding = GameFrame.BUTTON_PADDING_HORIZONTAL;

		buttonPanel.add(Box.createRigidArea(new Dimension(padding, 0)));

		buttonPanel.add(ok);
		buttonPanel.add(Box.createRigidArea(new Dimension(padding, 0)));

	}
}
