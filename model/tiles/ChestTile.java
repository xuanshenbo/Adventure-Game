package model.tiles;

import model.state.Player;
import model.state.Position;

public class ChestTile implements Tile {

	private char id = 'O';

	@Override
	public void move(Player player, int direction) {
//		if(player.hasKey()){
//			player.position = position;
//		}
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
		return null;
	}

	@Override
	public boolean isGround() {
		return false;
	}

}
