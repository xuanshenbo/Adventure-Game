package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import model.items.Item;
import model.logic.Game.Direction;
import model.logic.Generator;
import model.state.Container;
import model.state.Player;
import model.state.Position;
import static utilities.PrintTool.p;


/**
 * This is the chest tile in the game. It can be opened with a key
 * and holds a list of inventory
 * @author tuckergare
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ChestTile implements Tile, Container{

	private char id = 'O';
	@XmlTransient
	private Position position;
	@XmlElementWrapper
	@XmlElement(name = "inventory")
	private Item[] inventory = new Item[10];
	private int x;
	private int y;

	public ChestTile(Position position, String difficulty){
		this.position = position;
		fillChest(difficulty);
		x = position.getX();
		y = position.getY();
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
	
	
	/**
	 * Will return the item in the inventory slot
	 * @param containerSlot
	 * @return
	 */
	@Override
	public Item getItem(int containerSlot) {
		for(int i = 0; i< inventory.length; i++){
			System.out.print(inventory[i]+" ");
		}
		System.out.println("");
		return inventory[containerSlot];
	}
	
	@Override
	public void move(Player player, Direction direction) {
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
	
	/**
	 * Removes the item in the slot passed in from teh inventory
	 * @param containerSlot: the slot of the container where the item 
	 * is
	 */
	@Override
	public void removeItemSlot(int containerSlot) {
		inventory[containerSlot] = null;
	}

	public Item[] open(){
		return inventory;
	}

	public char getId() {
		return id;
	}

	public void setId(char id) {
		this.id = id;
	}

	public Item[] getInventory() {
		return inventory;
	}

	public void setInventory(Item[] inventory) {
		this.inventory = inventory;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
