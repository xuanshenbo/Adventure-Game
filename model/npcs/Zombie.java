/**
 * This is a Zombie that moves around the world independently,
 * create it with a position and a strategy that controls how
 * it moves around.
 */

package model.npcs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import model.state.Area;
import model.state.Position;

@XmlAccessorType(XmlAccessType.FIELD)
public class Zombie {

	@XmlTransient
	private ZombieStrategy strategy  = new RandomZombie();
	private Position position;
	private char id = 'Z';

	public Zombie(ZombieStrategy strategy, Position position){
		this.strategy = strategy;
		this.position = position;
	}

	private Zombie() {

	}

	public void tick(){
		position = strategy.move(position);
	}

	// ================================================
	// getters from here
	// ================================================

	public ZombieStrategy getStrategy() {
		return strategy;
	}

	public char getid(){
		return id;
	}

	/**
	 * This method returns the position of the Zombie. It is called after the
	 * Zombie has moved based on the strategy and then is used to draw the Zombie
	 * into the view.
	 * @return
	 */
	public Position getPosition() {
		return position;
	}

	// ================================================
	// setters from here
	// ================================================

	public void setStrategy(ZombieStrategy strategy) {
		this.strategy = strategy;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setId(char id) {
		this.id = id;
	}
}
