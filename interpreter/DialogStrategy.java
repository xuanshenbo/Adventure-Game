package interpreter;

import interpreter.Translator.Command;
import GUI.*;

public class DialogStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	@Override
	public void notify(String text) {
		if(Avatar.isAvatar(text)){
			//tell Model which Avatar has been selected
		}

	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}

	@Override
	public void notify(Command cmd) {
		// TODO Auto-generated method stub

	}

}
