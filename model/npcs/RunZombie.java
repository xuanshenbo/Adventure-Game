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
		p("Cave:"+caveEntrance);
		p("oldPosition:"+oldPosition);
		
		Position newPosition;
		
		if(oldX > caveX){
			//move left if possible
			newPosition = new Position (oldX-1, oldY, oldPosition.getArea());
			p("moveLeft");
			if(newPosition.isValid()){
				return newPosition;
			}
		}else if (oldX < caveX){
			//move right is possible
			p("moveRight");
			newPosition = new Position (oldX+1, oldY, oldPosition.getArea());
			if(newPosition.isValid()){
				return newPosition;
			}
		}
		
		if(oldY > caveY){
			//move up if possible
			p("moveUp");
			newPosition = new Position (oldX, oldY-1, oldPosition.getArea());
			if(newPosition.isValid()){
				return newPosition;
			}
		}else{
			//move down is possible
			p("moveDown");
			newPosition = new Position (oldX, oldY+1, oldPosition.getArea());
			if(newPosition.isValid()){
				return newPosition;
			}
		}
		return null;
	}

}
