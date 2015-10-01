package model.npcs;

import model.state.Area;
import model.state.Position;

public class Zombie {
	
	private ZombieStrategy strategy;
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
