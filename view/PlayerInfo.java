package view;

/**
 * A simple class to store information about the player, which is used to display the PlayerProfilePanel
 * @author flanagdonn
 *
 */
public class PlayerInfo {
	private String playerName;
	private Avatar avatar;
	private int lifeline = 100;

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
		return lifeline;
	}

	public void setLifeline(int value){
		lifeline = value;
	}



}
