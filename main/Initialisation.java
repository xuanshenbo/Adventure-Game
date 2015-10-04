package main;

import interpreter.ButtonStrategy;
import interpreter.InitialStrategy;
import interpreter.KeyStrategy;
import interpreter.MenuStrategy;
import interpreter.StrategyInterpreter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
					closeServer();
					System.exit(0);
				}
			}
		});


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




	public void closeServer() {
		Main.closeServer();

	}
}
