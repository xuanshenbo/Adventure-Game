package model.tiles;

import model.logic.Game.Direction;
import model.state.Player;
import model.state.Position;

public class ChestTile implements Tile {

	private char id = 'O';
	private Position position;

	public ChestTile(Position position){
		this.position = position;
	}

	@Override
	public void move(Player player, Direction direction) {
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
