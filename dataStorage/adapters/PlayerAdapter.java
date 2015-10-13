package dataStorage.adapters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import model.items.Item;
import model.state.Player;
import model.state.Position;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerAdapter extends XmlAdapter<PlayerAdapter, Player> {

	private Position position;
	private int id;
	private Item[] inventory = new Item[6];
	private int happiness = 5;
	private Position startingPosition;

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

	public Item[] getInventory() {
		return inventory;
	}

	public void setInventory(Item[] inventory) {
		this.inventory = inventory;
	}

	public int getHappiness() {
		return happiness;
	}

	public void setHappiness(int happiness) {
		this.happiness = happiness;
	}

	public Position getStartingPosition() {
		return startingPosition;
	}

	public void setStartingPosition(Position startingPosition) {
		this.startingPosition = startingPosition;
	}

	@Override
	public Player unmarshal(PlayerAdapter adaptedPlayer) throws Exception {
		Player player = new Player(adaptedPlayer.getPosition(), adaptedPlayer.getId());
		player.setInventory(adaptedPlayer.getInventory());
		player.setHappiness(adaptedPlayer.getHappiness());
		player.setStartingPosition(adaptedPlayer.getStartingPosition());
		return player;
	}

	@Override
	public PlayerAdapter marshal(Player player) throws Exception {
		PlayerAdapter adaptedPlayer = new PlayerAdapter();
		adaptedPlayer.setPosition(player.getPosition());
		adaptedPlayer.setId(player.getId());
		adaptedPlayer.setInventory(player.getInventory());
		adaptedPlayer.setHappiness(player.getHappiness());
		adaptedPlayer.setStartingPosition(player.getStartingPosition());
		return adaptedPlayer;
	}

}
