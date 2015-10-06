package model.tiles;

import model.items.Item;
import model.logic.Game.Direction;
import model.state.Container;
import model.state.Player;
import model.state.Position;

public class ChestTile implements Tile, Container{

	private char id = 'O';
	private Position position;
	private Item[] inventory = new Item[10];

	public ChestTile(Position position){
		this.position = position;
	}

	@Override
	public void move(Player player, Direction direction) {
		Item[] items = inventory;
	}

	@Override
	public void interact(Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public char getType() {
		return id;
	}

	public String toString(){
		return Character.toString(id);
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public boolean isGround() {
		return false;
	}

	@Override
	public boolean isContainer() {
		return true;
	}

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

	@Override
	public Item removeItem(int id) {
		for(int i = 0; i<inventory.length; i++){
			if(inventory[i].getId() == id){
				Item item = inventory[i];
				inventory[i] = null;
				return item;
			}
		}
		return null;
	}

	public Item[] open(){
		return inventory;
	}

}
