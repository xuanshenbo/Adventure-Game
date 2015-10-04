package GUI;

import interpreter.DialogStrategy;
import interpreter.StrategyInterpreter;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.xml.bind.JAXBException;

import dataStorage.Serializer;
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

import main.InitialisationState;
import main.Main;
import main.MainGameState;
import model.items.Item;
import model.items.Key;
import model.logic.Game;
import model.logic.Game.Direction;
import model.logic.Generator;
import model.state.Area;
import model.state.GameState;
import model.state.Player;
import model.state.Position;

/**
 * NEW version of GameFrame doesn't use GridBagLayout, but instead uses a BoxLayout with 3 Jpanels, each of
 * which has its own Layout
 * @author flanagdonn
 * TODO show onscreen what ip address
 */
public class GameFrame extends JFrame{
	private int frameWidth = 800;
	private int frameHeight = 800;

	private Game game; //TODO initalise this WHERE?

	private Dimension mapSize = new Dimension(750, 400);

	private Dimension gamePanelSize = new Dimension(800, 600);

	public static final int buttonPaddingHorizontal = 50;	//public as needs to be accessed from ButtonPanel
	public static final int buttonPaddingVertical = 50;

	private PlayerInfo player = new PlayerInfo("Donald Duck", Avatar.DONALD_DUCK);

	private StrategyInterpreter keyInterpreter;
	private StrategyInterpreter menuInterpreter;
	private StrategyInterpreter buttonInterpreter;

	//have to initialise this here for use in WelcomeDialog
	private StrategyInterpreter dialogInterpreter = new StrategyInterpreter(this, new DialogStrategy(), null);

	private testRenderer data;

	private TopPanel topPanel;

	private JPanel midPanel;

	private JPanel botPanel;

	private GameRenderer renderer;

	/**
	 * First a WelcomeDialog is displayed and then
	 * the constructor sets up the KeyListener using the KeyboardFocusManager, sets up the layout with all the appropriate Panels.
	 * @param title The title of the GameFrame, used in the super constructor
	 */
	public GameFrame(String title, Game g) {
		super(title);
		game = g;
		/*try {
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}*/

		addMenuBar();

		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		setUpLayout();

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
					System.exit(0);
				}
			}
		});

		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());

		makePretty(topPanel.getPanels(), midPanel, botPanel);

		pack();
		setLocationRelativeTo(null);	//set the frame at the center of the screen
		setVisible(true);
	}


	//add all the necessary panels with the appropriate GridBagConstraints
	private void setUpLayout() {

		setupMiddlePanel();//need it set up so can use its width in Top Panel class

		addTopPanel();

		add(midPanel);	//as it has already been set up

		addBottomPanel();


	}

	private void addTopPanel() {
		topPanel = new TopPanel(this);
		add(topPanel);

	}

	/*
	 * Sets up the Middle Panel, ready for displaying. But doesn't add it to the Frame yet.
	 */
	private void setupMiddlePanel() {

		midPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		//generate a Game for testing
		//this.game = generateGame(20, 2, 1, 5, 20, 20, 4, 1);

		char[][] view = game.getGameView(1).get(0);
		char[][] objects = game.getGameView(1).get(1);

		renderer = new GameRenderer(800, 600, view, objects);

		Image renderWindow = renderer.getImage(); //need to set the size??
		JLabel renderLabel = new JLabel(new ImageIcon(renderWindow));
		midPanel.add(renderLabel);

		//data = new testRenderer(20, 0, 0, 15, 20, 20, 4, 0);

	}

	//taken from GUIForTest for testing purposes
	/*private static Game generateGame(int trees, int buildings, int caves, int chests, int width, int height, int playerCount, int lootValue) {
		Generator g = new Generator(trees, buildings, caves, chests, lootValue);
		Area a = new Area(width, height, Area.AreaType.OUTSIDE, null);
		a.generateWorld(g);
		ArrayList<Player> p = placePlayers(playerCount, width, height, a);
		GameState state = new GameState(a, p);
		Game game = new Game(state);
		return game;
	}*/

	//taken from GUIForTest for testing purposes
	/*private static ArrayList<Player> placePlayers(int playerCount, int width, int height, Area a) {
		double[] xCoords = {0.5, 0, 0.5, 1};
		double[] yCoords = {0, 0.5, 1, 0.5};
		ArrayList<Player> list = new ArrayList<Player>();
		for(int count = 0; count < playerCount; count++){
			int x = (int) ((width-1)*xCoords[count]);
			int y = (int) ((height-1)*yCoords[count]);
			int id = count+1;
			Position position = new Position(x, y, a);
			Player p = new Player(position, id);
			list.add(p);
		}
		return list;
	}*/

	private void addBottomPanel() {
		//new JPanel(new BoxLayout(botPanel, BoxLayout.LINE_AXIS));
		botPanel = new ButtonPanel(this, this.buttonInterpreter, MainGameState.MAIN);

		//botPanel.add(buttons);

		add(botPanel);

	}


	private void addMenuBar() {
		//create a new JMenuBar
		MenuBar bar = new MenuBar(game);

		/*JMenuBar bar = new JMenuBar();

		//create a File menu
		JMenu menu = new JMenu("File");

		//Create a file chooser
		final JFileChooser fc = new JFileChooser();

		//add an Exit option to the file menu
		final JMenuItem exit = new JMenuItem("Exit");

		//add Save option to the file menu
		final JMenuItem save = new JMenuItem("Save");

		//add Load option to the file menu
		final JMenuItem load = new JMenuItem("Load");

		//add a menu listener
		ActionListener menuListener = new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				//prompt the user if they choose exit, to ensure they want to exit the program
				if(e.getSource()==exit){
					String ObjButtons[] = {"Yes","No"};
					int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Adventure Game",
							JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
					if(PromptResult==JOptionPane.YES_OPTION){
						System.exit(0);
					}
				}
				else if(e.getSource()==load){
					int returnVal = fc.showOpenDialog(GameFrame.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						System.out.println(file);
						try {
							menuInterpreter.notify("open "+file);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						//do nothing
					}
				}
				else if(e.getSource()==save){
					System.out.println("Saving the game...");
					try {
						Serializer.serialize(game.getGameState());
					} catch (JAXBException ex) {
						System.out.println("Saving failed...");
						return;
					}
					System.out.println("Done!");
				}
			}
		};

		exit.addActionListener(menuListener);
		save.addActionListener(menuListener);
		load.addActionListener(menuListener);

		menu.add(exit);
		menu.add(save);
		menu.add(load);

		bar.add(menu);*/



		setJMenuBar(bar);
	}



	/**
	 * Shows a dialog if the user presses an arrow key
	 */

	//for testing renderer
	private class MyDispatcher implements KeyEventDispatcher{
		@Override
		public boolean dispatchKeyEvent(KeyEvent e){

			if (e.getID() == KeyEvent.KEY_PRESSED) {
				char[][] map;
				char[][] items;

				switch( e.getKeyCode()) {
				case KeyEvent.VK_UP:
					try {
						keyInterpreter.notify("up");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} //implement for all key presses

					/*game.move(game.getGameState().getPlayer(1), Direction.UP);
					map = game.getGameView(1).get(0);
					items = game.getGameView(1).get(1);
					renderer.update(map, items);*/
					midPanel.repaint();

					break;
				case KeyEvent.VK_DOWN:
					try {
						keyInterpreter.notify("down");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					/*game.move(game.getGameState().getPlayer(1), Direction.DOWN);

					map = game.getGameView(1).get(0);
					items = game.getGameView(1).get(1);
					renderer.update(map, items);*/
					midPanel.repaint();

					break;
				case KeyEvent.VK_LEFT:
					try {
						keyInterpreter.notify("left");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					//TODO refactor this using interpreter
					/*game.move(game.getGameState().getPlayer(1), Direction.LEFT);

					map = game.getGameView(1).get(0);
					items = game.getGameView(1).get(1);
					renderer.update(map, items);*/
					midPanel.repaint();

					break;
				case KeyEvent.VK_RIGHT :
					try {
						keyInterpreter.notify("right");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

			/*		game.move(game.getGameState().getPlayer(1), Direction.RIGHT);

					map = game.getGameView(1).get(0);
					items = game.getGameView(1).get(1);
					renderer.update(map, items);*/
					midPanel.repaint();

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

	public void updateRenderer(char[][] map, char[][] items){
		renderer.update(map, items);
	}

	private void showDialog(String string) {
		int PromptResult = JOptionPane.showConfirmDialog(this, "You pressed: "+string);

		while(PromptResult==JOptionPane.NO_OPTION){
			PromptResult = JOptionPane.showConfirmDialog(this, "Yes you did!");
		}

	}

	/**
	 * Get contents of this player's inventory from Game, via network
	 * @return
	 */
	public Set<Item> getInventoryContents() {

		//for testing, display random num of key images

		Set<Item> items = new HashSet<Item>();
		for(int i = 0; i<(int)(Math.random()*5 +1); i++){
			items.add(new Key());
		}

		return items;
	}

	public void addInventoryDialog() {
		Dialog inventory = new Dialog(this, "Display Inventory", "Your inventory contains:",
				MainGameState.DISPLAY_INVENTORY, this.dialogInterpreter);
	}

	public StrategyInterpreter getKeyInterpreter() {
		return keyInterpreter;
	}

	public StrategyInterpreter getDialogInterpreter() {
		return dialogInterpreter;
	}

	public void setKeyInterpreter(StrategyInterpreter keyInterpreter) {
		this.keyInterpreter = keyInterpreter;
	}
	public void setButtonInterpreter(StrategyInterpreter b) {
		this.buttonInterpreter = b;
	}

	public void setMenuInterpreter(StrategyInterpreter m) {
		this.menuInterpreter = m;
	}

	public void setDialogInterpreter(StrategyInterpreter d) {
		this.dialogInterpreter = d;
	}

	public PlayerInfo getPlayer() {
		return player;
	}

	public int getMapWidth() {
		return this.midPanel.getWidth();
	}

	public ArrayList<Avatar> getAvailableAvatars() {
		ArrayList<Avatar> avatars= new ArrayList<Avatar>();
		// ask Model for the available Avatars to display as options to the user

		avatars.add(Avatar.DONALD_DUCK);	//for testing purposes

		return avatars;
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



	public Game getGame() {
		return this.game;
	}
}
