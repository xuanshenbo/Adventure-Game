/**
 * This is an area of the game
 */

package state;

import java.util.Random;

public class Area {

	private final Tile[][] area; // the area that is made up of individual tiles
	private Type type;

	public enum Type {CAVE, ROOM, OUTSIDE}

	public Area(Type t){
		this.type = t;
		area = new Tile[10][10];
		for(int row = 0; row< area.length; row++){
			for(int col = 0; col< area[0].length; col++){
				int rand =  new Random().nextInt(19);
				if(rand > 18){
					area[row][col] = new Tile(Tile.Type.TREE);
				}else{
					area[row][col] = new Tile(Tile.Type.GRASS);
				}
			}
		}
	}

}
