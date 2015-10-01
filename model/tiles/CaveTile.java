package model.tiles;

import model.state.Player;
import model.state.Position;

public class CaveTile implements Tile {

	private char id = 'C';

	@Override
	public void move(Player player, int direction) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isGround() {
		return false;
	}
}
