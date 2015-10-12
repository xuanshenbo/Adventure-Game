/**
 * The holds the state of the Players in the game.
 */

package model.state;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import model.items.Bag;
import model.items.Consumable;
import model.items.Cupcake;
import model.items.Item;
import model.items.Key;
import model.tiles.ChestTile;
import static utilities.PrintTool.p;

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
	private Position startingPosition;
	private Set<ChestTile> openedChests = new HashSet<ChestTile>();

	public Player(Position p, int id) {
		this.position = p;
		this.startingPosition = p;
		this.id = id;
//		inventory[0] = new Bag();
//		inventory[1] = new Key();
	}

	public Player(int id){
		this.id = id;
	}



	public void increaseHappiness() {
		happiness ++;
		if(happiness > 9){
			happiness = 9;
		}
	}

	public void loseHappiness() {
		happiness --;
		if(happiness < 1){
			position = startingPosition;
			happiness = 5;
		}

	}

	public boolean addItemToInventory(Item item){
		for(int i = 0; i < inventory.length; i++){
			if(inventory[i] == null){
				inventory[i] = item;
				return true;
			}
		}
		return false;
	}

	public void makeActive() {
		inGame = true;
	}
	public void makeInactive() {
		inGame = false;
	}

	public Item getSelectedItem(){
		return selectedItem;
	}


	public void removeSelectedItem(){
		selectedItem = null;
	}



	/**
	 * Called when a player tries to move an item from the
	 * open container to their inventory.
	 * @param  containerSlot
	 */

	public void moveToInventory(int containerSlot) {
		boolean added = addItemToInventory(openContainer.getItem(containerSlot));
		if(added){
			openContainer.removeItemSlot(containerSlot);
		}

	}

	public boolean getKey() {
		for(int i = 0; i < inventory.length; i++){
			if(inventory[i] instanceof Key){
				inventory[i] = null;
				return true;
			}else if(inventory[i] instanceof Bag){
				Bag bag = (Bag)inventory[i];
				for(int j = 0; j < bag.open().length; j++){
					if(bag.open()[i] instanceof Key){
						bag.open()[i] = null;
						return true;
					}
				}
			}
		}
		return false;
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
	 * Player uses the item that is passed in. If it is a container
	 * it will return the inventory of that container, else it will
	 * return null
	 */

	public Item[] use(int inventorySlot){
		Item item = inventory[inventorySlot];
		if(item instanceof Bag){
			openContainer = (Container) item;
			return item.use(this);
		}
		else if(item instanceof Consumable){
			item.use(this);
			inventory[inventorySlot] = null;
		}
		return null;
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

	public void setSelected(Item item) {
		selectedItem = item;
	}

	public void setSelected(int inventorySlot) {
		selectedItem = inventory[inventorySlot];
	}

	public void setOpenContainer(Container container){
		openContainer = container;
	}

	public void addChest(ChestTile container) {
		openedChests.add(container);

	}

	public boolean hasOpenedChest(ChestTile container) {
		return openedChests.contains(container);
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

	public Container getOpenContainer(){
		return openContainer;
	}














}
