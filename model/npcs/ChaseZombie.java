/**
 * This Strategy makes the Zombie chase the player
 */

package model.npcs;

import model.state.Position;
import static utilities.PrintTool.p;

public class ChaseZombie implements ZombieStrategy {


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
