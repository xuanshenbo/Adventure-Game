/**
 * This Strategy makes the Zombie chase the player
 */

package model.npcs;

import model.state.Position;

public class ChaseZombie implements ZombieStrategy {
	@Override
	public Position move(Position oldPosition) {
		Position player = oldPosition.getArea().getNearestPlayer(oldPosition);
		return null;
	}

}
