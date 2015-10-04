package model.items;

import model.state.Player;

public class Cupcake extends Item{



	public Cupcake(String description, String imgpath, char c) {
		super(description, imgpath, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Item[] use(Player player) {
		player.increaseHappiness();
		return null;
	}

	@Override
	public String getUseDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
