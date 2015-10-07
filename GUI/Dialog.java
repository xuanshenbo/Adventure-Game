package GUI;

import interpreter.StrategyInterpreter;

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
import main.InitialisationState;
import main.MainGameState;
import model.items.Item;

/**
 * A subclass of JDialog which presents options to the player and displays warning messages *
 */
public class Dialog extends JDialog implements ActionListener {

	private GameFrame parentFrame;

	private Avatar chosenAvatar;

	private MainGameState state;

	private StrategyInterpreter dialogInterpreter;

	private Initialisation initialisation;

	private WelcomePanel welcomePanel;

	private boolean loadingSavedPlayer;



	/**
	 * Creates a dialog with a message, and different behaviour depending on the state
	 * @param gameFrame The parent frame of the dialog
	 * @param title The title of the Dialog to be


			String testing1 = inventoryContents.get(i);
			JLabel testing2 = jlabels.get(testing1);passed to super constructor
	 * @param msg Message to display
	 * @param i The state of the Game
	 */
	public Dialog(GameFrame gameFrame, String title, String msg, MainGameState state, StrategyInterpreter dialogInterp) {
		super(gameFrame, title, true);

		this.state = state;

		this.dialogInterpreter = dialogInterp;

		getContentPane().setLayout( new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		parentFrame = gameFrame;

		JPanel messagePane = new JPanel();
		messagePane.add(new JLabel(msg));
		getContentPane().add(messagePane);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		if(state.equals(MainGameState.DISPLAY_INVENTORY)){
			displayInventory();
		}

		JButton ok = new JButton("OK");
		ok.addActionListener(this);
		add(ok);

		//display the dialog
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}


	public Dialog(String title, String msg, InitialisationState state, Initialisation i, WelcomePanel wPanel) {
		this.initialisation = i;
		this.welcomePanel = wPanel;
		getContentPane().setLayout( new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		JPanel messagePane = new JPanel();

		messagePane.add(new JLabel(msg));
		getContentPane().add(messagePane);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		if(state.equals(InitialisationState.CREATE_NEW_PLAYER)){
			displayAvatarOptions(false);
		}
		else if(state.equals(InitialisationState.LOAD_SAVED_PLAYER)){
			displayAvatarOptions(true);
		}

		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				welcomePanel.transitionToNewState(InitialisationState.START_GAME);
				Dialog.this.dispose();	//get rid of the dialog
			}

		});

		add(ok);

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
			JRadioButton avatar = new JRadioButton(a.toString());

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
		RadioButtonPanel radioPanel = new RadioButtonPanel(parentFrame.getInventoryContents(), parentFrame.getRadioInterpreter());
		add(radioPanel);
	}

	/**
	 * Called when the user clicks OK on the dialog
	 * Notifies different interpreters depending on what state the game is in.
	 */
	public void actionPerformed(ActionEvent e) {
		boolean validInput = false;
		if(state.equals(MainGameState.DISPLAY_INVENTORY)){
			validInput = true;
		}

		if(state.equals("")){
			try {
				dialogInterpreter.notify(chosenAvatar.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			validInput = true;
		}

		if(validInput){
			setVisible(false);
			dispose();
			parentFrame.setVisible(true);
		}
	}

	/**	 *
	 * @param c TextField to store in a field
	 */
	public void setTextField(JTextField c){

	}
}