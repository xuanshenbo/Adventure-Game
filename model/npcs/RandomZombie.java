package model.npcs;

import java.util.List;

import model.state.Area;
import model.state.Position;

public class RandomZombie implements ZombieStrategy {

	@Override
	public Position move(Position oldPosition) {
		
		List<Position> validPositions = oldPosition.getValid();
		int i = (int) (Math.random()*validPositions.size());
		Position newPosition = validPositions.get(i);
		
		return newPosition;
		
	}

	
}
