package state;

import org.junit.Test;
import logic.Generator;

public class StateTests {


	/**
	 * Testing the dealing
	 */
	@Test
	public void generateWorldTest01(){
		Area w = create(1, 1, 0, 0, 0, 0);
		Tile[][] wArray = w.getTileArray();
		assert(wArray[0][0].getType() == Tile.TileType.TREE);
	}

	public Area create(int size, int trees, int buildings, int caves, int chests, int lootValue) {
		Area w = new Area(size, size, Area.AreaType.OUTSIDE, null);
		Generator g = new Generator(trees, buildings, caves, chests, lootValue);
		w.generateWorld(g);
		return w;
	}
}
