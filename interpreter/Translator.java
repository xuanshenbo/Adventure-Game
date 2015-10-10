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
		DROP("D"), USE("U"), MOVE_ITEM("V"), ITEM_SELECTED("C"), DISPLAY_INVENTORY("I"), EXIT("DECIDE"), DISPLAY_CONTAINER("DECIDE"), PICK_UP("P"),
		DISPLAY_ITEM_OPTIONS("DECIDE"), DISPLAY_AVATAR_OPTIONS("DECIDE"), MOVE_WEST("MW"), MOVE_EAST("ME"), MOVE_NORTH("MN"), MOVE_SOUTH("MS");

		public String code = "";
		private Command(String c){
			code = c;
		}
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
		return command.code;
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