package GUI;

import java.io.Writer;

import interpreter.ButtonStrategy;
import interpreter.DialogStrategy;
import interpreter.KeyStrategy;
import interpreter.MenuStrategy;
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
		StrategyInterpreter keyInterpreter = new StrategyInterpreter(game, new KeyStrategy(), null);
		StrategyInterpreter buttonInterpreter = new StrategyInterpreter(game, new ButtonStrategy(), null);
		StrategyInterpreter menuInterpreter = new StrategyInterpreter(game, new MenuStrategy(), null);

		//add the Strategy Interpreters to the GameFrame
		game.setKeyInterpreter(keyInterpreter);
		game.setButtonInterpreter(buttonInterpreter);
		game.setMenuInterpreter(menuInterpreter);

	}

}
