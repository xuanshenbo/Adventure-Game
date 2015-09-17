package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class GameFrame extends JFrame{
	private int frameWidth = 800;
	private int frameHeight = 800;

	private int mapSize = 400;

	public final int buttonPaddingHorizontal = 20;	//public as needs to be accessed from ButtonPanel
	private final int buttonPaddingVertical = 50;

	/**
	 * The constructor sets up the KeyListener using the KeyboardFocusManager
	 * @param title The title of the GameFrame, used in the super constructor
	 */
	public GameFrame(String title) {
		super(title);
		setLayout(new GridBagLayout());
		setUpLayout();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		/*
		 *Prompt the user to confirm if they click the close button
		 */
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we) {

				String ObjButtons[] = {"Yes","No"};
				int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","CluedoGame",
						JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
				if(PromptResult==JOptionPane.YES_OPTION)
				{
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

	private void setUpLayout() {
		GridBagConstraints c = new GridBagConstraints();
		//natural height, maximum width
		c.fill = GridBagConstraints.HORIZONTAL;

		c = new GridBagConstraints();
		c.ipady = 0;       //reset to default
		JButton time = new JButton("The time/position panel");
		c.insets = new Insets(0,buttonPaddingHorizontal,0,buttonPaddingHorizontal);  //padding on sides
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;   //1 column wide
		c.gridheight = 1; //1 row high
		add(time, c); //add panel which shows the time, position, map button?

		//implement team panel later?
		/*
		c = new GridBagConstraints();
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

		c = new GridBagConstraints();
		c.ipady = 0;       //reset to default
		//JButton profile = new JButton("The Player Profile Panel");
		UserProfilePanel profile = new UserProfilePanel();
		c.insets = new Insets(0,buttonPaddingHorizontal,0,buttonPaddingHorizontal);  //padding on sides
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridwidth = 1;   //1 column wide
		c.gridx = 6;
		c.gridy = 3;
		add(profile, c); //add panel which shows current player picture, and stats

		c = new GridBagConstraints();
		c.ipady = 0;       //reset to default
		JButton map = new JButton("The Game Map panel");
		c.insets = new Insets(buttonPaddingVertical,0,buttonPaddingVertical,0);  //padding on top and bottom
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = mapSize;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 9;
		c.gridheight = 6;
		c.gridx = 0;
		c.gridy = 5;
		add(map, c); //add main board panel showing map

		GridBagConstraints c1 = new GridBagConstraints();
		c1.ipady = 0;       //reset to default
		ButtonPanel buttons = new ButtonPanel(this);

		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.ipady = 0;       //reset to default
		c1.weighty = 1.0;   //request any extra vertical space
		c1.anchor = GridBagConstraints.PAGE_END; //bottom of space
		//c1.insets = new Insets(10,0,0,0);  //top padding
		c1.gridx = 6;       //aligned with team panel
		//c1.gridwidth = 6;   //6 columns wide
		c1.gridy = 10;       //10th row
		add(buttons, c1);

		c = new GridBagConstraints();
		c.ipady = 0;       //reset to default
		MenuBar menu = new MenuBar();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default

		//c.weighty = 0.5;	//request any extra vertical space
		//c.ipadx = 2;
		c.anchor = GridBagConstraints.PAGE_START; //top of space
		//c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 0;       //aligned with button 1

		c.gridwidth = 9;   //6 columns wide
		c.gridheight = 1; //1 row high?
		c.gridy = 2;       //third row
		add(menu, c);

	}

	/**
	 * Shows a dialog if the user presses an arrow key
	 */
	private class MyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {

			if (e.getID() == KeyEvent.KEY_PRESSED) {
				switch( e.getKeyCode()) {
				case KeyEvent.VK_UP:
					showDialog("UP!");
					//notify interpreter?
					break;
				case KeyEvent.VK_DOWN:
					showDialog("DOWN!");
					//notify interpreter?
					break;
				case KeyEvent.VK_LEFT:
					showDialog("LEFT!");
					//notify interpreter?
					break;
				case KeyEvent.VK_RIGHT :
					showDialog("RIGHT!");
					//notify interpreter?
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

	private void showDialog(String string) {
		int PromptResult = JOptionPane.showConfirmDialog(this, "You pressed: "+string);

		while(PromptResult==JOptionPane.NO_OPTION){
			PromptResult = JOptionPane.showConfirmDialog(this, "Yes you did!");
		}

	}

	public static void main(String[] args){
		new GameFrame("Adventure Game");
	}

}
