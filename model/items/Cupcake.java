/**
 * This represents the cupcake item in the world that increases happiness
 * when used.
 * @author tuckergare
 */

package model.items;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import model.state.Player;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Cupcake extends Item implements Consumable{

	public Cupcake() {
		super('c');
	}

	@Override
	public Item[] use(Player player) {
		player.increaseHappiness();
		return null;
	}


	@Override
	public String toString(){
		return "Cupcake";
	}

}
