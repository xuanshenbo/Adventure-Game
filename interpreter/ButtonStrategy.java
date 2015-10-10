package interpreter;

import interpreter.Translator.Command;

import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class ButtonStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	public ButtonStrategy(StrategyInterpreter buttonInterpreter) {
		this.interpreter = buttonInterpreter;
	}

	@Override
	public void notify(String text) {

		//if this action has been made to an item, work out what the action is
		if(text.startsWith("item")){
			Scanner sc = new Scanner(text);

			//this should be 'item'
			String reference = sc.next();

			//this should be 'drop' 'moveToBag' or 'use'
			String command = sc.next();

			try {
				interpreter.getClient().send(Translator.encode(Translator.toCommand(command)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}

}
