package Main;

import interpreter.ButtonStrategy;
import interpreter.KeyStrategy;
import interpreter.MenuStrategy;
import interpreter.StrategyInterpreter;
import GUI.GameFrame;

/**
 * The following is the main class for the whole game.
 * @author yanlong
 *
 */
public class Main {

	/**
	 * Displays welcome dialog and set up interpreters, before displaying the main GameFrame
	 * @author flanagdonn
	 * @param args
	 */
	public static void main(String[] args) {

		new Initialisation();

		/*GameFrame game = new GameFrame("Adventure Game");

		//create the Strategy Interpreters with different Strategies as appropriate
		StrategyInterpreter keyInterpreter = new StrategyInterpreter(game, new KeyStrategy());
		StrategyInterpreter buttonInterpreter = new StrategyInterpreter(game, new ButtonStrategy());
		StrategyInterpreter menuInterpreter = new StrategyInterpreter(game, new MenuStrategy());

		//add the Strategy Interpreters to the GameFrame
		game.setKeyInterpreter(keyInterpreter);
		game.setButtonInterpreter(buttonInterpreter);
		game.setMenuInterpreter(menuInterpreter);*/
	}

}
