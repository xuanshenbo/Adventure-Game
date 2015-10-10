package interpreter;
import interpreter.Translator.Command;

import java.io.IOException;
import java.io.Writer;

import view.GameFrame;
import model.logic.Game;
import control.Client;

/**
 * An implementation of the Strategy design pattern. A StrategyInterpreter acts as an Observer
 * and behaves differently depending on what the Strategy is.
 */
public class StrategyInterpreter implements Observer{

	private GameFrame gameGUI;
	private Strategy strategy;
	private Client client;

	private Game game;

	/**
	 * Stores the arguments in the fields
	 * @param game The Game
	 * @param gameFrame The BoardFrame
	 * @param s The Strategy
	 */
	public StrategyInterpreter(GameFrame game, Strategy s, Client c){
		gameGUI = game;
		strategy = s;
		client = c;;
		s.setInterpreter(this);
	}

	/**
	 * The interface which ensures different behaviour when notify is called
	 */
	public interface Strategy{
		public void notify(String text) throws IOException;
		public void setInterpreter(StrategyInterpreter i);
	}

	/**
	 * Notifies the game of the user events or game instructions
	 * @throws IOException
	 */
	public void notify(String text) throws IOException{
		strategy.notify(text);
	}

	/**
	 * @return The Strategy
	 */
	public Strategy getStrategy(){
		return strategy;
	}

	/**
	 * a getter for the client
	 * @return client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * a setter for the client
	 * @param output
	 */
	public void setClient(Client c) {
		client = c;
	}

	public void setGame(Game game) {
		this.game = game;

	}
	public Game getGame(){
		return this.game;
	}


}
