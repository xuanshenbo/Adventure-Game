package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * A subclass of JDialog which welcomes a new player and invites them to choose an avatar
 */
public class WelcomeDialog extends JDialog implements ActionListener {

	private GameFrame parentFrame;

	private int heading1Size = 50;
	private int heading2Size = 30;

	private Dimension imageSize = new Dimension(850, 400);

	//static so as to be used in parent constructor
	private static String welcome = "Welcome to Adventure Game!";

	private String instructions = "If you wish to start a new game, please click OK, to choose an Avatar!";


	/**
	 * Creates a dialog with a message, and different behaviour depending on the state
	 * @param gameFrame The parent frame of the dialog
	 * @param title The title of the Dialog to be passed to super constructor
	 * @param msg Message to display
	 * @param i The state of the Game
	 */
	public WelcomeDialog(GameFrame gameFrame) {
		super(gameFrame, welcome, true);

		//setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		//setLayout(new BorderLayout());
		setLayout(new GridBagLayout());

		parentFrame = gameFrame;

		GridBagConstraints gc=new GridBagConstraints();
		gc.fill=GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;
		//gc.anchor = GridBagConstraints.PAGE_START;

		//The message is put in a panel, in case new messages will be added later
		JPanel messagePane = new JPanel();
		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.PAGE_AXIS));

		//display welcome message in appropriate size.
		JLabel welcomeMessage = new JLabel(welcome);
		welcomeMessage.setFont(new Font("Serif", Font.BOLD, heading1Size));

		messagePane.add(welcomeMessage);

		//add(messagePane, BorderLayout.PAGE_START);
		add(messagePane, gc);

		gc=new GridBagConstraints();
		gc.fill=GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 5;


		Image image = ImageLoader.loadImage("welcomeImage.jpg");
		image = image.getScaledInstance(imageSize.width, imageSize.height, -1);

		ImageIcon icon = new ImageIcon(image);
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		//add(thumb, BorderLayout.CENTER);
		add(thumb, gc);

		gc=new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 10;

		JButton chooseAvatar = new JButton("Choose my Avatar");
		chooseAvatar.addActionListener(this);
		//add(chooseAvatar, BorderLayout.EAST);
		add(chooseAvatar, gc);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//display the dialog
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}


	/**
	 * Called when the user clicks "Choose Avatar" on the dialog
	 */
	public void actionPerformed(ActionEvent e) {
		Dialog avatarDialog = new Dialog(parentFrame, "Avatar chooser", "These are your available options.", "avatars", parentFrame.getDialogInterpreter());
	}

	/**	 *
	 * @param c TextField to store in a field
	 */
	public void setTextField(JTextField c){

	}
}