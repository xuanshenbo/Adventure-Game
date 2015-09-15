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
		CHEST('O');
		public final char id;
		private TileType(char c){
			id = c;
		}		
	}
	private char id;

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
}
