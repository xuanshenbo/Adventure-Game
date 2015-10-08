package interpreter;

import interpreter.Translator.Command;

import java.io.IOException;
import java.util.Scanner;

import javax.xml.bind.JAXBException;

import dataStorage.Serializer;

public class MenuStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	public MenuStrategy(StrategyInterpreter menuInterpreter) {
		this.interpreter = menuInterpreter;
	}

	@Override
	public void notify(String text) {

		if(text.equals("save")){
			System.out.println("Saving the game...");
			try {
				interpreter.getClient().send("Save");
			} catch (IOException e) {
				e.printStackTrace();
			}
//			System.out.println("Saving the game...");
//			try {
//				Serializer.serialize(interpreter.getGame().getGameState());
//			} catch (JAXBException ex) {
//				System.out.println("Saving failed...");
//				return;
//			}
//			System.out.println("Done!");
			//notify game
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
