package tiles;

import state.Player;
import state.Position;

public class ChestTile implements Tile {

	private char id = 'O';

	@Override
	public void move(Player player, int direction) {

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
		// TODO Auto-generated method stub
		return null;
	}

}
