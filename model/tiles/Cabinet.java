package model.tiles;

import model.state.Container;
import model.state.Player;
import model.state.Position;

public class Cabinet implements Container, Tile {

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
		// TODO Auto-generated method stub
		return 0;
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
