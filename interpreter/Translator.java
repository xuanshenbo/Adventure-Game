package interpreter;

public class Translator {

	public static String encode(String command) {
		if(command.equals("drop")){
			return "D";
		}
		else if(command.equals("use")){
			return "U";
		}
		else if(command.equals("moveToBag")){

		}
		return null;
	}

}
