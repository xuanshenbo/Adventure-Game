package interpreter;
import interpreter.Translator.Command;

import java.io.IOException;
import java.io.Writer;

import view.frames.Dialog;
import view.frames.GameFrame;
import model.logic.Game;
import control.Client;

/**
 * An implementation of the Strategy design pattern. A StrategyInterpreter acts as an Observer
 * and behaves differently depending on what the Strategy is.
 * @author flanagdonn
 */
public class StrategyInterpreter implements Observer{

	private GameFrame gameGUI;
	private Strategy strategy;
	private Client client;

	private Game game;
	private Dialog dialog;

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
		if( s!= null) s.setInterpreter(this);
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
	 * Get the game using this interpreter
	 * @return The game
	 */
	public Game getGame(){
		return this.game;
	}

	/**
	 * a setter for the client
	 * @param output
	 */
	public void setClient(Client c) {
		client = c;
	}

	/**
	 * Sets the Strategy
	 */
	public void setStrategy(StrategyInterpreter.Strategy s){
		this.strategy = s;
	}

	/**
	 * Assign the game to the field
	 * @param game The game field to be assigned
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * Assign the dialog to the field
	 * @return The dialog to be assigned
	 */
	public Dialog getDialog() {
		return dialog;
	}

	/**
	 * Set the containing dialog
	 * @param containerDialog The dialog which contains the panel using this interpreter
	 */
	public void setDialog(Dialog containerDialog) {
		dialog = containerDialog;
	}


}
