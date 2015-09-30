package interpreter;

import java.util.Scanner;

import javax.xml.bind.JAXBException;

import dataStorage.Serializer;

public class MenuStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	@Override
	public void notify(String text) {
		Scanner sc = new Scanner(text);
		String command = sc.next();
		String filename = sc.next();

		if(command.equals("open")){
			//notify game passing it the filename

		}
		else if(command.equals("save")){
			System.out.println("Saving the game...");
			try {
				Serializer.serialize(interpreter.getGame().getGameState());
			} catch (JAXBException ex) {
				System.out.println("Saving failed...");
				return;
			}
			System.out.println("Done!");
			//notify game
		}
	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}
}
