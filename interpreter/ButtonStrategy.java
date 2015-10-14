package interpreter;

import interpreter.Translator.Command;

import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import main.Main;
import static utilities.PrintTool.p;


/**
 * An implementation of the Observer and Strategy design patterns, combined to
 * allow strict observation of the overarching MVC design pattern
 * The ButtonStrategy governs interaction between the users and the buttons
 * @author flanagdonn
 */
public class ButtonStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	/**
	 * Assigns the button interpreter object as this strategy's interpreter
	 * @param buttonInterpreter The StrategyInterpreter using this strategy
	 */
	public ButtonStrategy(StrategyInterpreter buttonInterpreter) {
		this.interpreter = buttonInterpreter;
	}

	private int selectedItem = -1;

	private int moveTo = -1;

	private boolean isMove;

	/**
	 * This method performs the logic required after the user presses a button.
	 * Often this involves sending an appropriately encoded message
	 * across the network.
	 */
	@Override
	public void notify(String text) {
		if(Translator.isCommand(text)){
			notifyCommand(text);
		}

		else if(text.startsWith("selected")){
			Scanner sc = new Scanner(text);

			//this should be 'selected'
			String reference = sc.next();

			if(isMove){	//this should only be true after selectedItem has already been assigned
				moveTo = sc.nextInt();
			}
			else{
				this.selectedItem = sc.nextInt();
			}
		}

	}


	private void notifyCommand(String text) {
		Scanner sc = new Scanner(text);
		String commandString = sc.next();
		String msg = "";

		Command cmd = Translator.toCommand(commandString);
		if(cmd.equals(Command.DISPLAY_INVENTORY)){
			msg = Translator.encode(cmd);
		}

		else if(cmd.equals(Command.MOVE_ITEM)){
			if(moveTo != -1){

				//shouldn't get here
				if(selectedItem == -1){
					isMove = false;
					moveTo = -1;
					return;
				}
				msg = Translator.encode(cmd);

				msg += selectedItem; //add the index of the item being moved
				msg += moveTo; //add the new index for the item being moved

				//reset the two indices
				selectedItem = -1;
				moveTo = -1;
				isMove = false;

				//send the encoded message via the network
				try {
					interpreter.getClient().send(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
				//now that move has been completed, close the inventory window
				if(interpreter.getDialog() != null){
					interpreter.getDialog().dispose();
				}

			}
			else{
				isMove = true;
				return; //don't want to sent to network until we know where they want to move the item to.
			}
		}

		else if(cmd.equals(Command.MOVE_ITEM_TO_INVENTORY)){
			msg = Translator.encode(cmd);
			msg += selectedItem;

		}

		//if the player is trying to perform a non-move action on an item
		else if(cmd.equals(Command.DROP) ||
				cmd.equals(Command.USE)){
			p("client sending use");

			//if they have selected an item
			if(selectedItem!=-1){
				//encode the action and append the inventory slot of the selected item
				msg = Translator.encode(cmd);
				msg += selectedItem;

				//set selectedSlot back to -1
				selectedItem = -1;

			}
		}

		//send the encoded message via the network
		try {
			interpreter.getClient().send(msg);
		} catch (IOException e) {
			e.printStackTrace();
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
