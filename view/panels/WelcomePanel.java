package view.panels;

import interpreter.Translator.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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

import dataStorage.Deserializer;
import view.frames.Dialog;
import view.frames.GameFrame;
import view.utilities.ImageLoader;
import main.Initialisation;
import main.Main;

/**
 * A subclass of JDialog which welcomes a new player and invites them to choose an avatar
 * @author flanagdonn
 */
public class WelcomePanel extends JPanel{

	private JFrame parentFrame;

	private int heading1Size = 50;

	//the image to be displayed on the opening welcome panel
	private Image welcomeImage;

	private InputPanel iPanel;

	private Initialisation initialisation;

	private Dimension imageSize = new Dimension(850, 400);

	//static so as to be used in parent constructor
	private static String welcome = "Game of Happiness!";

	//this will display different buttons depending on what the user needs to choose
	private ButtonPanel bPanel;

	private JPanel sliderPanel;

	//the state decides what to display
	private InitialisationCommand state;

	//how many trees the user wants
	private int density = 50;

	//0, 1 or 2 for easy, medium or hard
	private int difficultyLevel = 1;

	//width and height of game
	private int gameWidth = 50, gameHeight = 50;

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
		welcomeMessage.setForeground(GameFrame.FONT_COLOR);

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
			boolean isValid = Main.oldGame();
			if(!isValid){	//if cancel option selected
				transitionToNewState(InitialisationCommand.SHOW_LOAD_OR_NEW_OPTION);
			}
			else{
				displayAvatarOptions(true);
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
				loadingSavedPlayer ? load : create, initialisation);


		remove(bPanel); //remove the previous buttons

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

	/**
	 * The following starts the server in a server-client mode
	 */
	public void startServer() {
		String diffLevel = "";
		if(difficultyLevel == 0){
			diffLevel = "easy";
		}
		else if(difficultyLevel == 1){
			diffLevel = "medium";
		}
		else{
			diffLevel = "hard";
		}

		Main.serverClient(gameHeight, gameWidth, density, diffLevel);
	}

	//====================================================================
	//===================GETTERS AND SETTERS FOLLOW=======================
	//====================================================================

	/**
	 * @param c TextField to store in a field
	 */
	public void setTextField(JTextField c){

	}

	/**
	 * Set whether the user has entered a valid ip address
	 * @param b True if the input is valid
	 */
	public void setValidIP(boolean b) {
		this.validIP = b;

	}

	/**
	 * Sets the value chosen by the user, for use when creating game
	 * @param value The density level
	 */
	public void setDensity(int value) {
		density = value;
	}

	/**
	 * Sets the value chosen by the user, for use when creating game
	 * @param value The difficulty level
	 */
	public void setDifficultyLevel(int value) {
		difficultyLevel = value;
	}

	/**
	 * Sets the value chosen by the user, for use when creating game
	 * @param value The game height
	 */
	public void setGameHeight(int value) {
		gameHeight = value;
	}

	/**
	 * Sets the value chosen by the user, for use when creating game
	 * @param value The game width
	 */
	public void setGameWidth(int value) {
		gameWidth = value;
	}


}