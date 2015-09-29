/**
 * This class is a simple wrapper class to hold the position of something
 * in the world.
 */

package state;

public class Position {

	private Area a;
	private int x;
	private int y;

	public Position(int x, int y, Area a){
		this.x = x;
		this.y = y;
		this.a = a;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int areaY) {
		this.y = areaY;
	}

	@Override
	public String toString(){
		return "X:"+x+" Y:"+y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + x;
		result = prime * result + y;
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
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	// ================================================
	// getters from here
	// ================================================

	public Area getArea() {
		return a;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
