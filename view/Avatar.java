package view;

import interpreter.Translator.Command;

/**
 * Represents the different Avatar options for a player
 * @author flanagdonn
 *
 */
public enum Avatar{
	DONALD_DUCK, MICKEY_MOUSE, HAIRY_MACLARY, MUFFIN_MACLAY;
	/**
	 * Works out which integer the avatar String corresponds to.
	 * @return an integer corresponding to the avatar to draw on screen
	 */
	public static int getAvatarAsInteger(Avatar a){

		if(a==DONALD_DUCK){
			return 1;
		}
		else if(a==MICKEY_MOUSE){
			return 2;
		}
		else if(a==HAIRY_MACLARY){
			return 3;
		}
		else if(a==MUFFIN_MACLAY){
			return 4;
		}

		//shouldn't get here
		return -1;
	}

	public static Avatar getAvatarFromInt(int i){
		switch (i){
		case 1: return Avatar.DONALD_DUCK;
		case 2: return Avatar.MICKEY_MOUSE;
		case 3: return Avatar.HAIRY_MACLARY;
		case 4: return Avatar.MUFFIN_MACLAY;
		default: return null;

		}
	}
	@Override
	/**
	 * Returns the String associated with this enum instance
	 */
	public String toString(){
		String name = "";

		if(this.equals(DONALD_DUCK)){
			name = "Donald Duck";
		}
		else if(this.equals(MICKEY_MOUSE)){
			name = "Mickey Mouse";
		}
		else if(this.equals(HAIRY_MACLARY)){
			name = "Hairy Maclary";
		}
		else if(this.equals(MUFFIN_MACLAY)){
			name = "Muffin Maclay";
		}

		return name;

	}

	/**
	 * Used to interpret user input
	 * @param text The name of the possible avatar
	 * @return true if the name corresponds correctly with one of the 4 avatar options
	 */
	public static boolean isAvatar(String text) {
		return(text.equals(DONALD_DUCK.toString()) ||
				text.equals(MICKEY_MOUSE.toString()) ||
				text.equals(HAIRY_MACLARY.toString()) ||
				text.equals(MUFFIN_MACLAY.toString()));
	}

	public static Avatar toAvatar(String text) {
		//iterate through each value in Avatar
		for(Avatar a : Avatar.values()){
			if(a.toString().equals(text)){
				return a;
			}
		}

		//no matching value found
		return null;
	}


}
