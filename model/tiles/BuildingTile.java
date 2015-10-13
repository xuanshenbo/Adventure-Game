package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import model.logic.Game.Direction;
import model.state.Player;
import model.state.Position;

@XmlSeeAlso({ BuildingAnchorTile.class })
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BuildingTile implements Tile {

	private char id = 'B';
	private Position position;

	public BuildingTile(Position position){
		this.position = position;
	}

	@Override
	public void move(Player player, Direction direction) {
		// TODO Auto-generated method stub

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
