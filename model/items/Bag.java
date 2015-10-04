package model.items;

import model.state.Container;
import model.state.Player;

public class Bag extends Item implements Container {

	private Item[] inventory = new Item[3];

	public Bag(String description) {
		super(description, "", 'B');
	}

	@Override
	public Item[] use(Player player) {
		return inventory;
	}

	@Override
	public String getUseDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addItem(Item item) {
		if(item instanceof Bag){
			return false;
		}
		for(int i = 0; i<inventory.length; i++){
			if(inventory[i] == null){
				inventory[i] = item;
				return true;
			}
		}
		return false;
	}

	@Override
	public Item removeItem(int id) {
		for(int i = 0; i<inventory.length; i++){
			if(inventory[i].getId() == id){
				Item item = inventory[i];
				inventory[i] = null;
				return item;
			}
		}
		return null;
	}
}
