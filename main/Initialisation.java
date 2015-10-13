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

import view.frames.GameFrame;
import view.frames.WelcomeFrame;
import view.frames.YesNoOptionWindow;
import view.panels.WelcomePanel;
import view.utilities.Avatar;
import model.logic.Game;
import control.Client;
import control.Server;

/**
 * The following initialises a game. It asks user to choose from creating a client or a server together with a client.
 * @author yanlong, flanagdonn
 *
 */
public class Initialisation extends StrategyInterpreter{

	private WelcomeFrame frame;
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

		frame = new WelcomeFrame("Welcome to Adventure Game", this);

		welcome = new WelcomePanel(this);

		frame.add(welcome);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);


	}

/**
 * Used for determining what avatar options to display to the user
 * for selection
 * @return A list of the available avatars
 */
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

}
