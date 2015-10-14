package model.items;

/**
 * This class represents a bag item in the game, it can be in the inventory and can hold
 * 3 items. The player is able to put things in the bag.
 * @author tuckergare
 */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import model.logic.Generator;
import model.state.Container;
import model.state.Player;

import static utilities.PrintTool.p;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bag extends Item implements Container {

	@XmlElementWrapper
	@XmlElement(name="inventory")
	private Item[] inventory = new Item[3];

	/**
	 * Constructor creates a bag and fills it wtih random objects
	 */
	public Bag() {
		super('b');
		fillBag();
	}

	/**
	 *This method is called when the bag is first made and fill the bag
	 *with random objects from the game.
	 */
	public void fillBag(){
		for(int i = 0; i < inventory.length; i++){
			inventory[i] = Generator.randomItem();
		}
	}
	
	/**
	 * This is called when the player uses an item, it returns the inventory
	 * of the bag.
	 */
	@Override
	public Item[] use(Player player) {
		p();
		return inventory;
	}

	@Override
	public Item getItem(int containerSlot) {
		return inventory[containerSlot];
	}
	
	/**
	 * This is when the player tries to add objects to the bag
	 * @param: the item to be added to the bag
	 * @return: whether the item was added to the bag successfully
	 */
	@Override
	public boolean addItem(Item item) {
		for(int i = 0; i<inventory.length; i++){
			if(inventory[i] == null){
				inventory[i] = item;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method removes the Item with the id from the bag
	 * @param: the id of the item to be removed
	 * @return: the item being removed
	 */
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
	
	/**
	 * Removes the item from the bag that is in the container slot
	 * @param: The slot of the bag
	 */
	@Override
	public void removeItemSlot(int containerSlot) {
		inventory[containerSlot] = null;

	}
	
	/**
	 * This method returns the inventory of the bag
	 * @return: the inventory of the bag
	 */
	@Override
	public Item[] open() {

		return inventory;
	}

	@Override
	public String toString(){
		return "Bag";
	}


}
