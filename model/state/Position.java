package model.state;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.sun.xml.internal.bind.CycleRecoverable;

import dataStorage.pointers.PositionPointer;

/**
 * This class is a simple wrapper class to hold the position of something
 * in the world.
 * @author tuckergare
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Position implements CycleRecoverable {

	private Area a;
	private int x;
	private int y;

	public Position(int x, int y, Area a){
		this.x = x;
		this.y = y;
		this.a = a;
	}

	private Position() {
	}

	/**
	 * This method checks if the tile is ground
	 * @return true or false if it is a ground tile.
	 */
	public boolean isValid() {
		return a.getArea()[y][x].isGround();
	}

	/**
	 * This method returns a list of valid positions to move from this position.
	 * @param area
	 * @return
	 */
	public List<Position> getValid() {
		List<Position> validPositions = new ArrayList<Position>();
		for(int dx = -1; dx < 2; dx++){
			for(int dy = -1; dy < 2; dy++){
				int newX = x+dx;
				int newY = y+dy;

				if(newX >= a.getArea()[0].length){ newX = a.getArea()[0].length -1;}
				if(newY >= a.getArea().length){ newY = a.getArea().length -1;}
				if(newX < 0){ newX = 0;}
				if(newY < 0){ newY = 0;}

				Position newPos = new Position(newX, newY, a);
				if(a.getArea()[newY][newX].isGround() && newPos != this && Math.abs(dx + dy) == 1){
					validPositions.add(newPos);
				}
			}
		}
		return validPositions;
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
	// getters and setters from here
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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int areaY) {
		this.y = areaY;
	}

	@Override
	public Object onCycleDetected(Context arg0) {
		PositionPointer positionPointer = new PositionPointer();
		positionPointer.setX(x);
		positionPointer.setY(y);
		return positionPointer;
	}
}
