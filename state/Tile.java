/**
 * This is a single tile in the game
 */

package state;

import static utilities.PrintTool.p;

public class Tile {

	public enum TileType {TREE}
	private char id;

	private TileType type;

	public Tile(TileType t) {
		this.type = t;
		if(t == TileType.TREE){
			id = 'T';
		}
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}
	
	@Override
	public String toString(){
		return Character.toString(id);
	}
}
