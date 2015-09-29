package interpreter;

public class ButtonStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	@Override
	public void notify(String text) {


	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}

}
