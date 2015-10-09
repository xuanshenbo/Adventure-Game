package interpreter;

import interpreter.Translator.Command;
import interpreter.Translator.InitialisationState;

public class Translator {

	public enum Command{
		DROP, USE, MOVE_ITEM, ITEM_SELECTED, DISPLAY_INVENTORY, EXIT, DISPLAY_CONTAINER, DISPLAY_ITEM_OPTIONS
	}

	public enum InitialisationState {
		SHOW_CLIENT_SERVER_OPTION, SHOW_LOAD_OR_NEW_OPTION, CONNECT_TO_SERVER,
		START_GAME, LOAD_GAME, CHOOSE_SLIDER_OPTIONS, LOAD_SAVED_PLAYER,
		CREATE_NEW_PLAYER, LOAD_PLAYER_OR_CREATE_NEW_PLAYER;
	}

	public enum MainGameState {
MAIN
	}

	public static String encode(Command command) {

		if(command.equals(Command.DROP)){
			return "D";
		}
		else if(command.equals(Command.USE)){
			return "U";
		}
		else if(command.equals(Command.MOVE_ITEM)){
			return "V";
		}
		else if(command.equals(Command.ITEM_SELECTED)){
			return "C";
		}
		else if(command.equals(Command.DISPLAY_INVENTORY)){
			return "I";
		}
		//		else if(command.equals()){
		//			return "I";
		//		}

		return null;
	}

	public static boolean isInitialisationState(String text) {

		//iterate through each value in InitialisationState
		for(InitialisationState s : InitialisationState.values()){
			if(s.toString().equals(text)){
				return true;
			}
		}

		//no matching value found
		return false;
	}

	public static boolean isCommand(String text) {
		//iterate through each value in Command
		for(Command c : Command.values()){
			if(c.toString().equals(text)){
				return true;
			}
		}

		//no matching value found
		return false;
	}

	public static Command toCommand(String text) {
		//iterate through each value in Command
		for(Command c : Command.values()){
			if(c.toString().equals(text)){
				return c;
			}
		}

		//no matching value found
		return null;
	}

	public static InitialisationState toInitState(String text) {
		//iterate through each value in InitialisationState
		for(InitialisationState s : InitialisationState.values()){
			if(s.toString().equals(text)){
				return s;
			}
		}

		//no matching value found
		return null;
	}

}
