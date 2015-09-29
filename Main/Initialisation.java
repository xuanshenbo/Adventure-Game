package Main;

import interpreter.ButtonStrategy;
import interpreter.InitialStrategy;
import interpreter.KeyStrategy;
import interpreter.MenuStrategy;
import interpreter.StrategyInterpreter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

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
		super(null, new InitialStrategy());
		frame = new JFrame("Welcome to Adventure Game");
		WelcomePanel welcome = new WelcomePanel(this);
		frame.add(welcome);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void displayMainGameFrame(){
		frame.dispose();	//get rid of welcome frame

		GameFrame game = new GameFrame("Adventure Game");

		//create the Strategy Interpreters with different Strategies as appropriate
		StrategyInterpreter keyInterpreter = new StrategyInterpreter(game, new KeyStrategy());
		StrategyInterpreter buttonInterpreter = new StrategyInterpreter(game, new ButtonStrategy());
		StrategyInterpreter menuInterpreter = new StrategyInterpreter(game, new MenuStrategy());

		//add the Strategy Interpreters to the GameFrame
		game.setKeyInterpreter(keyInterpreter);
		game.setButtonInterpreter(buttonInterpreter);
		game.setMenuInterpreter(menuInterpreter);
	}



}
