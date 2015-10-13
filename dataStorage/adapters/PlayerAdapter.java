package dataStorage.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import model.state.Player;
import model.state.Position;

public class PlayerAdapter extends XmlAdapter<PlayerAdapter, Player> {

	private Position position;
	private int id;

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public Player unmarshal(PlayerAdapter adaptedPlayer) throws Exception {
		return new Player(adaptedPlayer.getPosition(), adaptedPlayer.getId());
	}

	@Override
	public PlayerAdapter marshal(Player player) throws Exception {
		PlayerAdapter adaptedPlayer = new PlayerAdapter();
		adaptedPlayer.setId(player.getId());
		adaptedPlayer.setPosition(player.getPosition());
		return adaptedPlayer;
	}

}
