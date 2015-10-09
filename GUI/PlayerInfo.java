package GUI;

/**
 * A simple class to store information about the player, which is used to display the PlayerProfilePanel
 * @author flanagdonn
 *
 */
public class PlayerInfo {
	private String playerName;
	private Avatar avatar;
	private int lifeline;

	public PlayerInfo(Avatar avatar){
		this.playerName = avatar.toString();
		this.avatar = avatar;
	}

	public String getName() {
		return playerName;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public int getLifeline() {
		// TODO Auto-generated method stub
		return 0;
	}



}
