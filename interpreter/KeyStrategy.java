package interpreter;

import java.io.IOException;

public class KeyStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	/**
	 * Sends information to the "Model" via the network, depending on what key the user pressed
	 * @throws IOException
	 */
	@Override
	public void notify(String text) throws IOException {
		if(text.equals("up")){
			interpreter.getClient().send("MN");
		}
		else if(text.equals("down")){
			interpreter.getClient().send("MS");
		}
		else if(text.equals("left")){
			interpreter.getClient().send("MW");
		}
		else if(text.equals("right")){
			interpreter.getClient().send("ME");
		}
		else if(text.equals("pickUp")){
			interpreter.getClient().send("P");
		}
	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}

}
