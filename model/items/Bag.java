package model.items;

import model.logic.Generator;
import model.state.Container;
import model.state.Player;

public class Bag extends Item implements Container {

	private Item[] inventory = new Item[3];

	public Bag() {
		super('b');
		fillBag();
	}

	public void fillBag(){
		for(int i = 0; i < inventory.length; i++){
			inventory[i] = Generator.randomItem();
		}
	}

	@Override
	public Item[] use(Player player) {
		return inventory;
	}

	@Override
	public Item getItem(int containerSlot) {
		return inventory[containerSlot];
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
	public Item removeItemId(int id) {
		for(int i = 0; i<inventory.length; i++){
			if(inventory[i].getId() == id){
				Item item = inventory[i];
				inventory[i] = null;
				return item;
			}
		}
		return null;
	}

	@Override
	public void removeItemSlot(int containerSlot) {
		inventory[containerSlot] = null;

	}

	@Override
	public Item[] open() {
		return inventory;
	}


}
