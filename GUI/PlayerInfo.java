package GUI;

public class PlayerInfo {
	private String playerName;
	private Avatar avatar;

	public PlayerInfo(String pName, Avatar avatar){
		this.playerName = pName;
		this.avatar = avatar;
	}

	public String getName() {
		return playerName;
	}

	public Avatar getAvatar() {
		return avatar;
	}



}
