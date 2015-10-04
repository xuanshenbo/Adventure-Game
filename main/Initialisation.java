package main;

import interpreter.ButtonStrategy;
import interpreter.InitialStrategy;
import interpreter.KeyStrategy;
import interpreter.MenuStrategy;
import interpreter.StrategyInterpreter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.JFrame;

import model.logic.Game;
import control.Client;
import GUI.Avatar;
import GUI.GameFrame;
import GUI.WelcomePanel;

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

	private InitialStrategy initStrategy = new InitialStrategy();

	/**
	 * Create an Initialisation object using the StrategyInterpreter super constructor
	 * Create a new frame in which to display the WelcomePanel
	 * @author flanagdonn
	 */
	public Initialisation(){
		super(null, new InitialStrategy(), null);
		frame = new JFrame("Welcome to Adventure Game");
		WelcomePanel welcome = new WelcomePanel(this);
		frame.add(welcome);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}




	public ArrayList<Avatar> getAvailableAvatars() {
		ArrayList<Avatar> avatars= new ArrayList<Avatar>();
		// ask Model for the available Avatars to display as options to the user

		avatars.add(Avatar.DONALD_DUCK);	//for testing purposes

		return avatars;
	}

	/**
	 * A getter for the JFrame
	 * @return
	 */
	public JFrame getFrame() {
		return frame;
	}



	public void displayMainGameFrame(){
		Main.displayMainGameFrame();
	}
}
