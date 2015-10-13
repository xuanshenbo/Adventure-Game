package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import model.logic.Game.Direction;
import model.state.Player;
import model.state.Position;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DoorTile implements Tile {


	private char id = 'D';
	private Position entry;
	private Position exit;

	public DoorTile(Position entry, Position exit){
		this.entry = entry;
		this.exit = exit;
	}

	@SuppressWarnings("unused")
	private DoorTile() {
		this(null, null);
	}

	@Override
	public void move(Player player, Direction direction) {
		if(player.getPosition() == exit){
			player.setPosition(entry);
		}else{
			player.setPosition(exit);
		}

	}

	@Override
	public void interact(Player player) {

	}

	@Override
	public char getType() {
		return id;
	}

	public String toString(){
		return Character.toString(id);
	}

	@Override
	public Position getPosition() {
		return entry;
	}

	@Override
	public boolean isGround() {
		return false;
	}

	@Override
	public boolean isContainer() {
		return false;
	}

}
