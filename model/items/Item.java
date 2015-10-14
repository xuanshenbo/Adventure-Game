/**
 * The item interface that holds all the 
 */

package model.items;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

import model.state.Player;

@XmlSeeAlso({ Bag.class, Cupcake.class, Key.class, Pumpkin.class })
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Item {

	// every time an Item is created, generate an id for it using this field
	private static int idCounter = 0;

	private int id;
	private char type;

	public Item(char c){
		id = idCounter;
		idCounter++;
		type = c;
	}

	public abstract Item[] use(Player player);

	// ================================================
	// getters from here
	// ================================================

	public int getId() {
		return id;
	}

	public char getType(){
		return type;
	}

	// ================================================
	// setters from here
	// ================================================

	public void setId(int id) {
		this.id = id;
	}

	public void setType(char type) {
		this.type = type;
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
		Item other = (Item) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
