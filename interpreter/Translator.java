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
	 * These commands are used to determine which actions to perform
	 * or what to send through the network
	 * @author flanagdonn
	 *
	 */
	public enum Command{
		//"N/A" as the code indicated that this command isn't sent through the pipe, and thus doesn't need to be encoded
		DROP("D"), USE("U"), MOVE_ITEM("V"), ITEM_SELECTED("C"), DISPLAY_INVENTORY("I"),
		EXIT("DECIDE"), DISPLAY_CONTAINER("DECIDE"), PICK_UP("P"), 	DISPLAY_INVENTORY_ITEM_OPTIONS("DECIDE"),
		DISPLAY_AVATAR_OPTIONS("DECIDE"), MOVE_WEST("MW"), MOVE_EAST("ME"), MOVE_NORTH("MN"), MOVE_SOUTH("MS"),
		EXIT_CLIENT("Q"), ROTATE_VIEW("O"), DISPLAY_CONTAINER_ITEM_OPTIONS("N/A"), MOVE_ITEM_TO_INVENTORY("Z"),
		SAVE("S"), SAVE_AS("Y"), LOAD_FILE("L"), NOTIFY_USER_OF_MESSAGE("N/A"), DISPLAY_CONFIRM_OR_CANCEL_OPTION("N/A"),
		YES_SELECTED("N/A"), MAIN("N/A");

		public String code = "";
		private Command(String c){
			code = c;
		}
	}

	/**
	 * These commands are made during the setting-up stage,
	 * and help control the sequence of events
	 * @author flanagdonn
	 *
	 */
	public enum InitialisationCommand {
		SHOW_CLIENT_SERVER_OPTION, SHOW_LOAD_OR_NEW_OPTION, CONNECT_TO_SERVER,
		START_GAME, LOAD_GAME, CHOOSE_SLIDER_OPTIONS, LOAD_SAVED_PLAYER,
		CREATE_NEW_PLAYER, LOAD_PLAYER_OR_CREATE_NEW_PLAYER, SELECTED_AVATAR,
		SELECTED_CLIENT, SELECTED_CLIENT_AND_SERVER, SELECTED_NEW_GAME, GET_AVAILABLE_AVATARS;
	}


/**
 * Encodes the command with the code given in the command constructor
 * @param command The command to be encoded
 * @return The String which is this command's unique code
 */
	public static String encode(Command command) {
		return command.code;
	}


	/**
	 * Encodes the command with a pre-decided code
	 * @param command The command to be encoded
	 * @return The String which is this command's unique code
	 */
	public static String encode(InitialisationCommand command) {

		if(command.equals(InitialisationCommand.SELECTED_AVATAR)){
			return "J";
		}
		else if(command.equals(InitialisationCommand.GET_AVAILABLE_AVATARS)){
			return "R";
		}
		return null;
	}

	/**
	 * Decides whether this string corresponds to one of the InitialisationCommands
	 * @param text The text to be checked
	 * @return True iff the text corresponds to an InitialisationCommand
	 */
	public static boolean isInitialisationCommand(String text) {

		//iterate through each value in InitialisationState
		for(InitialisationCommand s : InitialisationCommand.values()){
			if(s.toString().equals(text)){
				return true;
			}
		}

		//no matching value found
		return false;
	}

	/**
	 * Decides whether this string corresponds to one of the Commands
	 * @param text The text to be checked
	 * @return True iff the text corresponds to an Command
	 */
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

	/**
	 * Takes a String and returns the command associated with it.
	 * @param text The text to be transformed into a command
	 * @return The Command corresponding with the text
	 */
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

	/**
	 * Takes a String and returns the InitialisationCommand associated with it.
	 * @param text The text to be transformed into an InitialisationCommand
	 * @return The InitialisationCommand corresponding with the text
	 */
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
