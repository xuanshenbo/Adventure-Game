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

	public void tick(){
		position = strategy.move(position);
	}

	public ZombieStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(ZombieStrategy strategy) {
		this.strategy = strategy;
	}

	public char getid(){
		return id;
	}

	public Position getPosition() {
		return position;
	}
}
