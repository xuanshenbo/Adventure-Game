package model.items;

import model.state.Player;


public class Key extends Item {

	public Key(){
		super("A Key", "key", 'k');
	}

	@Override
	public Item[] use(Player player) {
		return null;

	}

	@Override
	public String getUseDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
