package interpreter;

public class Translator {

	public enum Command{
		DROP, USE, MOVE_ITEM, ITEM_SELECTED, DISPLAY_INVENTORY
	}

	public static String encode(String command) {

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

}
