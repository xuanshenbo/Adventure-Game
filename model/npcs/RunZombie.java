

package model.npcs;

import model.state.Position;
import static utilities.PrintTool.p;

/**
 * This method makes the Zombie run towards the nearest cave. This is 
 * called when it is day time and the player is out of range
 * @author tuckergare
 *
 */
public class RunZombie implements ZombieStrategy {
	
	/**
	 * this moves the zombie towards the nearest cave in a really dumb way.
	 */
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

		return oldPosition;
	}

}
