package main;

import interpreter.ButtonStrategy;
import interpreter.InitialStrategy;
import interpreter.KeyStrategy;
import interpreter.MenuStrategy;
import interpreter.StrategyInterpreter;
import interpreter.Translator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import view.Avatar;
import view.GameFrame;
import view.WelcomePanel;
import model.logic.Game;
import control.Client;
import control.Server;

/**
 * The following initialises a game. It asks user to choose from creating a client or a server together with a client.
 * @author yanlong
 *
 */
public class Initialisation extends StrategyInterpreter{

	private JFrame frame;
	private Main main;

	public final static int maxTrees = 100;
	public final static int maxBuildings = 20;

	private InitialStrategy initStrategy;

	private ArrayList<Avatar> avatars;

	private WelcomePanel welcome;

	private Avatar chosenAvatar;
	//private Server server;

	/**
	 * Create an Initialisation object using the StrategyInterpreter super constructor
	 * Create a new frame in which to display the WelcomePanel
	 * @author flanagdonn
	 */
	public Initialisation(){
		super(null, null, null);

		initStrategy =  new InitialStrategy(this);

		initStrategy.setInterpreter(this);

		setStrategy(initStrategy);

		frame = new JFrame("Welcome to Adventure Game");
		welcome = new WelcomePanel(this);
		frame.add(welcome);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		/*
		 *Prompt the user to confirm if they click the close button
		 */
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we) {

				String ObjButtons[] = {"Yes", "No"};
				int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Happiness Game",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					try {
						initStrategy.notify(Translator.Command.EXIT.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		//add a pink border around the whole frame
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.PINK));

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	public ArrayList<Avatar> getAvailableAvatars() {
		ArrayList<Avatar> avatarOptions = this.avatars;

		if(avatarOptions == null){
			avatarOptions = new ArrayList<Avatar>();
			//for testing purposes
			avatarOptions.add(Avatar.DONALD_DUCK);
			avatarOptions.add(Avatar.MICKEY_MOUSE);
			avatarOptions.add(Avatar.HAIRY_MACLARY);
			avatarOptions.add(Avatar.MUFFIN_MACLAY);
		}

		return avatarOptions;
	}

	/**
	 * Used to set the available avatar options
	 * @param avatars The available avatars for selection
	 */
	public void setAvatars(ArrayList<Avatar> avatars){
		this.avatars = avatars;
	}

	/**
	 * A getter for the JFrame
	 * @return
	 */
	public JFrame getFrame() {
		return frame;
	}



	/**
	 * The following will call the method in main to display the main game frame
	 */
	public void displayMainGameFrame(){
		try {
			Main.displayMainGameFrame();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * a getter for welcomePanel
	 * @return
	 */
	public WelcomePanel getWelcomePanel(){
		return welcome;
	}


	/**
	 * a setter for server
	 * @param server
	 */
	/*public void setServer(Server server) {
		this.server = server;
	}*/

	/**
	 * a getter for server
	 * @return
	 */
	/*public Server getServer() {
		return server;
	}*/

	/**
	 * Close the server so as to not have to manually terminate
	 */
	public void closeServer() {
		Main.closeServer();
	}


	public void setChosenAvatar(Avatar a) {
		chosenAvatar = a;
	}

	public Avatar getChosenAvatar(){
		return chosenAvatar;
	}



	/*public void closeServer() {
		Main.closeServer();

	}
	 */
}
