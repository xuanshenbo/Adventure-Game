package interpreter;

import java.util.Scanner;

public class MenuStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	@Override
	public void notify(String text) {
		Scanner sc = new Scanner(text);
		String command = sc.next();
		String filename = sc.next();

		if(command.equals("open")){
			//notify game
		}
		else if(command.equals("save")){
			//notify game
		}
	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}
}
