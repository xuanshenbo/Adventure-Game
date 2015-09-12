/**
 * This is a single tile in the game
 */

package state;

public class Tile {

	public enum Type {GRASS, TREE}

	private Type type;

	public Tile(Type t) {
		this.type = t;
	}

}
