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

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class GameFrame extends JFrame{
	private int frameWidth = 800;
	private int frameHeight = 800;

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

		setLocationRelativeTo(null);//set the frame at the center of the screen TODO fix this so it does actually center it on screen

		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());

		add(new MenuBar());
		add(new ButtonPanel());

		pack();
		setSize(new Dimension(frameWidth, frameHeight));	//should use setPreferredSize??

		setVisible(true);
	}

	private void setUpLayout() {
		GridBagConstraints c = new GridBagConstraints();
		                //natural height, maximum width
		                c.fill = GridBagConstraints.HORIZONTAL;


		//button = new JButton("Button 1");

		                   c.weightx = 0.5;

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		//pane.add(button, c); add panel which shows the time, position, map button?

		//button = new JButton("Button 2");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		//pane.add(button, c); add panel which shows the Team members?? Should we implement team??

		//button = new JButton("Button 3");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		//pane.add(button, c);add panel which shows current player picture, and stats

		//button = new JButton("Long-Named Button 4");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		//pane.add(button, c);

		//button = new JButton("5");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 2;       //third row
		//pane.add(button, c);


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
