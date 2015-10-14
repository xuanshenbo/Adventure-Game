

package model.npcs;

import javax.xml.bind.annotation.XmlSeeAlso;

import model.state.Position;


/**
 * Interface to control how the Zombie moves around the world
 */
public interface ZombieStrategy {

	public Position move(Position oldPosition);

}
