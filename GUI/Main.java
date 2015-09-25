package GUI;

import interpreter.ButtonStrategy;
import interpreter.KeyStrategy;
import interpreter.StrategyInterpreter;

/**
 * This class to be used for testing the GUI and interpreter packages
 * @author flanagdonn
 *
 */
public class Main {

	public static void main(String[] args) {
		GameFrame game = new GameFrame("Adventure Game");

		//create the Strategy Interpreters with different Strategies as appropriate
		StrategyInterpreter keyInterpreter = new StrategyInterpreter(game, new KeyStrategy());
		StrategyInterpreter buttonInterpreter = new StrategyInterpreter(game, new ButtonStrategy());

		//add the Strategy Interpreters to the GameFrame
		game.setKeyInterpreter(keyInterpreter);
		game.setButtonInterpreter(buttonInterpreter);

	}

}
