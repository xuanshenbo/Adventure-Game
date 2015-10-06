package model.items;

import model.state.Player;

public class Cupcake extends Item{



	public Cupcake() {
		super('c');
	}

	@Override
	public Item[] use(Player player) {
		player.increaseHappiness();
		return null;
	}

}
