/**
 * This class is a simple wrapper class to hold the position of something
 * in the world.
 */

package state;

public class Position {

	private int areaX;
	private int areaY;

	public Position(int ax, int ay){
		this.areaX = ax;
		this.areaY = ay;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + areaX;
		result = prime * result + areaY;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (areaX != other.areaX)
			return false;
		if (areaY != other.areaY)
			return false;
		return true;
	}

}
