package GUI;

import interpreter.*;

/**
 * This class to be used for testing the GUI and interpreter packages
 * @author flanagdonn
 *
 */
public class Main {

	public static void main(String[] args) {
		GameFrame game = new GameFrame("Adventure Game");
		StrategyInterpreter keyInterpreter = new StrategyInterpreter(game, new TestKeyStrategy());
		game.setKeyInterpreter(keyInterpreter);


	}

}
