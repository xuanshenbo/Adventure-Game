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
	private GridBagConstraints buttonPanelConstraints;

	private Dimension imageSize = new Dimension(850, 400);

	//static so as to be used in parent constructor
	private static String welcome = "Welcome to Adventure Game!";

	//this will display different buttons depending on what the user needs to choose
	private ButtonPanel bPanel;

	private InitialisationStates state;

	private String instructions = "If you wish to start a new game, please click OK, to choose an Avatar!";


	/**
	 * Creates a dialog with a message, and different behaviour depending on the state
	 * @param gameFrame The parent frame of the dialog
	 * @param title The title of the Dialog to be passed to super constructor
	 * @param msg Message to display
	 * @param i The state of the Game
	 */
	public WelcomeDialog() {		//TODO Felix to create an Initialisation Object
		setLayout(new GridBagLayout());

		GridBagConstraints gc=new GridBagConstraints();
		gc.fill=GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;

		//The message is put in a panel, in case new messages will be added later
		JPanel messagePane = new JPanel();
		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.PAGE_AXIS));

		//display welcome message in appropriate size.
		JLabel welcomeMessage = new JLabel(welcome);
		welcomeMessage.setFont(new Font("Serif", Font.BOLD, heading1Size));

		messagePane.add(welcomeMessage);

		//add(messagePane, BorderLayout.PAGE_START);
		add(messagePane, gc);

		addWelcomeImage();

		buttonPanelConstraints=new GridBagConstraints();
		buttonPanelConstraints.gridx = 0;
		buttonPanelConstraints.gridy = 10;

		//button panel needs to store "this" to call the display next methods, and send it Initialisation too? Or Initialisation
		//has access to buttonInterpreter?
		ButtonPanel b = new ButtonPanel(this, "serverClient");
		add(b, buttonPanelConstraints);

		//display the dialog
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addWelcomeImage() {
		GridBagConstraints gc=new GridBagConstraints();
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

	}

	public void displayNext(String s){
		if(s.equals("loadNew")){
			displayLoadNew();
		}
		else if(s.equals("connect")){
			displayConnect();
		}
		else if(s.equals("newGame")){
			displayNewGameOptions();
		}
	}

	/*
	 * Displays option dialog to get user input on which server to connect to
	 */
	private void displayConnect() {
		// TODO Auto-generated method stub

	}

	/*
	 * Displays option dialog to get user to decide to load a game, or start a new game
	 */
	private void displayLoadNew() {
		// TODO Auto-generated method stub

	}

	/*
	 * Displays option dialog to get user to choose Avatar
	 */
	private void displayNewGameOptions() {
		JButton chooseAvatar = new JButton("Choose my Avatar");
		chooseAvatar.addActionListener(this);
		add(chooseAvatar, buttonPanelConstraints);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}
	/**
	 * Called when the user clicks "Choose Avatar" on the dialog
	 */
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
		dispose();
		if(this.state.equals(InitialisationStates.NEW_GAME)){
			Dialog avatarDialog = new Dialog(parentFrame, "Avatar chooser", "These are your available options.", "avatars", parentFrame.getDialogInterpreter());
		}
	}

	/**	 *
	 * @param c TextField to store in a field
	 */
	public void setTextField(JTextField c){

	}
}