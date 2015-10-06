package model.state;

import model.items.Item;

public interface Container {

	public boolean addItem(Item item);
	public  Item removeItem(int id);

}
