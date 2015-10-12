package model.tiles;

import javax.xml.bind.annotation.XmlRootElement;

import model.logic.Game.Direction;
import model.state.Player;
import model.state.Position;

@XmlRootElement
public class TreeTile implements Tile {

	private Position position;
	private char id = 'T';

	public TreeTile(Position position){
		this.position = position;
	}

	@Override
	public void move(Player player, Direction direction) {

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
		return position;
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
