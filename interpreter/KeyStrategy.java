package interpreter;

public class KeyStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	/**
	 * Sends information to the "Model" via the network, depending on what key the user pressed
	 */
	@Override
	public void notify(String text) {
		if(text.equals("up")){

		}
		else if(text.equals("down")){

		}
		else if(text.equals("left")){

		}
		else if(text.equals("right")){

		}
	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}

}
