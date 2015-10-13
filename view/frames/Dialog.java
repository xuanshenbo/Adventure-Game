package view.frames;

import interpreter.StrategyInterpreter;
import interpreter.Translator;
import interpreter.Translator.Command;
import interpreter.Translator.InitialisationCommand;

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
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import view.panels.ButtonPanel;
import view.panels.ContainerInventoryDisplayPanel;
import view.panels.WelcomePanel;
import view.styledComponents.HappinessButton;
import view.styledComponents.HappinessLabel;
import view.styledComponents.HappinessRadioButton;
import view.utilities.Avatar;
import main.Initialisation;
import model.items.Item;

/**
 * A subclass of JDialog which presents options to the player and displays warning messages *
 */
public class Dialog extends JDialog implements ActionListener {

	private GameFrame parentFrame;

	private Avatar chosenAvatar;

	private Translator.Command state;

	private StrategyInterpreter dialogInterpreter;

	private Initialisation initialisation;

	private WelcomePanel welcomePanel;

	private boolean loadingSavedPlayer;

	private ButtonPanel itemOptions;


	private InitialisationCommand initState;

	//The OK button is used on most dialogs
	private JButton ok = new JButton("OK");

	private JPanel messagePane;

	private HappinessLabel messageLabel;

	private boolean isYes;

	/**
	 * Creates a dialog with a message, and different behaviour depending on the state
	 * This constructor used for displaying inventory/container contents
	 * @param gameFrame The parent frame of the dialog
	 * @param title The title of the Dialog to be passed to super constructor
	 * @param msg Message to display
	 * @param i The state of the Game
	 */
	public Dialog(GameFrame gameFrame, String title, String msg, Translator.Command state, StrategyInterpreter dialogInterp) {
		super(gameFrame, title, true);

		this.state = state;

		this.dialogInterpreter = dialogInterp;

		getContentPane().setLayout( new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		parentFrame = gameFrame;

		messagePane = new JPanel();
		messageLabel = new HappinessLabel(msg);
		messagePane.add(messageLabel);
		getContentPane().add(messagePane);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		if(state.equals(Translator.Command.DISPLAY_INVENTORY)){
			displayInventory();
		}

		else if(state.equals(Translator.Command.DISPLAY_CONTAINER)){
			displayContainer();
		}

		addOKButton();

		displayDialog();
	}

	/**
	 * This constructor used to display Avatar Options
	 * @param title The title of the dialog
	 * @param msg The message to be displayed to the user
	 * @param state The state: either CREATE_NEW_PLAYER or LOAD_SAVED_PLAYER
	 * @param i The Initialisation interpreter
	 * @param wPanel The welcome panel on which this dialog lives
	 */
	public Dialog(String title, String msg, Translator.InitialisationCommand state, Initialisation i, WelcomePanel wPanel) {

		this.initialisation = i;
		this.welcomePanel = wPanel;
		getContentPane().setLayout( new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		addMessage(msg);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		if(state.equals(Translator.InitialisationCommand.CREATE_NEW_PLAYER)){
			this.state = Translator.Command.DISPLAY_AVATAR_OPTIONS;
			displayAvatarOptions(false);
		}
		else if(state.equals(Translator.InitialisationCommand.LOAD_SAVED_PLAYER)){
			this.state = Translator.Command.DISPLAY_AVATAR_OPTIONS;
			displayAvatarOptions(true);
		}

		addOKButton();

		displayDialog();

	}

	/**
	 * For displaying messages
	 * @param state
	 * @param message
	 * @param container
	 */
	public Dialog(Command state, String message){

		this.state = state;

		System.out.println(state);

		addMessage(message);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		if(state.equals(Command.NOTIFY_USER_OF_MESSAGE)){
			addOKButton();
		}

		displayDialog();

	}

	private void addMessage(String message) {

		messagePane = new JPanel();
		messageLabel = new HappinessLabel(message);

		messagePane.add(messageLabel);
		add(messagePane);
	}

	//add a confirm button
	private void addOKButton() {
		ok = new HappinessButton("OK");
		ok.addActionListener(this);
		ok.setMnemonic(KeyEvent.VK_ENTER);	//TODO fix this
		add(ok);
	}

	private void displayDialog() {
		//display the dialog
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}


	private void displayAvatarOptions(boolean b) {

		this.loadingSavedPlayer = b;

		JPanel avatarOptions = new JPanel();
		List<Avatar> availAvatars= initialisation.getAvailableAvatars();

		ButtonGroup group = new ButtonGroup();

		List<JRadioButton> buttons = new ArrayList<JRadioButton>();

		for(final Avatar a: availAvatars){
			System.out.println("Dialog 186: "+a.toString());//debug
			HappinessRadioButton avatar = new HappinessRadioButton(a.toString());

			ItemListener radioListener = new ItemListener(){
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==1) { //then checked
						chosenAvatar = a;
					}
				}
			};

			avatar.addItemListener(radioListener);
			group.add(avatar);
			avatarOptions.add(avatar);
		}
		add(avatarOptions);

	}


	/*
	 * Retrieves the inventory from the GameFrame and displays a String description of each item on screen.
	 */
	private void displayInventory() {
		ContainerInventoryDisplayPanel radioPanel = new ContainerInventoryDisplayPanel(parentFrame.getInventoryContents(),
				parentFrame.getRadioInterpreter(), this, state);
		add(radioPanel);
		revalidate();
	}

	/*
	 * Retrieves the container contents from the GameFrame and displays a String description of each item on screen.
	 */
	private void displayContainer() {
		ContainerInventoryDisplayPanel radioPanel = new ContainerInventoryDisplayPanel(parentFrame.getContainerContents(),
				parentFrame.getRadioInterpreter(), this, state);
		add(radioPanel);
		revalidate();

	}

	/**
	 * Called when the user clicks OK on the dialog
	 * Notifies different interpreters depending on what state the game is in.
	 */
	public void actionPerformed(ActionEvent e) {
		boolean validInput = false;

		if(state.equals(Translator.Command.DISPLAY_INVENTORY) || state.equals(Translator.Command.DISPLAY_CONTAINER)){
			//notify that inventory closed?
			validInput = true;
			initState = null;
		}

		if(state.equals(Translator.Command.DISPLAY_AVATAR_OPTIONS)){
			if(chosenAvatar != null){
				try {
					initialisation.notify(chosenAvatar.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				validInput = true;
			}
		}

		if(validInput){
			setVisible(false);
			dispose();
			if(parentFrame != null) parentFrame.setVisible(true);
		}
	}

	/**
	 * @param c TextField to store in a field
	 */
	public void setTextField(JTextField c){

	}

	/*
	 * Display the item options, with a different button panel depending on whether this is an inventory or container
	 */
	public void displayItemOptions(boolean isInventory) {
		if(isInventory){
			this.itemOptions = new ButtonPanel(Command.DISPLAY_INVENTORY_ITEM_OPTIONS, parentFrame.getButtonInterpreter(), this);
		}
		else{
			this.itemOptions = new ButtonPanel(Command.DISPLAY_CONTAINER_ITEM_OPTIONS, parentFrame.getButtonInterpreter(), this);
		}
		remove(ok);
		add(itemOptions);

		displayDialog();

	}


}