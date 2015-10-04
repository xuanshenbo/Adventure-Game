/**
 * This Strategy makes the Zombie run towards the nearest cave
 */

package model.npcs;

import model.state.Position;
import static utilities.PrintTool.p;

public class RunZombie implements ZombieStrategy {

	@Override
	public Position move(Position oldPosition) {
		Position caveEntrance = oldPosition.getArea().getNearestCaveEntrance(oldPosition);
		int oldX = oldPosition.getX();
		int oldY = oldPosition.getY();
		int caveX = caveEntrance.getX();
		int caveY = caveEntrance.getY();
		
		Position newPosition;
		
		if(oldX > caveX){
			//move left if possible
			newPosition = new Position (oldX-1, oldY, oldPosition.getArea());
			if(newPosition.isValid()){
				return newPosition;
			}
		}else if (oldX < caveX){
			//move right is possible
			newPosition = new Position (oldX+1, oldY, oldPosition.getArea());
			if(newPosition.isValid()){
				return newPosition;
			}
		}else if(oldY > caveY){
			//move up if possible
			newPosition = new Position (oldX, oldY-1, oldPosition.getArea());
			if(newPosition.isValid()){
				return newPosition;
			}
		}else if(oldY < caveY){
			//move down if possible
			newPosition = new Position (oldX, oldY+1, oldPosition.getArea());
			if(newPosition.isValid()){
				return newPosition;
			}
		}
		//nothing is possible so now move either left or right to get around the obstacle
		
		//try right
		int newX = oldX+1;
		if(newX > oldPosition.getArea().getArea()[0].length){
			newX = oldPosition.getArea().getArea()[0].length;
		}
		newPosition = new Position(newX, oldY, oldPosition.getArea());
		if(newPosition.isValid()){
			return newPosition;
		}
		//or left
		newX = oldX-1;
		if(newX < 0){
			newX = 0;
		}
		newPosition = new Position(newX, oldY, oldPosition.getArea());
		if(newPosition.isValid()){
			return newPosition;
		}
		
		return oldPosition;
	}

}
