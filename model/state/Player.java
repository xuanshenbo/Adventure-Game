/**
 * The holds the state of the Players in the game.
 */

package model.state;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import model.items.Bag;
import model.items.Item;
import model.items.Key;

@XmlAccessorType(XmlAccessType.FIELD)
public class Player {

	private Position position; // Position of the player in the world
	private final int id; // unique identifier of the player
	@XmlElementWrapper
	@XmlElement(name = "item")
	private Item[] inventory = new Item[6]; // The inventory of the player
	private int happiness = 5;
	private boolean inGame = false;
	private Item selectedItem = null;
	private Container openContainer = null;

	public Player(Position p, int id) {
		this.position = p;
		this.id = id;
		inventory[0] = new Bag();
		inventory[1] = new Key();
	}



	public void increaseHappiness() {
		happiness ++;
		if(happiness > 9){
			happiness = 9;
		}
	}



	public void makeActive() {
		inGame = true;

	}
	
	public Item getSelectedItem(){
		return selectedItem;
	}
	
	
	public void removeSelectedItem(){
		selectedItem = null;
	}

	/**
	 * Adds the item to the players inventory
	 *
	 * @param item
	 *            The item to be added to the inventory
	 */
	public boolean collect(Item item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				inventory[i] = item;
				return true;
			}
		}
		return false;
	}

	/**
	 * Player uses the item that is passed in
	 * @return
	 */

	public Item[] use(int inventorySlot){
		Item item = inventory[inventorySlot];
		return item.use(this);
	}

	public void removeItem(int inventorySlot) {
		inventory[inventorySlot] = null;
	}

	@SuppressWarnings("unused")
	// for serializing final fields purpose.
	private Player() {
		this(null, 0);
	}

	public void setPosition(Position newPosition) {
		position = newPosition;
	}

	public String toString() {
		return Integer.toString(id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}

	// ================================================
	// getters from here
	// ================================================

	public Position getPosition() {
		return position;
	}

	public int getId() {
		return id;
	}

	public Item[] getInventory() {
		return inventory;
	}

	public Item getItemFromInventory(int inventorySlot) {
		return inventory[inventorySlot];
	}



	public int getHappiness() {
		return happiness;
	}



	public boolean isInGame() {
		return inGame;
	}
}
