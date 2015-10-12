package model.tiles;

import javax.xml.bind.annotation.XmlRootElement;

import model.logic.Game.Direction;
import model.state.Player;
import model.state.Position;

@XmlRootElement
public class CaveEntranceTile implements Tile {

	private char id = 'E';
	private Position entry;
	private Position exit;

	public CaveEntranceTile(Position entry, Position exit){
		this.entry = entry;
		this.exit = exit;
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
		// TODO Auto-generated method stub

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
