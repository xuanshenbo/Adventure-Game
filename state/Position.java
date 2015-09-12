/**
 * This class is a simple wrapper class to hold the position of something
 * in the world.
 */

package state;

public class Position {

	private int areaX;
	private int areaY;
	private int tileX;
	private int tileY;

	public Position(int ax, int ay, int tx, int ty){
		this.areaX = ax;
		this.areaY = ay;
		this.tileX = tx;
		this.tileY = ty;
	}

	public int getAreaX() {
		return areaX;
	}

	public void setAreaX(int areaX) {
		this.areaX = areaX;
	}

	public int getAreaY() {
		return areaY;
	}

	public void setAreaY(int areaY) {
		this.areaY = areaY;
	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

}
