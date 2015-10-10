package interpreter;

import interpreter.Translator.Command;

import java.io.IOException;

public class RadioStrategy implements StrategyInterpreter.Strategy {

	private StrategyInterpreter radioInterpreter;

	public RadioStrategy(StrategyInterpreter radioInterp) {
		radioInterpreter = radioInterp;
	}

	@Override
	public void notify(String text) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		//TODO get rid of this in interface?

	}



}
