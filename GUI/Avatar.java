package GUI;

/**
 * Represents the different Avatar options for a player
 * @author flanagdonn
 *
 */
public enum Avatar{
		DONALD_DUCK;


		/**
		 * Works out which integer the avatar String corresponds to.
		 * @return an integer corresponding to the avatar to draw on screen
		 */
		public static int getAvatarAsInteger(Avatar a){
			if(a==DONALD_DUCK){
				return 0;
			}
			return -1;
		}
}
