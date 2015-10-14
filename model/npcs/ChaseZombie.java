

package model.npcs;

import model.state.Position;
import static utilities.PrintTool.p;

/**
 * This Strategy makes the Zombie chase the player, the Zombie will
 * attack the player if they are in range and will reduce thier 
 * happiness. It is very stupid
 * @author tuckergare
 */

public class ChaseZombie implements ZombieStrategy {

	/**
	 * This moves the Zombie towards the player, the Zombie will move across
	 * until it is in the same column of the player and then move up or down.
	 */
	@Override
	public Position move(Position oldPosition) {
		Position player = oldPosition.getArea().getNearestPlayer(oldPosition);
		int oldX = oldPosition.getX();
		int oldY = oldPosition.getY();
		int playerX = player.getX();
		int playerY = player.getY();

		Position newPosition = oldPosition;

		if(oldX > playerX){
			//move left if possible
			newPosition = new Position (oldX-1, oldY, oldPosition.getArea());
			if(!newPosition.isValid()){
				newPosition = oldPosition;
			}
		}else if (oldX < playerX){
			//move right is possible
			newPosition = new Position (oldX+1, oldY, oldPosition.getArea());
			if(!newPosition.isValid()){
				newPosition = oldPosition;
			}
		}else if(oldY > playerY){
			//move up if possible
			newPosition = new Position (oldX, oldY-1, oldPosition.getArea());
			if(!newPosition.isValid()){
				newPosition = oldPosition;
			}
		}else if(oldY < playerY){
			//move down if possible
			newPosition = new Position (oldX, oldY+1, oldPosition.getArea());
			if(!newPosition.isValid()){
				newPosition = oldPosition;
			}
		}

		//p("Chase Zombie Moving from: "+oldPosition+" to: "+newPosition);
		return newPosition;
	}

}
