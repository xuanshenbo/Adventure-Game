package view;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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



	/**
	 * Creates a dialog with a message, and different behaviour depending on the state
	 * @param gameFrame The parent frame of the dialog
	 * @param title The title of the Dialog to be


			String testing1 = inventoryContents.get(i);
			JLabel testing2 = jlabels.get(testing1);passed to super constructor
	 * @param msg Message to display
	 * @param i The state of the Game
	 */
	public Dialog(GameFrame gameFrame, String title, String msg, Translator.Command state, StrategyInterpreter dialogInterp) {
		super(gameFrame, title, true);

		this.state = state;

		this.dialogInterpreter = dialogInterp;

		getContentPane().setLayout( new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		parentFrame = gameFrame;

		JPanel messagePane = new JPanel();
		JLabel messageLabel = new JLabel(msg);
		ButtonPanel.makeLabelPretty(messageLabel);
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





	private void addOKButton() {
		ok = new JButton("OK");
		ButtonPanel.makeButtonsPretty(ok);
		ok.addActionListener(this);
	//	ok.setMnemonic(KeyEvent.VK_ENTER);	//TODO fix this
		add(ok);
	}





	public Dialog(String title, String msg, Translator.InitialisationCommand state, Initialisation i, WelcomePanel wPanel) {

		this.initialisation = i;
		this.welcomePanel = wPanel;
		getContentPane().setLayout( new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		JPanel messagePane = new JPanel();

		JLabel messageLabel = new JLabel(msg);
		ButtonPanel.makeLabelPretty(messageLabel);
		messagePane.add(messageLabel);

		getContentPane().add(messagePane);

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





	private void displayDialog() {
		//display the dialog
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}


	private void displayAvatarOptions(boolean b) {

		this.loadingSavedPlayer = b;

		//TODO use RadioButtonPanel instead?
		JPanel avatarOptions = new JPanel();
		List<Avatar> availAvatars= initialisation.getAvailableAvatars();

		ButtonGroup group = new ButtonGroup();

		List<JRadioButton> buttons = new ArrayList<JRadioButton>();

		for(final Avatar a: availAvatars){
			JRadioButton avatar = new JRadioButton(a.toString());

			ButtonPanel.makeRadioButtonPretty(avatar);

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
	 * Add pictures
	 */
	private void displayInventory() {
		RadioButtonPanel radioPanel = new RadioButtonPanel(parentFrame.getInventoryContents(),
				parentFrame.getRadioInterpreter(), this, state);
		add(radioPanel);
		revalidate();
	}

	private void displayContainer() {
		RadioButtonPanel radioPanel = new RadioButtonPanel(parentFrame.getContainerContents(),
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