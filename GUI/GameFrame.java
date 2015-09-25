package GUI;

import interpreter.StrategyInterpreter;

import javax.swing.*;

import renderer.GameRenderer;
import renderer.testRenderer;
import state.Item;
import state.Key;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Set;


public class GameFrame extends JFrame{
	private int frameWidth = 800;
	private int frameHeight = 800;

	private Dimension mapSize = new Dimension(750, 400);

	public final int buttonPaddingHorizontal = 50;	//public as needs to be accessed from ButtonPanel
	private final int buttonPaddingVertical = 50;

	private PlayerInfo player = new PlayerInfo("Donald Duck", Avatar.DONALD_DUCK);

	private StrategyInterpreter keyInterpreter;

	private StrategyInterpreter buttonInterpreter;
	private testRenderer data;


	/**
	 * The constructor sets up the KeyListener using the KeyboardFocusManager
	 * @param title The title of the GameFrame, used in the super constructor
	 */
	public GameFrame(String title) {
		super(title);

		setLayout(new GridBagLayout());

		addMenuBar();

		setUpLayout();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		/*
		 *Prompt the user to confirm if they click the close button
		 */
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we) {

				String ObjButtons[] = {"Yes", "No"};
				int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "CluedoGame",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});


		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());

		pack();
		setLocationRelativeTo(null);	//set the frame at the center of the screen
		setVisible(true);
	}

	//add all the necessary panels with the appropriate GridBagConstraints
	private void setUpLayout() {

		GridBagConstraints c;

		addGameInfoPanel();

		addTeamPanel();

		addPlayerProfilePanel();

		addGameMapPanel();

		addButtonPanel();


	}

	private void addTeamPanel() {
		GridBagConstraints c;
		//implement team panel later?

		/*c = new GridBagConstraints();
		c.ipady = 0;       //reset to default
		JButton team = new JButton("The Team panel");
		c.insets = new Insets(0,buttonPaddingHorizontal,0,buttonPaddingHorizontal);  //padding on sides
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridwidth = 1;   //1 column wide
		c.gridx = 3;
		c.gridy = 3; //same row as time/position panel
		add(team, c); //add panel which shows the Team members. Should we implement team??
		 */
		}

	private void addMenuBar() {
		GridBagConstraints c = new GridBagConstraints();

		//create a new JMenuBar
		JMenuBar bar = new JMenuBar();

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
				            //notify interpreter to open file
				        } else {
				            //do nothing
				        }
				}
				else if(e.getSource()==save){
					int returnVal = fc.showSaveDialog(GameFrame.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
			            //notify interpreter to save file
			        } else {
			            //do nothing
			        }

				}
			}
		};

		exit.addActionListener(menuListener);
		save.addActionListener(menuListener);
		load.addActionListener(menuListener);

		menu.add(exit);
		menu.add(save);
		menu.add(load);

		bar.add(menu);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START; //top of space
		c.gridx = 0;       //aligned with button 1

		c.gridwidth = 9;   //6 columns wide
		c.gridheight = 1; //1 row high?
		c.gridy = 2;       //third row

		add(bar, c);
	}

	private void addButtonPanel() {
		GridBagConstraints c1 = new GridBagConstraints();
		c1.ipady = 0;       //reset to default
		ButtonPanel buttons = new ButtonPanel(this, this.buttonInterpreter);

		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.ipady = 0;       //reset to default
		//c1.weighty = 1.0;   //request any extra vertical space
		c1.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c1.gridx = 7;
		//c1.gridwidth = 6;   //6 columns wide
		c1.gridy = 10;       //10th row
		add(buttons, c1);
	}

//	private void addGameMapPanel() {
//		GridBagConstraints c;
//		c = new GridBagConstraints();
//		JButton map = new JButton("The Game Map panel");
//		c.insets = new Insets(buttonPaddingVertical,0,buttonPaddingVertical,0);  //padding on top and bottom
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.ipady = mapSize.height;      //specify height
//		c.ipadx = mapSize.width; //specify width
//		c.gridwidth = 9;
//		c.gridheight = 6;
//		c.gridx = 0;
//		c.gridy = 5;
//		add(map, c); //add main board panel showing map
//	}

	private void addPlayerProfilePanel() {
		GridBagConstraints c;
		c = new GridBagConstraints();
		PlayerProfilePanel profile = new PlayerProfilePanel(player);
		c.insets = new Insets(0,buttonPaddingHorizontal,0,buttonPaddingHorizontal);  //padding on sides
		c.fill = GridBagConstraints.NONE;	//don't resize this panel
		c.gridwidth = 1;   //1 column wide
		c.gridx = 8;
		c.gridy = 3;
		add(profile, c); //add panel which shows current player picture, and stats
	}

	private void addGameInfoPanel() {
		GridBagConstraints c = new GridBagConstraints();
		GameInfoPanel gameInfo = new GameInfoPanel();
		//c.insets = new Insets(0,buttonPaddingHorizontal,0,buttonPaddingHorizontal);  //padding on sides
		c.fill = GridBagConstraints.NONE; //don't resize this panel
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;   //1 column wide
		add(gameInfo, c); //add panel which shows the time, position, map button?
	}

	/**
	 * Shows a dialog if the user presses an arrow key
	 */

	//for testing renderer
	private class MyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {

			if (e.getID() == KeyEvent.KEY_PRESSED) {
				int x = data.getPlayers().get(0).getPosition().getX();
				int y = data.getPlayers().get(0).getPosition().getY();

				switch( e.getKeyCode()) {
					case KeyEvent.VK_UP:
						y -= 1;
						keyInterpreter.notify("UP"); //implement for all key presses
						break;
					case KeyEvent.VK_DOWN:
						y += 1;
						//notify interpreter?
						break;
					case KeyEvent.VK_LEFT:
						x -= 1;
						//notify interpreter?
						break;
					case KeyEvent.VK_RIGHT :
						x += 1;
						//notify interpreter?
						break;
				}

				data.getPlayers().get(0).getPosition().setX(x);
				data.getPlayers().get(0).getPosition().setY(y);
			}
			else if (e.getID() == KeyEvent.KEY_RELEASED) {
			}
			else if (e.getID() == KeyEvent.KEY_TYPED) {
			}
			return false;
		}


	}

	private void showDialog(String string) {
		int PromptResult = JOptionPane.showConfirmDialog(this, "You pressed: "+string);

		while(PromptResult==JOptionPane.NO_OPTION){
			PromptResult = JOptionPane.showConfirmDialog(this, "Yes you did!");
		}

	}

	public Set<Item> getInventoryContents() {

		Set<Item> items = new HashSet<Item>();

		//Get contents from Game, via Felix

		//for testing, display random num of images
		for(int i = 0; i<(int)(Math.random()*5 +1); i++){
			items.add(new Key());
		}

		return items;
	}

	public void addInventoryDialog() {
		Dialog inventory = new Dialog(this, "Display Inventory", "Your inventory contains:", "inventory");
	}

	public StrategyInterpreter getKeyInterpreter() {
		return keyInterpreter;
	}

	public void setKeyInterpreter(StrategyInterpreter keyInterpreter) {
		this.keyInterpreter = keyInterpreter;
	}
	public void setButtonInterpreter(StrategyInterpreter b) {
		this.buttonInterpreter = b;

	}



	//add by Lucas for debugging purpose
	private void addGameMapPanel() {
		GridBagConstraints c;
		c = new GridBagConstraints();

		//data for testing
		data = new testRenderer(20, 0, 0, 15, 20, 20, 4, 0);

		GameRenderer map = new GameRenderer(mapSize.width, mapSize.height, data.getArea().getTileArray(), data.getPlayers());
		c.insets = new Insets(buttonPaddingVertical,0,buttonPaddingVertical,0);  //padding on top and bottom
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = mapSize.height;      //specify height
		c.ipadx = mapSize.width; //specify width
		c.gridwidth = 9;
		c.gridheight = 6;
		c.gridx = 0;
		c.gridy = 5;
		add(map, c); //add main board panel showing map
	}
}
