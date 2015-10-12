package model.items;

import model.state.Player;

public class Pumpkin extends Item implements Consumable{



	public Pumpkin() {
		super('p');
	}

	@Override
	public Item[] use(Player player) {
		player.increaseHappiness();
		return null;
	}

}