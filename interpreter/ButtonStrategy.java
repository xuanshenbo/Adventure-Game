package interpreter;

import java.io.Writer;

public class ButtonStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	public ButtonStrategy(StrategyInterpreter buttonInterpreter) {
		this.interpreter = buttonInterpreter;
	}

	@Override
	public void notify(String text) {


	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}

}
