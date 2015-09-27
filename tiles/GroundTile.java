package tiles;

import state.Player;
import state.Position;

public class GroundTile implements Tile {

	public enum TileType {
		GRASS('G'),
		ROCK('R'),
		WOOD('W');

		public final char id;
		private TileType(char c){
			id = c;
		}
	}

	private TileType type;
	private Position position;

	public GroundTile(TileType t, Position position) {
		this.type = t;
		this.position = position;
	}


	@Override
	public void move(Player player, int direction) {
		player.setPosition(position);

	}

	@Override
	public void interact(Player player) {

	}

	@Override
	public char getType() {
		return type.id;
	}

	public String toString(){
		return Character.toString(type.id);
	}

	public Position getPosition(){
		return position;
	}

}
