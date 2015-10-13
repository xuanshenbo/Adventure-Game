package view.frames;


import interpreter.StrategyInterpreter;
import interpreter.Translator;
import interpreter.Translator.Command;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.xml.bind.JAXBException;

import dataStorage.Serializer;
import renderer.GameCanvas;
import renderer.GameRenderer;
//import renderer.GuiForTest;
import renderer.testRenderer;
import view.panels.ButtonPanel;
import view.panels.PlayerProfilePanel;
import view.panels.StatusPanel;
import view.styledComponents.MenuBar;
import view.utilities.Avatar;
import view.utilities.PlayerInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import main.Main;
import model.items.Item;
import model.items.Key;
import model.logic.Game;
import model.logic.Game.Direction;
import model.logic.Generator;
import model.state.Area;
import model.state.GameState;
import model.state.Player;
import model.state.Position;
import static utilities.PrintTool.p;

/**
 * NEW version of GameFrame doesn't use GridBagLayout, but instead uses a
 * BoxLayout with 3 Jpanels, each of which has its own Layout
 *
 * @author flanagdonn TODO show onscreen what ip address
 */
public class GameFrame extends JFrame {
	private int frameWidth = 800;
	private int frameHeight = 800;

	private int gamePanelWidth = 800;
	private int gamePanelHeight = 600;

	/*
	 * These constants define the main colour scheme and are used throughout all
	 * the panels which form the GameFrame
	 */
	public static final Color COL1 = Color.CYAN.darker();
	public static final Color COL2 = Color.PINK;
	public static final Color FONT_COLOR = Color.PINK;
	public static final Color BUTTON_FONT_COLOR = new Color(0, 128, 255);
	public static final Color STATUS_PANEL_FONT_COLOR = new Color(152, 152, 255);
	public static final Color HAPPINESS_BAR_COLOR = new Color(255, 204, 255);
	public static final Color STATUS_PANEL_COLOR = Color.GRAY;

	private String ip = "???", time = "???";

	private Dimension statusPanelSize = new Dimension(170, 125);

	/**
	 * These constants determine the padding between all buttons used in the game
	 */
	public static final int BUTTON_PADDING_HORIZONTAL = 50;
	public static final int BUTTON_PADDING_VERTICAL = 50;

	private YesNoOptionWindow yesno;

	/*
	 * These are the interpreters which decide what to do with various user
	 * inputs
	 */
	private StrategyInterpreter keyInterpreter;
	private StrategyInterpreter menuInterpreter;
	private StrategyInterpreter buttonInterpreter;
	private StrategyInterpreter radioInterpreter;

	// these fields are set by the model, ready for display
	private ArrayList<String> inventoryContents;
	private ArrayList<String> containerContents;

	// gap around main buttons/status panel. Public for use in StatusPanel
	public static int gap = 5;

//	// have to initialise this here for use in WelcomeDialog
//	private StrategyInterpreter dialogInterpreter = new StrategyInterpreter(
//			this, new DialogStrategy(), null);

	private PlayerProfilePanel playerProfilePanel;

	private JPanel midPanel;

	private JLayeredPane middleLayeredPane;

	private JPanel butPanel;

	private GameCanvas canvas;

	private Dialog container;

	private Dimension buttonPanelSize = new Dimension(265, 50);

	//The information object associated with this player
	private PlayerInfo player;

	/**
	 * First a WelcomeDialog is displayed and then the constructor sets up the
	 * KeyListener using the KeyboardFocusManager, sets up the layout with all
	 * the appropriate Panels.
	 *
	 * @param title
	 *            The title of the GameFrame, used in the super constructor
	 */
	public GameFrame(String title) {
		super(title);

		/*
		 * Set the LookAndFeel to system default for client
		 */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		/*
		 * Prompt the user to confirm if they click the close button
		 */

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we) {

				yesno = new YesNoOptionWindow(Command.EXIT, GameFrame.this, "Happiness Game");

			}
		});


		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());

		// the game doesn't resize, so it doesn't make logical sense for the
		// window to be resizeable
		setResizable(false);

		// add a pink border around the whole frame
		getRootPane().setBorder(
				BorderFactory.createMatteBorder(4, 4, 4, 4, Color.PINK));

		// set this player
		player = new PlayerInfo();

	}

	/**
	 * Add all the necessary panels with the appropriate GridBagConstraints. It
	 * has public visibility, for access in Main
	 */
	public void setUpLayoutAndDisplay() {

		// This just has a file menu, with options to save, exit etc
		MenuBar bar = new MenuBar(menuInterpreter);
		setJMenuBar(bar);

		// The playerProfilePanel has the game logo, user avatar and name
		playerProfilePanel = new PlayerProfilePanel(this);
		add(playerProfilePanel);

		// This has the main game image, with buttons and a status panel
		addMiddlePanel();

		pack();
		setLocationRelativeTo(null); // set the frame at the center of the
										// screen
		setVisible(true);

	}

	/*
	 * add the middle pane as a JLayeredPane in order to be able to draw buttons  and
	 * the status panel on top of the main game canvas
	 */
	private void addMiddlePanel() {
		middleLayeredPane = new JLayeredPane();

		canvas = new GameCanvas(gamePanelWidth, gamePanelHeight);

		midPanel = new JPanel();

		midPanel.add(canvas);

		midPanel.setBounds(0, 0, gamePanelWidth, gamePanelHeight);

		middleLayeredPane.add(midPanel, new Integer(0), 0);

		JPanel statusPanel = createStatusPanel();

		createButtonPanel();

		// add the status panel and button panel ABOVE the main game canvas
		middleLayeredPane.add(statusPanel, new Integer(1), 0);
		middleLayeredPane.add(butPanel, new Integer(1), 0);

		middleLayeredPane.setPreferredSize(new Dimension(gamePanelWidth,
				gamePanelHeight));

		add(middleLayeredPane);

	}

	// This has the two main game buttons: Inventory and Team
	private void createButtonPanel() {
		butPanel = new ButtonPanel(this, this.buttonInterpreter,
				Command.MAIN);

		// so as to be able to see the game behind the buttons
		butPanel.setOpaque(false);

		int butPanelX, butPanelY;
		butPanelX = gamePanelWidth - buttonPanelSize.width - gap;
		butPanelY = gamePanelHeight - buttonPanelSize.height - gap;
		butPanel.setBounds(butPanelX, butPanelY, buttonPanelSize.width,
				buttonPanelSize.height);

	}

	// This displays the time, ip address, and the happiness level of the player
	private JPanel createStatusPanel() {

		// use a border layout to be able to center the status window
		JPanel statusPanel = new StatusPanel(new BorderLayout(), this);

		statusPanel.setBounds(gap, gap * 2, statusPanelSize.width,
				statusPanelSize.height);

		return statusPanel;
	}

	/**
	 * TODO Gareth/Lucas to fill in the gaps Updates the renderer with the new
	 * view
	 *
	 * @param type
	 * @param map
	 * @param items
	 */
	public void updateRenderer(char type, char[][] map, char[][] items) {
		canvas.getRenderer().update(type, map, items);
	}

	/*
	 * Create a dialog showing the container contents.
	 */
	private void addContainerDialog() {
		if (container != null) {
			container.dispose();
		}
		container = new Dialog(this, "Display Container",
				"This container contains:",
				Command.DISPLAY_CONTAINER);
	}

	/*
	 * Create a dialog showing the inventory.
	 */
	private void addInventoryDialog() {
		Dialog inventory = new Dialog(this, "Display Inventory",
				"Your inventory contains:",
				Command.DISPLAY_INVENTORY);
	}



	/**
	 * @return true if this player is in server mode
	 */
	/*
	 * public boolean isServerMode() { return isServerMode; }
	 */

	/**
	 * Displays a message from the game to the user
	 *
	 * @param msg
	 *            The message to be displayed
	 */
	public void displayMessageFromGame(String msg) {
		MessageWindow m = new MessageWindow(msg);
	}

	/**
	 * This method determines whether the user has confirmed they wish to
	 * close the window or not
	 * @param state The state: should be EXIT
	 * @param isYes True if the user confirmed, false if they cancelled
	 */
	public void exitSelected(Command state, boolean isYes){
		if(state.equals(Command.EXIT)){
			if(isYes){
				try {
					/*
					 * Rather than have a Game Strategy just for this, use the
					 * menuInterpreter, which also has to deal with requests to
					 * exit
					 */
					menuInterpreter.notify(Command.EXIT.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			remove(yesno);
		}
	}

	/*
	 * Sends a message to server if the user presses an arrow key
	 */
	private class MyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {

			String toNotify = "";

			if (e.getID() == KeyEvent.KEY_PRESSED) {
				canvas.getRenderer().doAnimation();
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					canvas.getRenderer().setDir(23);
					if (canvas.getRenderer().getViewDir() == 0) {
						toNotify = Command.MOVE_NORTH.toString();
					}
					else {
						toNotify = Command.MOVE_SOUTH.toString();
					}
					break;

				case KeyEvent.VK_DOWN:
					canvas.getRenderer().setDir(7);
					if (canvas.getRenderer().getViewDir() == 0) {
						toNotify = Command.MOVE_SOUTH.toString();
					}
					else {
						toNotify = Command.MOVE_NORTH.toString();
					}
					break;

				case KeyEvent.VK_LEFT:
					canvas.getRenderer().setDir(15);
					if (canvas.getRenderer().getViewDir() == 0) {
						toNotify = Command.MOVE_WEST.toString();
					}
					else {
						toNotify = Command.MOVE_EAST.toString();
					}
					break;

				case KeyEvent.VK_RIGHT:
					canvas.getRenderer().setDir(31);
					if (canvas.getRenderer().getViewDir() == 0) {
						toNotify = Command.MOVE_EAST.toString();
					}
					else {
						toNotify = Command.MOVE_WEST.toString();
					}
					break;

				case KeyEvent.VK_P:

					toNotify = Command.PICK_UP.toString();
					break;

				case KeyEvent.VK_R:
					canvas.getRenderer().rotate();
					return false; //don't need to notify the game of anything
				}
			}

			if (e.getID() == KeyEvent.KEY_RELEASED) {
				canvas.getRenderer().stopAnimation();
			}

			// notify the key interpreter with the appropriate message
			try {
				keyInterpreter.notify(toNotify);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

	//====================================================================
	//===================GETTERS AND SETTERS FOLLOW=======================
	//====================================================================

	/**
	 * Get contents of this player's inventory from Game, via network
	 *
	 * @return The list of items represented as Strings
	 */
	public ArrayList<String> getInventoryContents() {
		ArrayList<String> inventory = new ArrayList<String>();
		if (inventoryContents != null) {
			return inventoryContents;
		} else {
			inventory.add("key");
			inventory.add("cupcake");
			return inventory;
		}
	}

	/**
	 * Get contents of this player's container from Game, via network
	 *
	 * @return The list of items represented as Strings
	 */
	public ArrayList<String> getContainerContents() {
		ArrayList<String> container = new ArrayList<String>();
		if (containerContents != null) {
			return containerContents;
		} else {
			container.add("key");
			container.add("cupcake");
			return container;
		}
	}

	/**
	 *
	 * @return The Key-Action-Event Interpreter
	 */
	public StrategyInterpreter getKeyInterpreter() {
		return keyInterpreter;
	}


	/**
	 * @return The width of the main game canvas panel
	 */
	public int getMapWidth() {
		return this.midPanel.getWidth();
	}

	/**
	 * @return The Radio button interpreter
	 */
	public StrategyInterpreter getRadioInterpreter() {
		return radioInterpreter;
	}

	/**
	 * @return The button interpreter
	 */
	public StrategyInterpreter getButtonInterpreter() {
		return this.buttonInterpreter;
	}

	/**
	 * @return The GameCanvas
	 */
	public GameCanvas getCanvas() {
		return canvas;
	}

	/**
	 * @return The current time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @return The ip address of the client
	 */
	public String getIP() {
		return ip;
	}

	/**
	 *
	 * @return This player's avatar
	 */
	public Avatar getAvatar() {
		return player.getAvatar();
	}

	/**
	 *
	 * @return The gap to be used around panels. Used in the StatusPanel
	 */
	public int getGap() {
		return gap;
	}

	/**
	 *
	 * @return This player's current happiness level
	 */
	public int getHappinessLevel() {
		return player.getHappinessValue();
	}

	/**
	 *
	 * @return This player's imaginary team mate number 1
	 */
	public String getTeamMember1() {
		return player.getTeamMember1();
	}

	/**
	 *
	 * @return This player's imaginary team mate number 2
	 */
	public String getTeamMember2() {
		return player.getTeamMember2();
	}

	/**
	 * Set the inventory contents.
	 *
	 * @param inventory
	 *            The list of items as lower-case Strings
	 */
	public void setInventoryContents(ArrayList<String> inventory) {
		System.out.println("inventory contents.");
		inventoryContents = inventory;
		addInventoryDialog();
	}

	/**
	 * Set the container contents.
	 *
	 * @param inventory
	 *            The list of items as lower-case Strings
	 */
	public void setContainerContents(ArrayList<String> containerItems) {
		System.out.println("setting container content");// debug
		containerContents = containerItems;
		addContainerDialog();
	}

	/**
	 * Assigns the argument to the KeyInterpreter field
	 *
	 * @param keyInterpreter
	 *            The Key-Action-Event Interpreter
	 */
	public void setKeyInterpreter(StrategyInterpreter keyInterpreter) {
		this.keyInterpreter = keyInterpreter;
	}

	/**
	 * Assigns the argument to the ButtonInterpreter field
	 *
	 * @param b
	 *            The Button-Action-Event Interpreter
	 */
	public void setButtonInterpreter(StrategyInterpreter b) {
		this.buttonInterpreter = b;
	}

	/**
	 * Assigns the argument to the MenuInterpreter field
	 *
	 * @param m
	 *            The Menu-Action-Event Interpreter
	 */
	public void setMenuInterpreter(StrategyInterpreter m) {
		this.menuInterpreter = m;
	}

	/**
	 * Assigns the argument to the RadioInterpreter field
	 *
	 * @param d
	 *            The Radio Button-Action-Event Interpreter
	 */
	public void setRadioInterpreter(StrategyInterpreter menuInterpreter) {
		this.radioInterpreter = menuInterpreter;

	}

	/**
	 *
	 * @param t
	 *            The new time to be assigned.
	 */
	public void setTime(int t) {
		int absoluteValue = Math.abs(t);
		time = absoluteValue+":00";
	}

	/**
	 *
	 * @param ipAddress
	 *            The ipAddress of this client, to be assigned to the field.
	 */
	public void setIP(String ipAddress) {
		ip = ipAddress;
	}

	/**
	 * @return true if this player is in server mode
	 */
	/*
	 * public boolean isServerMode() { return isServerMode; }
	 */

	/**
	 *
	 * @param lvl
	 *            The happiness level of this player, to be reassigned to the
	 *            field.
	 */
	public void setHappinessLevel(int lvl) {
		player.setHappinessValue(lvl);
		repaint();
	}

	/**
	 *
	 * @param chosenAvatar
	 *            The avatar chosen by this player, to be displayed in the
	 *            playerprofilepanel
	 */
	public void setAvatar(Avatar chosenAvatar) {
		player.setAvatar(chosenAvatar);

	}

}
