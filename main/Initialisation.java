package main;

import interpreter.ButtonStrategy;
import interpreter.InitialStrategy;
import interpreter.KeyStrategy;
import interpreter.MenuStrategy;
import interpreter.StrategyInterpreter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Writer;

import javax.swing.JFrame;

import logic.Game;
import control.Client;
import GUI.GameFrame;
import GUI.WelcomePanel;

/**
 * The following initialises a game. It asks user to choose from creating a client or a server together with a client.
 * @author yanlong
 *
 */
public class Initialisation extends StrategyInterpreter{

	private JFrame frame;

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

	/**
	 * A getter for the JFrame
	 * @return
	 */
	public JFrame getFrame() {
		return frame;
	}

	public void displayMainGameFrame(Client c, Game g){
		frame.dispose();	//get rid of welcome frame

		GameFrame game = new GameFrame("Adventure Game", g);

		//create the Strategy Interpreters with different Strategies as appropriate
		StrategyInterpreter keyInterpreter = new StrategyInterpreter(game, new KeyStrategy(),c);
		StrategyInterpreter buttonInterpreter = new StrategyInterpreter(game, new ButtonStrategy(),c);
		StrategyInterpreter menuInterpreter = new StrategyInterpreter(game, new MenuStrategy(),c);

		//add the Strategy Interpreters to the GameFrame
		game.setKeyInterpreter(keyInterpreter);
		game.setButtonInterpreter(buttonInterpreter);
		game.setMenuInterpreter(menuInterpreter);
	}
}
