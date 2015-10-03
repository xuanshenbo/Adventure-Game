/**
 * Interface to control how the Zombie moves around the world
 */

package model.npcs;

import model.state.Area;
import model.state.Position;

public interface ZombieStrategy {
	
	public Position move(Position oldPosition);

}
