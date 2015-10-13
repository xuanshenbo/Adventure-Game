package model.items;

import javax.xml.bind.annotation.XmlRootElement;

import model.state.Player;

@XmlRootElement
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
