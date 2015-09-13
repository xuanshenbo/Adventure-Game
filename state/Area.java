/**
 * This is the area class that holds a 2d array of tiles.
 * To create a new Area you will need to give it a height and
 * width. To generate a new random area you will need to create
 * a generator and call generateWorld
 */

package state;

import java.util.Random;
import static utilities.PrintTool.p;

import state.Tile.TileType;
import logic.Generator;

public class Area {

	public enum AreaType {OUTSIDE, BUILDING, CAVE}

	private final AreaType type; // the type of area this is and determines the ground texture
	private final Tile[][] area; // the main world made up of areas



	public Area(int height, int width, AreaType t){
		type = t;
		area = new Tile[height][width];
	}

	public AreaType getType() {
		return type;
	}

	public Tile[][] getArea() {
		return area;
	}

	public void generateWorld(Generator g){
		for(int row=0; row < area.length; row++){
			for(int col=0; col < area[0].length; col++){
				//generate trees
				if(new Random().nextInt(g.treeRatio()) == g.treeRatio()-1){
					area[row][col] = new Tile(TileType.TREE);
				}
			}
		}
	}
}
