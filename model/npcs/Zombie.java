package model.npcs;

import model.state.Area;
import model.state.Position;

public class Zombie {
	
	private ZombieStrategy strategy;
	private Position position;

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
}
