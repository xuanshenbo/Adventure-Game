package tiles;

import state.Player;
import state.Position;


public class DoorTile implements Tile {


	private char id = 'D';
	private Position entry;
	private Position exit;

	public DoorTile(Position entry, Position exit){
		this.entry = entry;
		this.exit = exit;
	}

	@Override
	public void move(Player player, int direction) {
		if(player.getPosition() == exit){
			player.setPosition(entry);
		}else{
			player.setPosition(exit);
		}

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
