package view;

import interpreter.DialogStrategy;
import interpreter.StrategyInterpreter;
import interpreter.Translator;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.xml.bind.JAXBException;

import dataStorage.Serializer;
import renderer.GameCanvas;
import renderer.GameRenderer;
import renderer.GuiForTest;
import renderer.testRenderer;

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
 * NEW version of GameFrame doesn't use GridBagLayout, but instead uses a BoxLayout with 3 Jpanels, each of
 * which has its own Layout
 * @author flanagdonn
 * TODO show onscreen what ip address
 */
public class GameFrame extends JFrame{
	private int frameWidth = 800;
	private int frameHeight = 800;

	private int gamePanelWidth = 800;
	private int gamePanelHeight = 600;

	private int bar_left = 20;
	private int bar_top = 100;
	private int bar_width = 100;
	private int bar_height = 20;

	private int lifelineValue = 50;

	//These constants define the main colour scheme and are used throughout all the panels which form the GameFrame
	public static final Color col1 = Color.CYAN.darker();
	public static final Color col2 = Color.PINK;
	public static final Color fontColor = Color.PINK;
	public static final Color buttonFontColor = new Color(0, 128, 255);

	private Dialog ContainerDialog;

	private ArrayList<Avatar> avatars;

	private String ip = "192...", time = "0100";

	private Dimension mapSize = new Dimension(750, 400);

	private Dimension gamePanelSize = new Dimension(800, 600);

	//public as needs to be accessed from ButtonPanel
	public static final int buttonPaddingHorizontal = 50;
	public static final int buttonPaddingVertical = 50;


	//for testing
	private PlayerInfo player = new PlayerInfo(Avatar.MUFFIN_MACLAY);

	/*
	 * TODO Initialise these interpreters here rather than in Main method?
	 */
	private StrategyInterpreter keyInterpreter;
	private StrategyInterpreter menuInterpreter;
	private StrategyInterpreter buttonInterpreter;
	private StrategyInterpreter radioInterpreter;

	private ArrayList<String> inventoryContents;
	private ArrayList<String> containerContents;

	//have to initialise this here for use in WelcomeDialog
	private StrategyInterpreter dialogInterpreter = new StrategyInterpreter(this, new DialogStrategy(), null);

	private testRenderer data;

	private PlayerProfilePanel playerProfilePanel;

	private JPanel midPanel;

	private JLayeredPane middleLayeredPane;

	private JPanel botPanel;

	private GameCanvas canvas;

	/**
	 * First a WelcomeDialog is displayed and then
	 * the constructor sets up the KeyListener using the KeyboardFocusManager, sets up the layout with all the appropriate Panels.
	 * @param title The title of the GameFrame, used in the super constructor
	 */
	public GameFrame(String title) {
		super(title);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		//setUpLayoutAndDisplay();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		/*
		 *Prompt the user to confirm if they click the close button
		 */
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we) {

				String ObjButtons[] = {"Yes", "No"};
				int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Happiness Game",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					Main.closeServer();
				}
			}
		});

		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());

		setResizable(false);


		//makePretty(topPanel.getPanels(), midPanel, botPanel);



	}

	public void setContainer(){

	}

	//add all the necessary panels with the appropriate GridBagConstraints. public visibility, for access in Main
	public void setUpLayoutAndDisplay() {

		addMenuBar();

		addTopPanel();

		addMiddlePanel();

		addBottomPanel();

		pack();
		setLocationRelativeTo(null);	//set the frame at the center of the screen
		setVisible(true);

	}

	private void addMiddlePanel() {
		middleLayeredPane = new JLayeredPane();

		canvas = new GameCanvas(gamePanelWidth, gamePanelHeight);

		midPanel = new JPanel();

		midPanel.add(canvas);

		midPanel.setBounds(0, 0, gamePanelWidth, gamePanelHeight);

		middleLayeredPane.add(midPanel, new Integer(0), 0);

		JPanel happinessPanel = null;
		happinessPanel = new JPanel(new BorderLayout());
		JLabel hapLevel = new JLabel("Happiness Level");
		JLabel ipAddress = new JLabel(ip);
		JLabel timeLabel = new JLabel("The time is: "+time);

		JLabel hapBar = new JLabel(){
			@Override
			public void paintComponent(Graphics g){
				//draw the happiness level title
				g.setColor(col2);
				g.drawString("Happiness Level", bar_left, bar_top - 20);

				//draw the bar in white
				g.setColor(Color.WHITE);
				g.fillRect(bar_left, bar_top, lifelineValue, bar_height);

				//draw a pink outline around the bar
				g.setColor(col2);
				g.drawRect(bar_left, bar_top, bar_width, bar_height);

				//draw the time info
				g.setColor(col2);
				g.drawString("The time is: "+time, bar_left, bar_top + 40);

				//draw the player's ip address
				g.setColor(col2);
				g.drawString(ip+"", bar_left, bar_top + 60);

			}

		};

		happinessPanel.add(hapBar, BorderLayout.CENTER);

		//want to see the map behind panel, so make panel not opaque
		happinessPanel.setOpaque(false);


		Dimension hapPanelSize = new Dimension (500, 500);

		happinessPanel.setBounds(20, -40, hapPanelSize.width, hapPanelSize.height);

		botPanel = new ButtonPanel(this, this.buttonInterpreter, Translator.MainGameState.MAIN);
		botPanel.setOpaque(false);
		botPanel.setBounds(530, 550, 400, 50);


		middleLayeredPane.add(happinessPanel, new Integer(1), 0);
		middleLayeredPane.add(botPanel, new Integer(1), 0);

		middleLayeredPane.setPreferredSize(new Dimension(gamePanelWidth, gamePanelHeight));

		middleLayeredPane.revalidate();

		add(middleLayeredPane);

	}

	private void addTopPanel() {
		playerProfilePanel = new PlayerProfilePanel(this);
		add(playerProfilePanel);
	}

	private void addBottomPanel() {
//		botPanel = new ButtonPanel(this, this.buttonInterpreter, Translator.MainGameState.MAIN);
//
//		botPanel.setVisible(true);
//
//		add(botPanel);

	}


	private void addMenuBar() {
		//create a new JMenuBar
		MenuBar bar = new MenuBar(menuInterpreter);

		//bar.setInterpreter(menuInterpreter);

		setJMenuBar(bar);
	}



	/**
	 * Sends a message to server if the user presses an arrow key
	 */

	//for testing renderer
	private class MyDispatcher implements KeyEventDispatcher{
		@Override
		public boolean dispatchKeyEvent(KeyEvent e){

			if (e.getID() == KeyEvent.KEY_PRESSED) {

				switch( e.getKeyCode()) {
				case KeyEvent.VK_UP:
					try {
						keyInterpreter.notify(Translator.Command.MOVE_NORTH.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					break;
				case KeyEvent.VK_DOWN:
					try {
						keyInterpreter.notify(Translator.Command.MOVE_SOUTH.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					break;
				case KeyEvent.VK_LEFT:
					try {
						keyInterpreter.notify(Translator.Command.MOVE_WEST.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					break;
				case KeyEvent.VK_RIGHT :
					try {
						keyInterpreter.notify(Translator.Command.MOVE_EAST.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					break;
				case KeyEvent.VK_P:
					try {
						keyInterpreter.notify(Translator.Command.PICK_UP.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					break;

				}
			}
			else if (e.getID() == KeyEvent.KEY_RELEASED) {
			}
			else if (e.getID() == KeyEvent.KEY_TYPED) {
			}
			return false;
		}
	}

	public void updateRenderer(char type, char[][] map, char[][] items){
		canvas.getRenderer().update(type, map, items);
	}

	/*	private void showDialog(String string) {
		int PromptResult = JOptionPane.showConfirmDialog(this, "You pressed: "+string);

		while(PromptResult==JOptionPane.NO_OPTION){
			PromptResult = JOptionPane.showConfirmDialog(this, "Yes you did!");
		}
	}*/

	/**
	 * Get contents of this player's inventory from Game, via network
	 * @return
	 */
	public ArrayList<String> getInventoryContents() {
		ArrayList<String> inventory = new ArrayList<String>();
		if(inventoryContents != null) {
			return inventoryContents;
		}
		else{
			inventory.add("key");
			inventory.add("cupcake");
			return inventory;
		}
	}

	/**
	 * Get contents of this player's container from Game, via network
	 * @return
	 */
	public ArrayList<String> getContainerContents() {
		ArrayList<String> container = new ArrayList<String>();
		if(containerContents != null) {
			return containerContents;
		}
		else{
			container.add("key");
			container.add("cupcake");
			return container;
		}
	}

	/*******************************************************************************************************
	 * TODO Server needs to do the following:
	 * 1) set Inventory/Container Contents
	 * 2) add Inventory/Container Dialog
	 *
	 *******************************************************************************************************/

	/**
	 * Set the inventory contents.
	 * @param inventory The list of items as lower-case Strings
	 */
	public void setInventoryContents(ArrayList<String> inventory){
		inventoryContents = inventory;
		addInventoryDialog();
	}

	/**
	 * Set the container contents.
	 * @param inventory The list of items as lower-case Strings
	 */
	public void setContainerContents(ArrayList<String> containerItems){
		containerContents = containerItems;
		addContainerDialog();
	}

	private void addContainerDialog() {
		Dialog inventory = new Dialog(this, "Display Container", "This container contains:",
				Translator.Command.DISPLAY_CONTAINER, this.dialogInterpreter);
	}

	/**
	 * Create a dialog showing the inventory.
	 */
	public void addInventoryDialog() {
		Dialog inventory = new Dialog(this, "Display Inventory", "Your inventory contains:",
				Translator.Command.DISPLAY_INVENTORY, this.dialogInterpreter);
	}

	/**
	 *
	 * @return The Key-Action-Event Interpreter
	 */
	public StrategyInterpreter getKeyInterpreter() {
		return keyInterpreter;
	}

	/**
	 *
	 * @return The Dialog-Action-Event Interpreter.
	 */
	public StrategyInterpreter getDialogInterpreter() {
		return dialogInterpreter;
	}

	/**
	 * Assigns the argument to the KeyInterpreter field
	 * @param keyInterpreter The Key-Action-Event Interpreter
	 */
	public void setKeyInterpreter(StrategyInterpreter keyInterpreter) {
		this.keyInterpreter = keyInterpreter;
	}

	/**
	 * Assigns the argument to the ButtonInterpreter field
	 * @param b The Button-Action-Event Interpreter
	 */
	public void setButtonInterpreter(StrategyInterpreter b) {
		this.buttonInterpreter = b;
	}

	/**
	 * Assigns the argument to the MenuInterpreter field
	 * @param m The Menu-Action-Event Interpreter
	 */
	public void setMenuInterpreter(StrategyInterpreter m) {
		this.menuInterpreter = m;
	}

	/**
	 * Assigns the argument to the DialogInterpreter field
	 * @param d The Dialog-Action-Event Interpreter
	 */
	public void setDialogInterpreter(StrategyInterpreter d) {
		this.dialogInterpreter = d;
	}

	/**
	 * Returns a PlayerInfo object which contains information about this player
	 * @return PlayerInfo object which contains information about this player
	 */
	public PlayerInfo getPlayer() {
		return player;
	}

	/**
	 * Assigns the argument to the Player field
	 * @param p The PlayerInfo object which contains information about this player
	 */
	public void setPlayer(PlayerInfo p){
		this.player = p;
	}

	public int getMapWidth() {
		return this.midPanel.getWidth();
	}

	/**
	 * TODO Server calls setAvailableAvatars after the new game/load avatars button chosen.
	 * Then Server calls displayAvatarptions
	 * @return
	 */

	public void setAvatars(ArrayList<Avatar> avatarOptions) {
		this.avatars = avatarOptions;
	}



	public ArrayList<Avatar> getAvailableAvatars() {

		//if the game hasn't yet sent the available avatars
		if(avatars == null){

			ArrayList<Avatar> avatarsTest= new ArrayList<Avatar>();
			avatarsTest.add(Avatar.DONALD_DUCK);	//for testing purposes

			return avatarsTest;

		}
		else{
			return avatars;
		}
	}


	private void makePretty(Set<JPanel> topPanels, JPanel p1, JPanel p2) {

		HashSet<JPanel> panels = new HashSet<JPanel>();
		panels.addAll(topPanels);
		panels.add(p1);
		panels.add(p2);

		/*for(JPanel b:panels){
			System.out.println(b);
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
	        Color crl1 = (Color.red);
	        compound1 = BorderFactory.createCompoundBorder(empty, new OldRoundedBorderLine(crl1));
	        Color crl2 = (Color.black);
	        compound2 = BorderFactory.createCompoundBorder(empty, new OldRoundedBorderLine(crl2));
	        b.setFont(new Font("Serif", Font.BOLD, 14));
	        b.setForeground(Color.darkGray);
	        b.setPreferredSize(new Dimension(50, 30));

//	        b.setBorderPainted(true);
//	        b.setFocusPainted(false);
	        b.setBorder(compound);


		}*/

	}

	public StrategyInterpreter getRadioInterpreter() {
		return radioInterpreter;
	}

	public void setRadioInterpreter(StrategyInterpreter menuInterpreter) {
		this.radioInterpreter = menuInterpreter;

	}

	public StrategyInterpreter getButtonInterpreter() {
		return this.buttonInterpreter;
	}

	/**
	 * a getter for the MidPanel
	 * @return
	 */
	public GameCanvas getCanvas() {
		return canvas;
	}

	public String getTime() {
		return time;
	}

	public String getIP() {
		return ip;
	}

	public void setTime(String t) {
		time = t;
	}

	public void setIP(String ipAddress) {
		ip = ipAddress;
	}

}
