package interpreter;

public class TestKeyStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	@Override
	public void notify(String text) {
		if(text.equals("UP")){
			//send code to Model through network
		}
	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}

}
