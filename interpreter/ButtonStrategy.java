package interpreter;

import interpreter.Translator.Command;

import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import main.Main;
import static utilities.PrintTool.p;

public class ButtonStrategy implements StrategyInterpreter.Strategy{

	private StrategyInterpreter interpreter;

	public ButtonStrategy(StrategyInterpreter buttonInterpreter) {
		this.interpreter = buttonInterpreter;
	}

	private int selectedItem = -1;

	private int moveTo = -1;

	private boolean isMove;

	@Override
	public void notify(String text) {
		if(Translator.isCommand(text)){
			notifyCommand(text);
		}

		//if this action has been made to an item, work out what the action is
		if(text.startsWith("item")){
			Scanner sc = new Scanner(text);

			//this should be 'item'
			String reference = sc.next();

			//this should be 'drop' 'moveToBag' or 'use'
			String command = sc.next();
			p(reference);
			p(command);

			try {
				interpreter.getClient().send(Translator.encode(Translator.toCommand(command)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if(text.startsWith("selected")){
			Scanner sc = new Scanner(text);

			//this should be 'selected'
			String reference = sc.next();

			if(isMove){	//this should only be true after selectedItem has already been assigned
				moveTo = sc.nextInt();
				System.out.println("move to assigned");
			}
			else{
				this.selectedItem = sc.nextInt();
				System.out.println("selected item assigned");
			}
		}

	}


	private void notifyCommand(String text) {
		Scanner sc = new Scanner(text);
		String commandString = sc.next();

		Translator.Command cmd = Translator.toCommand(commandString);
		if(cmd.equals(Translator.Command.DISPLAY_INVENTORY)){
			String msg = Translator.encode(cmd);
			try {
				interpreter.getClient().send(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if(cmd.equals(Translator.Command.MOVE_ITEM)){
			if(moveTo != -1){

				//shouldn't get here
				if(selectedItem == -1){
					isMove = false;
					moveTo = -1;
					return;
				}
				String msg = Translator.encode(cmd);

				msg += selectedItem; //add the index of the item being moved
				msg += moveTo; //add the new index for the item being moved

				try {
					interpreter.getClient().send(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}

				//reset the two indices
				selectedItem = -1;
				moveTo = -1;
				isMove = false;
			}
			else{
				isMove = true;
			}
		}

		//if the player is trying to perform a non-move action on an item
		else if(cmd.equals(Translator.Command.DROP) ||
				cmd.equals(Translator.Command.USE)){

			//if they have selected an item
			if(selectedItem!=-1){
				//encode the action and append the inventory slot of the selected item
				String msg = Translator.encode(cmd);
				msg += selectedItem;
				//send encoded message via network
				try {
					interpreter.getClient().send(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}

				//set selectedSlot back to -1
				selectedItem = -1;

			}
		}

	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.interpreter = i;

	}

}
