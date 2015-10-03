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

import model.items.Item;

@XmlAccessorType(XmlAccessType.FIELD)
public class Player {

	private Position position; // Position of the player in the world
	private final int id; // unique identifier of the player
	@XmlElementWrapper
	@XmlElement(name="item")
	private List<Item> inventory = new ArrayList<Item>(); // The inventory of the player

	public Player(Position p, int id){
		this.position  = p;
		this.id = id;
	}

	@SuppressWarnings("unused")
	// for serializing final fields purpose.
	private Player() {
		this(null, 0);
	}

	public void setPosition(Position newPosition) {
		position = newPosition;
	}

	public void collect(Item item) {
		inventory.add(item);
	}

	public String toString(){
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

	public List<Item> getInventory() {
		return inventory;
	}

}
