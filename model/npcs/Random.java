package model.npcs;

import java.util.List;

import model.state.Area;
import model.state.Position;

public class Random implements ZombieStrategy {

	@Override
	public Position move(Position oldPosition) {
		
		List<Position> validPositions = oldPosition.getValid();
		int index = (int) (Math.random()*validPositions.size());
		Position newPosition = validPositions.get(index);
		
		return newPosition;
		
	}

	
}
