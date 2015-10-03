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

//		if(text.equals("up")){
//			interpreter.getClient().send("dirup");
//		}
//		else if(text.equals("down")){
//			interpreter.getClient().send("dirdown");
//		}
//		else if(text.equals("left")){
//			interpreter.getClient().send("dirleft");
//		}
//		else if(text.equals("right")){
//			interpreter.getClient().send("dirright");
//		}
	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}

}
