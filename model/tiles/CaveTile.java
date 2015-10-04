package model.tiles;

import model.logic.Game.Direction;
import model.state.Player;
import model.state.Position;

public class CaveTile implements Tile {

	private char id = 'C';
	private Position position;

	public CaveTile(Position position){
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
