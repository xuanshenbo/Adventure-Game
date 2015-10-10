package model.state;

import model.items.Item;

public interface Container {

	public boolean addItem(Item item);
	public  Item removeItemId(int id);
	public Item[] open();
	public Item getItem(int containerSlot);
	public void removeItemSlot(int containerSlot);

}
