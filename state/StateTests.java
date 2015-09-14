package state;

import org.junit.Test;
import logic.Generator;

public class StateTests {


	/**
	 * Testing the dealing
	 */
	@Test
	public void generateWorldTest01(){
		Area w = create(1, 1, 0);
		Tile[][] wArray = w.getArea();
		assert(wArray[0][0].getType() == Tile.TileType.TREE);
	}

	public Area create(int size, int trees, int buildings) {
		Area w = new Area(size, size, Area.AreaType.OUTSIDE);
		Generator g = new Generator(trees, buildings);
		w.generateWorld(g);
		return w;
	}
}
