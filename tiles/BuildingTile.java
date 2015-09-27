package tiles;

import state.Player;
import state.Position;

public class BuildingTile implements Tile {

	private char id = 'B';

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



}
