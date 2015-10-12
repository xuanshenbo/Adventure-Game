package view;

import interpreter.Translator.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.Initialisation;

/**
 * A subclass of JDialog which welcomes a new player and invites them to choose an avatar
 */
public class WelcomePanel extends JPanel{

	private JFrame parentFrame;

	private int heading1Size = 50;
	private int heading2Size = 30;
	private GridBagConstraints buttonPanelConstraints;
	private GridBagConstraints sliderPanelConstraints;

	//the image to be displayed on the opening welcome panel
	private Image welcomeImage;

	private InputPanel iPanel;

	private Initialisation initialisation;

	private Dimension imageSize = new Dimension(850, 400);

	//static so as to be used in parent constructor
	private static String welcome = "The Happiness Game!";

	//this will display different buttons depending on what the user needs to choose
	private ButtonPanel bPanel;

	private JPanel sliderPanel;

	//the state decides what to display
	private InitialisationCommand state;

	private String instructions = "If you wish to start a new game, please click OK, to choose an Avatar!";

	//how many trees the user wants
	private int density = 50;

	//0, 1 or 2 for easy, medium or hard
	private int difficultyLevel = 1;

	//width and height of game
	private int gameWidth = 50, gameHeight = 50;

	private InitialisationCommand initState;

	//has the user entered a sensible ip?
	private boolean validIP = true;

	/**
	 * Creates a dialog with a message, and different behaviour depending on the state
	 * @param gameFrame The parent frame of the dialog
	 * @param title The title of the Dialog to be passed to super constructor
	 * @param msg Message to display
	 * @param i The state of the Game
	 */
	public WelcomePanel(Initialisation i) {

		this.initialisation = i;

		this.state = InitialisationCommand.SHOW_CLIENT_SERVER_OPTION;

		setLayout(new BorderLayout());

		this.parentFrame = i.getFrame();

		//The message is put in a panel, in case new messages will be added later
		//		JPanel messagePane = new JPanel();
		//		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.PAGE_AXIS));

		//display welcome message in appropriate size, with the text centered
		JLabel welcomeMessage = new JLabel(welcome, SwingConstants.CENTER);
		welcomeMessage.setFont(new Font("Serif", Font.BOLD, heading1Size));
		welcomeMessage.setForeground(GameFrame.fontColor);

		//add the welcome message to the panel
		add(welcomeMessage, BorderLayout.NORTH);

		//create the cupcake image and put on jlabel
		welcomeImage = ImageLoader.loadImage("cupcake.png");
		welcomeImage = welcomeImage.getScaledInstance(imageSize.width, imageSize.height, -1);
		ImageIcon icon = new ImageIcon(welcomeImage);
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);

		//add the cupcake image to panel
		add(thumb, BorderLayout.CENTER);

		//create a button panel and add to this panel
		bPanel = new ButtonPanel(this, state, initialisation);
		add(bPanel, BorderLayout.SOUTH);

		//display the welcome panel
		setVisible(true);
	}

	/**
	 * This method takes care of the flow of logic when a user first enters the game.
	 * @param state The new state
	 */
	public void transitionToNewState(InitialisationCommand state){
		bPanel.setVisible(false);
		this.initState = state;

		switch(state){

		case SHOW_LOAD_OR_NEW_OPTION:
			displayLoadNew();
			break;

		case CONNECT_TO_SERVER:
			displayConnect();
			break;

		case LOAD_PLAYER_OR_CREATE_NEW_PLAYER:
			displayLoadCreatePlayerOptions();
			break;

		case LOAD_GAME:
			if (!loadSavedGame()){	//if they cancelled the load option
				transitionToNewState(InitialisationCommand.SHOW_LOAD_OR_NEW_OPTION);
			}
			break;

		case CHOOSE_SLIDER_OPTIONS:
			displaySliderOptions();
			break;

		case LOAD_SAVED_PLAYER:
			displayAvatarOptions(true);
			break;

		case CREATE_NEW_PLAYER:
			displayAvatarOptions(false);
			break;

		case START_GAME:
			try {
				initialisation.notify(InitialisationCommand.START_GAME.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		}

		//resize panel to make room for any newly added panels
		revalidate();
		repaint();

	}

	/*
	 * TODO Do we still need this option?
	 */
	private void displayLoadCreatePlayerOptions() {
		remove(bPanel);
		bPanel = new ButtonPanel(this, InitialisationCommand.LOAD_PLAYER_OR_CREATE_NEW_PLAYER, initialisation);
		add(bPanel, BorderLayout.SOUTH);

	}

	private boolean loadSavedGame() {
		boolean hasSelected = false;

		//Create a file chooser
		final JFileChooser fc = new JFileChooser();

		int returnVal = fc.showOpenDialog(WelcomePanel.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if(file != null){
				hasSelected = true;
			}
			try {
				initialisation.notify("open "+file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return hasSelected;
	}

	private void displaySliderOptions() {
		remove(bPanel);

		//initialise the slider panel with a vertical box layout
		sliderPanel = new SliderPanel(this);

		add(sliderPanel, BorderLayout.SOUTH);

	}

	/*
	 * Displays option dialog to get user input on which server to connect to
	 */
	private void displayConnect() {
		if(iPanel != null){
			remove(iPanel); //remove old ipanel if need to redisplay it now
		}
		iPanel = new InputPanel(initialisation, InitialisationCommand.CONNECT_TO_SERVER, validIP);
		add(iPanel, BorderLayout.SOUTH);

		revalidate();
	}

	/*
	 * Displays option dialog to get user to decide to load a game, or start a new game
	 * TODO why is this panel not being displayed?
	 */
	private void displayLoadNew() {
		remove(bPanel);
		bPanel = new ButtonPanel(this, InitialisationCommand.SHOW_LOAD_OR_NEW_OPTION, initialisation);
		add(bPanel, BorderLayout.SOUTH);
	}

	/*
	 * Displays option dialog to get user to choose Avatar
	 */
	private void displayAvatarOptions(boolean b) {
		final boolean loadingSavedPlayer = b;
		final String loadSavedMessage = "Select the avatar associated with your Player";
		final String createNewMessage = "Choose an avatar for your Player";

		final InitialisationCommand create = InitialisationCommand.CREATE_NEW_PLAYER;
		final InitialisationCommand load = InitialisationCommand.LOAD_SAVED_PLAYER;

		Dialog avatarDialog = new Dialog("Available Avatars", loadingSavedPlayer ? loadSavedMessage : createNewMessage,
				loadingSavedPlayer ? load : create, initialisation, WelcomePanel.this);


		remove(bPanel); //remove the previous buttons

	}

	/**
	 * @param c TextField to store in a field
	 */
	public void setTextField(JTextField c){

	}

	public void setValidIP(boolean b) {
		this.validIP = b;

	}

	public void setDensity(int value) {
		density = value;
	}

	public void setDifficultyLevel(int value) {
		difficultyLevel = value;
	}

	public void setGameHeight(int value) {
		gameHeight = value;
	}

	public void setGameWidth(int value) {
		gameWidth = value;
	}

	public void removeSliderPanel() {
		remove(sliderPanel);
	}

	public void notifyParameters() {
		try {
			initialisation.notify("parameters "+gameHeight+" "+gameWidth+" "+difficultyLevel+" "+density);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}