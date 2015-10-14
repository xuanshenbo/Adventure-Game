package interpreter;

import interpreter.Translator.Command;

import java.io.IOException;

import main.Main;

/**
 * An implementation of the Observer and Strategy design patterns, combined to
 * allow strict observation of the overarching MVC design pattern
 * The KeyStrategy interprets Key ActionEvents, uses the Translator to encode an appropriate message
 * which is then sent to the Model via the Network.
 * @author flanagdonn
 */
public class KeyStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	/**
	 * Assigns the key interpreter to the strategy interpreter field.
	 * @param keyInterpreter The Interpreter using this strategy
	 */
	public KeyStrategy(StrategyInterpreter keyInterpreter) {
		interpreter = keyInterpreter;
	}

	/**
	 * Sends information to the "Model" via the network, depending on what key the user pressed
	 * @throws IOException
	 */
	@Override
	public void notify(String text) throws IOException {
		if(Translator.isCommand(text)){
			notifyCommand(text);
		}
	}

	private void notifyCommand(String text) {
		Command cmd = Translator.toCommand(text);
		String msg = Translator.encode(cmd);
		try {
			interpreter.getClient().send(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Sets this interpreter
	 * @param i The interpreter using this strategy
	 */
	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}

}
