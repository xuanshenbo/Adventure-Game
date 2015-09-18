/**
 * This is a single tile in the game
 */

package state;

import static utilities.PrintTool.p;

public class Tile {

	public enum TileType {
		TREE('T'),
		BUILDING('B'),
		DOOR('D'),
		CAVE('C'),
		CAVEENTRANCE('c'),
		CHEST('O'),
		GRASS('G'),
		ROCK('R'),
		WOOD('W');

		public final char id;
		private TileType(char c){
			id = c;
		}
	}

	private TileType type;

	public Tile(TileType t) {
		this.type = t;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	@Override
	public String toString(){
		return Character.toString(type.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (type != other.type)
			return false;
		return true;
	}
}
