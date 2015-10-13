package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import model.items.Item;
import model.logic.Game.Direction;
import model.logic.Generator;
import model.state.Container;
import model.state.Player;
import model.state.Position;
import static utilities.PrintTool.p;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ChestTile implements Tile, Container{

	private char id = 'O';
	private Position position;
	@XmlElementWrapper
	@XmlElement(name = "inventory")
	private Item[] inventory = new Item[10];

	public ChestTile(Position position, String difficulty){
		this.position = position;
		fillChest(difficulty);
	}

	// JAXB needs a no-arg default constructor to instance ChestTile
	@SuppressWarnings("unused")
	private ChestTile() {
		this(null, null);
	}

	/**
	 * This method fills the chest with random objects when it is
	 * first created
	 * @param difficulty
	 */
	private void fillChest(String difficulty) {
		for(int i = 0; i<inventory.length; i++){
			int random = (int) (Math.random()*100)+1;
			if(difficulty.equals("easy") && random < 50){
				inventory[i] = Generator.randomItem();
			}else if(difficulty.equals("medium") && random < 30){
				inventory[i] = Generator.randomItem();
			}else if(difficulty.equals("hard") && random < 10){
				inventory[i] = Generator.randomItem();
			}
		}
	}

	@Override
	public Item getItem(int containerSlot) {
		return inventory[containerSlot];
	}

	@Override
	public void move(Player player, Direction direction) {
		//Item[] items = inventory;
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

	@Override
	public void removeItemSlot(int containerSlot) {
		inventory[containerSlot] = null;

	}

	public Item[] open(){
		p();
		return inventory;
	}

}
