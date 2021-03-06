package interpreter;
import interpreter.Translator.Command;

import java.io.IOException;
import java.util.Scanner;

import javax.xml.bind.JAXBException;

import view.frames.GameFrame;
import view.frames.YesNoOptionWindow;
import main.Main;
import dataStorage.Serializer;

/**
 * An implementation of the Observer and Strategy design patterns, combined to
 * allow strict observation of the overarching MVC design pattern
 * The ButtonStrategy governs interaction between the users and the buttons
 * @author flanagdonn
 */
public class MenuStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;
	private GameFrame gameFrame;


	/**
	 * Assigns the interpreter, and game frame two fields.
	 * @param menuInterpreter The interpreter using this strategy
	 * @param g The GameFrame on which the menu bar lies
	 */
	public MenuStrategy(StrategyInterpreter menuInterpreter, GameFrame g) {
		this.interpreter = menuInterpreter;
		this.gameFrame = g;
	}

	/**
	 * This method performs the logic required after the user chooses a
	 * menu bar option.
	 * Often this involves sending an appropriately encoded message
	 * across the network.
	 */
	@Override
	public void notify(String text) {

		if(Translator.isCommand(text)){
			notifyCommand(text);
		}
	}

	private void notifyCommand(String text) {
		Command cmd = Translator.toCommand(text);
		if(cmd.equals(Command.EXIT)){
			if(Main.getServer() == null){

				Command exit = Command.EXIT_CLIENT;
				String msg = Translator.encode(exit);
				msg += interpreter.getClient().getUid();
				try {
					interpreter.getClient().send(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			Main.closeServer();
			System.exit(0);

		}

		else {
			System.out.println("Saving the game...");
			try {
				interpreter.getClient().send(Translator.encode(cmd));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Sets this interpreter
	 * @param i The interpreter using this strategy
	 */
	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}
}
