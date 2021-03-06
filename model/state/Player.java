package model.state;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import dataStorage.adapters.PlayerAdapter;
import model.items.Bag;
import model.items.Consumable;
import model.items.Item;
import model.items.Key;
import model.tiles.ChestTile;
import static utilities.PrintTool.p;


/**
 * The holds the state of the Players in the game. It also holds logic
 * around the players happiness and if they die or not
 */
@XmlJavaTypeAdapter(PlayerAdapter.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Player {

	private Position position; // Position of the player in the world
	private final int id; // unique identifier of the player
	@XmlElementWrapper
	@XmlAnyElement
	private Item[] inventory = new Item[6]; // The inventory of the player
	private double happiness = 5;
	@XmlTransient
	private boolean inGame = false;
	@XmlTransient
	private Item selectedItem = null;
	@XmlTransient
	private Container openContainer = null;//this represents the open container that the player has open at the moment
	private Position startingPosition;//the starting position of the player, they are put here when they die
	@XmlElementWrapper
	private Set<ChestTile> openedChests = new HashSet<ChestTile>();

	public Player(Position p, int id) {
		this.position = p;
		this.startingPosition = p;
		this.id = id;
	}

	public Player(int id) {
		this.id = id;
	}

	/**
	 * Increases the players happines
	 */
	public void increaseHappiness() {
		happiness++;
		if (happiness > 9) {
			happiness = 9;
		}
	}
	/**
	 * decreases the players happiness
	 */
	public void loseHappiness() {
		happiness += -0.5;
	}
	
	/**
	 * called when teh player runs out of happiness
	 */
	public void die() {
		if (happiness < 1) {
			position = startingPosition;
			happiness = 5;
		}
	}


	/**
	 * This tries to add the item to the players inventory
	 * @param item: the item to be added
	 * @return: if the item was added or not
	 */
	public boolean addItemToInventory(Item item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				inventory[i] = item;
				return true;
			}
		}
		return false;
	}
	/**
	 * Sets the player to be active in the game, called when the player
	 * joins the server
	 */
	public void makeActive() {
		inGame = true;
	}
	
	/**
	 * Sets the player to be inactive in the game, called when teh player
	 * leaves the server
	 */
	public void makeInactive() {
		inGame = false;
	}


	/**
	 * Called when a player tries to move an item from the open container to
	 * their inventory.
	 *
	 * @param containerSlot
	 */

	public void moveToInventory(int containerSlot) {
		Item item = openContainer.getItem(containerSlot);
		boolean added = addItemToInventory(item);
		if (added) {
			openContainer.removeItemSlot(containerSlot);
		}

	}

	/**
	 * this returns whether the player has a key or not, called when the player
	 * tries to open a chest
	 * @return: if the player has a key or not
	 */
	public boolean getKey() {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] instanceof Key) {
				inventory[i] = null;
				return true;
			} else if (inventory[i] instanceof Bag) {
				Bag bag = (Bag) inventory[i];
				for (int j = 0; j < bag.open().length; j++) {
					if (bag.open()[i] instanceof Key) {
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
	 * Player uses the item that is passed in. If it is a container it will
	 * return the inventory of that container, else it will return null
	 */

	public Item[] use(int inventorySlot) {
		Item item = inventory[inventorySlot];
		if (item instanceof Bag) {
			openContainer = (Container) item;
			return item.use(this);
		} else if (item instanceof Consumable) {
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

	public void setOpenContainer(Container container) {
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
		return (int) happiness;
	}

	public boolean isInGame() {
		return inGame;
	}

	public Container getOpenContainer() {
		return openContainer;
	}

	public Position getStartingPosition() {
		return startingPosition;
	}

	public Item getSelectedItem() {
		return selectedItem;
	}

	// ================================================
	// setters from here
	// ================================================

	public void setStartingPosition(Position startingPosition) {
		this.startingPosition = startingPosition;
	}

	public Set<ChestTile> getOpenedChests() {
		return openedChests;
	}

	public void setOpenedChests(Set<ChestTile> openedChests) {
		this.openedChests = openedChests;
	}

	public void setInventory(Item[] inventory) {
		this.inventory = inventory;
	}

	public void setHappiness(int happiness) {
		this.happiness = happiness;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

	public void removeSelectedItem() {
		selectedItem = null;
	}

}
