package interpreter;

public class KeyStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	@Override
	public void notify(String text) {
		if(text.equals("UP")){
			//send code to Model through network, using the interpreter?
		}
	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}

}
