package GUI;


import interpreter.StrategyInterpreter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Border;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;

import main.Initialisation;
import main.InitialisationState;
import main.MainGameState;

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
	/**
	 * The constructor stores the button interpreter to a field
	 * @param container
	 * @param boxLayout2
	 */
	public ButtonPanel(GameFrame container, StrategyInterpreter b, MainGameState state){
		buttonInterpreter = b;
		containerFrame = container;
		//make buttons layout top to bottom


		if(state.equals(MainGameState.MAIN)){
			if(containerFrame!=null){
				BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);	//display main game-play buttons horizontally
				setLayout(boxLayout);
				CreateMainButtons();
			}
			else{
				throw new IllegalArgumentException("The GameFrame hasn't been stored by the ButtonPanel");
			}
		}
	}

	/**
	 * Displays the button choices for playing as a client or playing as a client + server
	 * @param i An initialisation object, which implements StrategyInterpreter
	 * @param welcomeDialog The Dialog which needs to be informed of any choice that is made
	 * @param state Which buttons are to be displayed?
	 */
	public ButtonPanel(WelcomePanel welcomeDialog, InitialisationState state, Initialisation i) {

		this.welcomePanel = welcomeDialog;

		this.initialisation = i;

		//display server or server+client buttons
		if(state.equals(InitialisationState.SHOW_CLIENT_SERVER_OPTION)){
			BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS); //display client server buttons vertically
			setLayout(boxLayout);
			createServerClientButtons();
		}

		//display option to load a game or start a new game
		else if(state.equals(InitialisationState.SHOW_LOAD_OR_NEW_OPTION)){
			BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS); //display client server buttons vertically
			setLayout(boxLayout);
			addLoadNewButtons();
		}

		else if(state.equals(InitialisationState.LOAD_PLAYER_OR_CREATE_NEW_PLAYER)){
			BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS); //display client server buttons vertically
			setLayout(boxLayout);
			addLoadCreatePlayerButtons();
		}





	}

	public ButtonPanel(MainGameState state, StrategyInterpreter buttonInterp) {
		this.buttonInterpreter = buttonInterp;

		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);	//display main game-play buttons horizontally
		setLayout(boxLayout);

		if(state.equals(MainGameState.DISPLAY_ITEM_OPTIONS)){
			displayItemOptions();
		}
	}

	private void displayItemOptions() {
		final JButton drop = new JButton("Drop item");
		final JButton use = new JButton("Use item");
		final JButton moveToBag = new JButton("Move item to bag");

		ActionListener itemActionListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==drop){
					try {
						buttonInterpreter.notify("item drop");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else if(e.getSource()==use){
					try {
						buttonInterpreter.notify("item use");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else if(e.getSource()==moveToBag){
					try {
						buttonInterpreter.notify("item moveToBag");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		};

		drop.addActionListener(itemActionListener);
		moveToBag.addActionListener(itemActionListener);
		use.addActionListener(itemActionListener);

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
						initialisation.notify("loadPlayer");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					welcomePanel.transitionToNewState(InitialisationState.LOAD_SAVED_PLAYER);
				}
				else if(e.getSource()==createPlayer){	//conditional not strictly necessary, but added for completion
					try {
						initialisation.notify("createPlayer");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					welcomePanel.transitionToNewState(InitialisationState.CREATE_NEW_PLAYER);
				}
			}

		};

		loadPlayer.addActionListener(loadcreate);
		createPlayer.addActionListener(loadcreate);

		makeButtonPretty(loadPlayer);
		makeButtonPretty(createPlayer);

		removeAllButtons();

		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingVertical,0))); //pad between buttons
		add(loadPlayer);
		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingVertical,0))); //pad between buttons
		add(createPlayer);


		setVisible(true);

		repaint();

	}

	private void addLoadNewButtons() {

		ActionListener loadnewListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==loadGame){
					try {
						initialisation.notify("load");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					welcomePanel.transitionToNewState(InitialisationState.LOAD_GAME);
				}
				else if(e.getSource()==newGame){	//conditional not strictly necessary, but added for completion
					try {
						initialisation.notify("newGame");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					welcomePanel.transitionToNewState(InitialisationState.CHOOSE_SLIDER_OPTIONS);
				}
			}

		};

		loadGame.addActionListener(loadnewListener);
		newGame.addActionListener(loadnewListener);

		removeAllButtons();

		makeButtonPretty(loadGame);
		makeButtonPretty(newGame);

		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingVertical,0))); //pad between buttons
		add(loadGame);
		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingVertical,0))); //pad between buttons
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
				if(e.getSource()==client){
					try {
						initialisation.notify("client");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					welcomePanel.transitionToNewState(InitialisationState.CONNECT_TO_SERVER);	//now display the option for which server to connect to
				}
				else if(e.getSource()==serverclient){	//conditional not strictly necessary, but added for completion
					try {
						initialisation.notify("clientserver");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					welcomePanel.transitionToNewState(InitialisationState.SHOW_LOAD_OR_NEW_OPTION); //now display the options of loading a game, or starting a new one
				}

			}

		};

		client.addActionListener(serverclientListener);
		serverclient.addActionListener(serverclientListener);

		makeButtonPretty(client);
		makeButtonPretty(serverclient);

		removeAllButtons();

		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingVertical,0))); //pad between buttons
		add(client);
		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingVertical,0))); //pad between buttons
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
				containerFrame.addInventoryDialog();
			}
		});


		team = new JButton("Team");
		team.setMnemonic(KeyEvent.VK_T);
		team.setToolTipText("Display your team");
		team.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(containerFrame,
						"You have NO team",
						"Sorry :(",
						JOptionPane.WARNING_MESSAGE);
			}
		});


		exchange = new JButton("Exchange");
		exchange.setMnemonic(KeyEvent.VK_E);
		exchange.setToolTipText("Exchange an item");
		exchange.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(containerFrame,
						"You have NO items to display",
						"Idiot!",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		//makePretty(inventory, team, exchange);
		makeButtonPretty(inventory);
		makeButtonPretty(team);
		makeButtonPretty(exchange);

		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingHorizontal,0))); //pad between buttons
		add(inventory);
		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingHorizontal,0))); //pad between buttons
		add(team);
		add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingHorizontal,0))); //pad between buttons
		add(exchange);

		revalidate();

	}

	//used in WelcomePanel, hence 'package' visibility
	static void makeButtonPretty(JButton b) {

		//System.out.println(b);
		javax.swing.border.Border line, raisedbevel, loweredbevel;
		TitledBorder title;
		javax.swing.border.Border empty;
		line = BorderFactory.createLineBorder(Color.black);
		raisedbevel = BorderFactory.createRaisedBevelBorder();
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		title = BorderFactory.createTitledBorder("");
		empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		final CompoundBorder compound, compound1, compound2;
		Color crl = (new Color(202, 0, 0));
		compound = BorderFactory.createCompoundBorder(empty, new OldRoundedBorderLine(crl));
		Color crl1 = (Color.GREEN.darker());
		compound1 = BorderFactory.createCompoundBorder(empty, new OldRoundedBorderLine(crl1));
		Color crl2 = (Color.black);
		compound2 = BorderFactory.createCompoundBorder(empty, new OldRoundedBorderLine(crl2));
		b.setFont(new Font("Sans-Serif", Font.BOLD, 16));
		b.setForeground(Color.darkGray);
		b.setPreferredSize(new Dimension(50, 30));

		b.setBorderPainted(true);
		b.setFocusPainted(false);
		b.setBorder(compound);

		b.revalidate();

	}

}
