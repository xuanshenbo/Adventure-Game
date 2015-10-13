package model.items;

import model.state.Player;


public class Key extends Item {

	public Key(){
		super('k');
	}

	@Override
	public Item[] use(Player player) {
		return null;

	}


	@Override
	public String toString(){
		return "Key";
	}

}
