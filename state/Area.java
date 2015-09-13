/**
 * This is the world class that holds a 2d array of tiles.
 */

package state;

import logic.Generator;

public class Area {

	public enum AreaType {OUTSIDE, BUILDING, CAVE}

	private final AreaType type; // the type of area this is and determines the ground texture
	private final Tile[][] world; // the main world made up of areas



	public Area(int height, int width, AreaType t){
		type = t;
		world = new Tile[height][width];
	}

	public AreaType getType() {
		return type;
	}

	public Tile[][] getWorld() {
		return world;
	}

	public void generateWorld(Generator g){

	}
}
