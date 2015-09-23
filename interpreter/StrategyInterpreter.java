package interpreter;
import GUI.GameFrame;

/**
 * An implementation of the Strategy design pattern. A StrategyInterpreter acts as an Observer
 * and behaves differently depending on what the Strategy is.
 */
public class StrategyInterpreter implements Observer{

	private GameFrame gameGUI;
	private Strategy strategy;

	/**
	 * Stores the arguments in the fields
	 * @param g The Game
	 * @param gameFrame The BoardFrame
	 * @param s The Strategy
	 */
	public StrategyInterpreter(GameFrame g, Strategy s){
		gameGUI = g;
		strategy = s;
		s.setInterpreter(this);
	}

	/**
	 * The interface which ensures different behaviour when notify is called
	 */
	public interface Strategy{
		public void notify(String text);
		public void setInterpreter(StrategyInterpreter i);
	}

	/**
	 * Notifies the game of the user events or game instructions
	 */
	public void notify(String text){
		strategy.notify(text);
	}

	/**
	 * @return The Strategy
	 */
	public Strategy getStrategy(){
		return strategy;
	}





}
