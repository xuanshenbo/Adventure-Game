/**
 * This is the world class that holds a 2d array of areas.
 */

package state;

public class World {

	private final Area[][] world; // the main world made up of areas

	public World(){
		world = new Area[5][5];
		for(int row = 0; row< world.length; row++){
			for(int col = 0; col< world[0].length; col++){
				world[row][col] = new Area(Area.Type.OUTSIDE);
			}
		}
	}
}
