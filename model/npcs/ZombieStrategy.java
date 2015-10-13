/**
 * Interface to control how the Zombie moves around the world
 */

package model.npcs;

import javax.xml.bind.annotation.XmlSeeAlso;

import model.state.Position;

public interface ZombieStrategy {

	public Position move(Position oldPosition);

}
