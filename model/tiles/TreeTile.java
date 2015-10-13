package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import model.logic.Game.Direction;
import model.state.Player;
import model.state.Position;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TreeTile implements Tile {

	private Position position;
	private char id = 'T';

	public TreeTile(Position position){
		this.position = position;
	}

	@SuppressWarnings("unused")
	private TreeTile() {
		this(null);
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
