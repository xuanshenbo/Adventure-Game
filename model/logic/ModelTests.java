package model.logic;

import org.junit.Test;

import model.state.Area;
import model.state.Area.AreaType;
import model.tiles.Tile;

public class ModelTests {


	/**
	 * Testing the dealing
	 */
	@Test
	public void generateWorldTest01(){
		Area w = create(1, 1, 0, 0, 0, 0);
		Tile[][] wArray = w.getArea();
		assert(wArray[0][0].getType() == 'T');
	}

	public Area create(int size, int trees, int buildings, int caves, int chests, int lootValue) {
		Area w = new Area(size, size, Area.AreaType.OUTSIDE, null);
		Generator g = new Generator(trees, buildings, caves, chests, lootValue);
		w.generateWorld(g);
		return w;
	}
}
