package interpreter;

import interpreter.Translator.Command;
import interpreter.Translator.InitialisationCommand;

/**
 * This class stores various states and commands, and encodes them as Strings to be sent
 * through the network.
 * @author flanagdonn
 *
 */
public class Translator {

	/**
	 * These command
	 * @author flanagdonn
	 *
	 */
	public enum Command{
		DROP, USE, MOVE_ITEM, ITEM_SELECTED, DISPLAY_INVENTORY, EXIT, DISPLAY_CONTAINER, PICK_UP,
		DISPLAY_ITEM_OPTIONS, DISPLAY_AVATAR_OPTIONS, MOVE_WEST, MOVE_EAST, MOVE_NORTH, MOVE_SOUTH
	}

	public enum InitialisationCommand {
		SHOW_CLIENT_SERVER_OPTION, SHOW_LOAD_OR_NEW_OPTION, CONNECT_TO_SERVER,
		START_GAME, LOAD_GAME, CHOOSE_SLIDER_OPTIONS, LOAD_SAVED_PLAYER,
		CREATE_NEW_PLAYER, LOAD_PLAYER_OR_CREATE_NEW_PLAYER, SELECTED_AVATAR,
		SELECTED_CLIENT, SELECTED_CLIENT_AND_SERVER, SELECTED_NEW_GAME;
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
		else if(command.equals(Command.MOVE_EAST)){
			return "ME";
		}
		else if(command.equals(Command.MOVE_NORTH)){
			return "MN";
		}
		else if(command.equals(Command.MOVE_SOUTH)){
			return "MS";
		}
		else if(command.equals(Command.MOVE_WEST)){
			return "MW";
		}
		else if(command.equals(Command.PICK_UP)){
			return "P";
		}


		return null;
	}

	public static String encode(InitialisationCommand command) {

		if(command.equals(InitialisationCommand.SELECTED_AVATAR)){
			//
		}
		return null;
	}

	public static boolean isInitialisationState(String text) {

		//iterate through each value in InitialisationState
		for(InitialisationCommand s : InitialisationCommand.values()){
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

	public static InitialisationCommand toInitState(String text) {
		//iterate through each value in InitialisationState
		for(InitialisationCommand s : InitialisationCommand.values()){
			if(s.toString().equals(text)){
				return s;
			}
		}

		//no matching value found
		return null;
	}

}
