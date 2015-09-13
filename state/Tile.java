/**
 * This is a single tile in the game
 */

package state;

public class Tile {

	public enum TileType {TREE}

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
}
