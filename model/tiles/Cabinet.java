package model.tiles;

import model.logic.Game.Direction;
import model.state.Container;
import model.state.Player;
import model.state.Position;

public class Cabinet implements Container, Tile {
	
	private Position position;

	public Cabinet(Position position){
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public boolean isGround() {
		return false;
	}

}
