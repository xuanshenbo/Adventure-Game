package model.items;

import javax.xml.bind.annotation.XmlRootElement;

import model.state.Player;

@XmlRootElement
public class Pumpkin extends Item implements Consumable{



	public Pumpkin() {
		super('p');
	}

	@Override
	public Item[] use(Player player) {
		player.increaseHappiness();
		return null;
	}


	@Override
	public String toString(){
		return "Pumpkin";
	}


}