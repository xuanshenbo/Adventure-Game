package tiles;

import state.Player;
import state.Position;


public class TreeTile implements Tile {

	private Position position;
	private char id = 'T';

	@Override
	public void move(Player player, int direction) {

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

}
