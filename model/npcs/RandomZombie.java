

package model.npcs;

import java.util.List;

import model.state.Area;
import model.state.Position;

/**
 * This strategy makes the zombie move around randomly, it is used 
 * when it is night time and the player is not in range
 */

public class RandomZombie implements ZombieStrategy {
	
	/**
	 * This method moves the random Zombie in one of the four positions
	 * around it if it is a valid position.
	 */
	@Override
	public Position move(Position oldPosition) {
		
		List<Position> validPositions = oldPosition.getValid();
		int i = (int) (Math.random()*validPositions.size());
		if(validPositions.size() != 0)	{
			return validPositions.get(i);
		}else{
			return oldPosition;
		}
		
		
		
	}

	
}
