package view;


import interpreter.StrategyInterpreter;
import interpreter.Translator;
import interpreter.Translator.Command;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Border;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;

import main.Initialisation;

/**
 * A panel to store the button options
 * @author flanagdonn
 * TODO MAKE BUTTONS RESIZE PROPERLY
 */
public class ButtonPanel extends JPanel {

	private int height = 100;
	private int width = 300;

	private JButton inventory;
	private JButton team;
	private JButton exchange;
	private GameFrame containerFrame;


	private JButton loadGame = new JButton("Load saved game");
	private JButton newGame = new JButton("Start new game");

	private JButton loadPlayer = new JButton("Load saved player");
	private JButton createPlayer = new JButton("Create new player");

	private JButton client = new JButton("Client");
	private JButton serverclient = new JButton("Server + Client");

	private StrategyInterpreter initialisation;


	private WelcomePanel welcomePanel;
	private StrategyInterpreter buttonInterpreter;
	private Dialog dialog;
	/**
	 * The constructor stores the button interpreter to a field
	 * @param container
	 * @param boxLayout2
	 */
	public ButtonPanel(GameFrame container, StrategyInterpreter b, Translator.MainGameState state){
		buttonInterpreter = b;
		containerFrame = container;

		//make buttons layout top to bottom
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);	//display main game-play buttons horizontally
		setLayout(boxLayout);

		if(state.equals(Translator.MainGameState.MAIN)){
			if(containerFrame!=null){
				CreateMainButtons();
			}
			else{
				throw new IllegalArgumentException("The GameFrame hasn't been stored by the ButtonPanel");
			}
		}

	}

	/**
	 * Displays the sequence of button choices after and including playing as a client or playing as a client + server
	 * @param i An initialisation object, which implements StrategyInterpreter
	 * @param welcomeDialog The Dialog which needs to be informed of any choice that is made
	 * @param state Which buttons are to be displayed?
	 */
	public ButtonPanel(WelcomePanel welcomeDialog, Translator.InitialisationCommand state, Initialisation i) {

		setOpaque(false);

		this.welcomePanel = welcomeDialog;

		this.initialisation = i;

		//display options vertically
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(boxLayout);

		//display server or server+client buttons
		if(state.equals(Translator.InitialisationCommand.SHOW_CLIENT_SERVER_OPTION)){
			createServerClientButtons();
		}

		//display option to load a game or start a new game
		else if(state.equals(Translator.InitialisationCommand.SHOW_LOAD_OR_NEW_OPTION)){
			addLoadNewButtons();
		}

		//display option to either load a player or create a new player
		else if(state.equals(Translator.InitialisationCommand.LOAD_PLAYER_OR_CREATE_NEW_PLAYER)){
			addLoadCreatePlayerButtons();
		}

	}


	public ButtonPanel(Translator.Command state, StrategyInterpreter buttonInterp, Dialog d) {
		this.dialog = d;

		this.buttonInterpreter = buttonInterp;

		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(boxLayout);

		if(state.equals(Translator.Command.DISPLAY_INVENTORY_ITEM_OPTIONS)){
			displayInventoryItemOptions();
		}

		else if(state.equals(Translator.Command.DISPLAY_CONTAINER_ITEM_OPTIONS)){
			displayContainerItemOptions();
		}

	}

	//should only appear when something has been selected
	private void displayContainerItemOptions() {

		final JButton move = new JButton("Move to Inventory");
		move.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==move){
					try {
						buttonInterpreter.notify(Command.MOVE_ITEM_TO_INVENTORY.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					dialog.dispose();
				}
			}
		});

		add(move);
		makeButtonsPretty(move);
	}


	private void displayInventoryItemOptions() {
		final JButton drop = new JButton("Drop");
		final JButton use = new JButton("Use");
		final JButton moveToBag = new JButton("Move to bag");

		ActionListener itemActionListener = new ActionListener(){
			private boolean movePressed = false;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==drop){
					try {
						buttonInterpreter.notify(Translator.Command.DROP.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					dialog.dispose();	//no longer display inventory
				}
				else if(e.getSource()==use){
					try {
						buttonInterpreter.notify(Translator.Command.USE.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					dialog.dispose();	//no longer display inventory
				}
				else if(e.getSource()==moveToBag){
					try {
						buttonInterpreter.notify(Translator.Command.MOVE_ITEM.toString());
						//we know something is selected at this point, because the drop/use/move
						//options only appear after something is selected

						//only display dialog if this is the first time move item has been selected

						if(!movePressed){
							JOptionPane.showMessageDialog(ButtonPanel.this, "Now select where to move the item");
							moveToBag.setText("Move to this slot");
							movePressed = true;
						}
						else{
							moveToBag.setText("Move item to bag");
							movePressed = false;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		};

		drop.addActionListener(itemActionListener);
		moveToBag.addActionListener(itemActionListener);
		use.addActionListener(itemActionListener);

		makeButtonsPretty(drop, moveToBag, use);

		add(drop);
		add(use);
		add(moveToBag);
	}

	private void addLoadCreatePlayerButtons() {
		ActionListener loadcreate = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==loadPlayer){
					try {
						initialisation.notify(Translator.InitialisationCommand.LOAD_SAVED_PLAYER.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else if(e.getSource()==createPlayer){	//conditional not strictly necessary, but added for completion
					try {
						initialisation.notify(Translator.InitialisationCommand.CREATE_NEW_PLAYER.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		};

		loadPlayer.addActionListener(loadcreate);
		createPlayer.addActionListener(loadcreate);

		makeButtonsPretty(loadPlayer, createPlayer);

		removeAllButtons();



		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingVertical,0))); //pad between buttons
		add(loadPlayer);
		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingVertical,0))); //pad between buttons
		add(createPlayer);

		setVisible(true);

		repaint();

	}


	private int centerButtonsOnPanel(JButton... buttons) {
		int size = 0;

		for(JButton b: buttons){
			//add the width of this button to the total size
			size += b.getPreferredSize().width;
		}



		int panelWidth = welcomePanel.getPreferredSize().width;

		int padding = (panelWidth - size) /4;

		return padding;

	}

	private void addLoadNewButtons() {

		ActionListener loadnewListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==loadGame){
					try {
						initialisation.notify(Translator.InitialisationCommand.LOAD_GAME.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
				else if(e.getSource()==newGame){	//conditional not strictly necessary, but added for completion
					try {
						initialisation.notify(Translator.InitialisationCommand.SELECTED_NEW_GAME.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}

		};

		loadGame.addActionListener(loadnewListener);
		newGame.addActionListener(loadnewListener);

		removeAllButtons();

		makeButtonsPretty(loadGame, newGame);

		int extraPadding = centerButtonsOnPanel(loadGame, newGame);

		add(Box.createRigidArea(new Dimension(extraPadding,0)));
		add(loadGame);
		add(Box.createRigidArea(new Dimension(extraPadding,0)));
		add(newGame);


		setVisible(true);

		repaint();

	}

	/**
	 * Adds two buttons to the panel: A button to play as a Client, and
	 * another to play as Server/Client.
	 */
	private void createServerClientButtons() {

		client.setMnemonic(KeyEvent.VK_C);
		client.setToolTipText("Play as a client");


		serverclient.setMnemonic(KeyEvent.VK_S);
		serverclient.setToolTipText("Play as a server + client");


		ActionListener serverclientListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = "";
				if(e.getSource()==client){
					msg = Translator.InitialisationCommand.SELECTED_CLIENT.toString();
				}
				else if(e.getSource()==serverclient){	//conditional not strictly necessary, but added for completion
					msg = Translator.InitialisationCommand.SELECTED_CLIENT_AND_SERVER.toString();
				}

				try {
					initialisation.notify(msg);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};

		client.addActionListener(serverclientListener);
		serverclient.addActionListener(serverclientListener);

		makeButtonsPretty(client, serverclient);

		int extraPadding = centerButtonsOnPanel(client, serverclient);

		removeAllButtons();

		add(Box.createRigidArea(new Dimension(extraPadding,0)));
		add(client);
		add(Box.createRigidArea(new Dimension(extraPadding,0)));
		add(serverclient);

	}
	private void removeAllButtons() {
		remove(client);
		remove(serverclient);
		remove(loadGame);
		remove(newGame);
	}

	private void CreateMainButtons() {
		inventory = new JButton("Inventory");
		inventory.setMnemonic(KeyEvent.VK_I);
		inventory.setToolTipText("Display your inventory");
		inventory.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				try {
					buttonInterpreter.notify(Translator.Command.DISPLAY_INVENTORY.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});


		team = new JButton("Team");
		team.setMnemonic(KeyEvent.VK_T);
		team.setToolTipText("Display your team");
		team.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				String teamMember1 = randomTeamMember();
				String teamMember2 = randomTeamMember();
				while(teamMember1.equals(teamMember2)){
					teamMember2 = randomTeamMember();
				}
				JOptionPane.showMessageDialog(containerFrame,
						"Your team includes "+teamMember1+" and "+teamMember2,
						"Team",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		makeButtonsPretty(inventory, team);

		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingHorizontal,0))); //pad between buttons
		add(inventory);
		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingHorizontal,0))); //pad between buttons
		add(team);

		revalidate();

	}

protected String randomTeamMember() {
		ArrayList<String> teamMembers = new ArrayList<String>();
		teamMembers.add("Hercules Morse");
		teamMembers.add("Ronald McDonald");
		teamMembers.add("Bottomley Potts");
		teamMembers.add("Scarface Claw");
		teamMembers.add("Bitzer Maloney");
		teamMembers.add("Schnitzel von Krumm");

		int random = (int) (Math.random()*teamMembers.size());
		return teamMembers.get(random);
	}

/**
 * This method is used to turn an average button, into an AMAZING button!
 * @param buttons The buttons to be prettified
 */
	public static void makeButtonsPretty(JButton... buttons) {

		for(JButton b: buttons){
			javax.swing.border.Border line, raisedbevel, loweredbevel;
			TitledBorder title;
			javax.swing.border.Border empty;
			line = BorderFactory.createLineBorder(Color.black);
			raisedbevel = BorderFactory.createRaisedBevelBorder();
			loweredbevel = BorderFactory.createLoweredBevelBorder();
			title = BorderFactory.createTitledBorder("");
			empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
			final CompoundBorder compound, compound1, compound2;

			Color crl = GameFrame.col2;
			compound = BorderFactory.createCompoundBorder(empty, new OldRoundedBorderLine(crl));
			//			b.setFont(new Font("Sans-Serif", Font.BOLD, 16));
			//			b.setForeground(Color.darkGray);

			b.setPreferredSize(new Dimension(50, 30));

			b.setBorderPainted(true);
			b.setFocusPainted(false);
			b.setBorder(compound);

			b.revalidate();

			b.setForeground(GameFrame.buttonFontColor);
		}

	}

	/**
	 * This method is used to turn an average label, into an AMAZING label!
	 * @param labels The labels to be prettified
	 */
	public static void makeLabelPretty(JLabel... labels){

		for(JLabel label: labels){
			label.setFont(new Font("Serif", Font.BOLD, 14));
			label.setForeground(GameFrame.buttonFontColor);
		}
	}

	/**
	 * This method is used to turn an average radio button, into an AMAZING radio button!
	 * @param radioButtons The radio buttons to be prettified
	 */
	public static void makeRadioButtonPretty(JRadioButton... radioButtons){

		for(JRadioButton rbutton: radioButtons){
			rbutton.setFont(new Font("Serif", Font.BOLD, 14));
			rbutton.setForeground(GameFrame.buttonFontColor);
		}
	}

}
