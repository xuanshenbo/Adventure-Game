package model.logic;

import static org.junit.Assert.*;

import org.junit.Test;

import control.Server;
import model.items.Bag;
import model.items.Item;
import model.items.Key;
import model.state.Area;
import model.state.Area.AreaType;
import model.state.Player;
import model.state.Position;
import model.tiles.ChestTile;
import model.tiles.Tile;
import static utilities.PrintTool.p;

public class ModelTests {


	/*============================================
	 * Testing basic player actions
	 * ===========================================
	 */
	/**
	 * Testing the player position
	 */
	@Test
	public void playerPlacement(){
		Game game = new Game(null, basicParameters(), true);
		Player player = game.getPlayer(1);
		Position p = new Position (0, 10, game.getGameState().getWorld(player));
		assertTrue(player.getPosition().equals(p));
	}

	/**
	 * Testing players inventory is empty
	 */
	@Test
	public void playerInventoryEmpty(){
		Game game = new Game(null, basicParameters(), true);
		Player player = game.getPlayer(1);
		boolean empty = true;
		for(int i = 0; i < player.getInventory().length; i++){
			if(player.getInventory()[i] != null){
				empty = false;
			}
		}
		assertTrue(empty);
	}

	/**
	 * Testing adding an item to the players inventory
	 * Player.collect()
	 */
	@Test
	public void addingToPlayerInventory(){
		Game game = new Game(null, basicParameters(), true);
		Player player = game.getPlayer(1);
		Item item = new Key();
		player.collect(item);
		int itemCount = 0;
		for(int i = 0; i < player.getInventory().length; i++){
			if(player.getInventory()[i] != null){
				itemCount++;;
			}
		}
		assertTrue(itemCount == 1);
	}

	/**
	 * Testing picking up from the ground
	 * game.pickUp(Player)
	 */
	@Test
	public void pickingUpFromTheGround(){
		Game game = new Game(new Server(), basicParameters(), true);
		Player player = game.getPlayer(1);
		Item[][] items = game.getGameState().getWorld(player).getItems();
		items[player.getPosition().getY()][player.getPosition().getX()] = new Key();
		game.pickUp(player);
		int itemCount = 0;
		for(int i = 0; i < player.getInventory().length; i++){
			if(player.getInventory()[i] != null){
				itemCount++;;
			}
		}
		assertTrue(itemCount == 1);
	}

	/**
	 * Testing dropping on the ground
	 * game.drop(Player)
	 */
	@Test
	public void droppingOnTheGround(){
		Game game = new Game(new Server(), basicParameters(), true);
		Player player = game.getPlayer(1);
		Item[][] items = game.getGameState().getWorld(player).getItems();
		Item item = new Key();
		player.setSelected(item);
		game.drop(player);
		Item droppedItem = items[player.getPosition().getY()][player.getPosition().getX()];
		assertTrue(droppedItem.equals(item));
	}
	
	/*============================================
	 * Testing actions through the client Parser
	 * ===========================================
	 */
	
	/**
	 * Testing move through the parser, it will attempt to move the player north
	 * with the array ['M', 'N'] and check that the position is correct afterwards
	 */
	@Test
	public void parseMove(){
		Game game = new Game(new Server(), basicParameters(), true);
		Player player = game.getPlayer(1);
		Position oldPosition = player.getPosition();
		char[] message = new char[]{'M', 'N'};
		game.getParser().processClientEvent(message, null, 1);
		Position newPosition = new Position (oldPosition.getX(), oldPosition.getY()-1, oldPosition.getArea());
		assertTrue(player.getPosition().equals(newPosition));
	}
	
	
	/**
	 * Testing Use on the bag to set it to the currentOpenContainer with the
	 * array ['U', '0'] where the bag is inventory slot 0
	 */
	@Test
	public void parseUseBag(){
		Game game = new Game(new Server(), basicParameters(), true);
		Player player = game.getPlayer(1);
		Bag bag = new Bag();
		player.collect(bag);
		char[] message = new char[]{'U', '0'};
		game.getParser().processClientEvent(message, null, 1);
		assertTrue(player.getOpenContainer().equals(bag));
	}
	
	/**
	 * Testing Pickup through the parser by sending through ['P'] when the
	 * player is standing on an key and checking the inventory count
	 */
	@Test
	public void parsePickup(){
		Game game = new Game(new Server(), basicParameters(), true);
		Player player = game.getPlayer(1);
		
		Item[][] items = game.getGameState().getWorld(player).getItems();
		Item item = new Key();
		items[player.getPosition().getY()][player.getPosition().getX()] = item;
		char[] message = new char[]{'P'};
		game.getParser().processClientEvent(message, null, 1);
		int itemCount = 0;
		for(int i = 0; i < player.getInventory().length; i++){
			if(player.getInventory()[i] != null){
				itemCount++;;
			}
		}
		assertTrue(itemCount == 1);
		assertTrue(player.getInventory()[0].equals(item));
	}
	
	/**
	 * Testing the selecting of an inventory slot through the parser with
	 * the array ['C', 0] where 0 is the inventory slot
	 */
	@Test
	public void parseSelect(){
		Game game = new Game(new Server(), basicParameters(), true);
		Player player = game.getPlayer(1);
		
		Item item = new Key();
		player.collect(item);
		
		char[] message = new char[]{'C', '0'};
		game.getParser().processClientEvent(message, null, 1);
		
		assert(player.getSelectedItem().equals(item));
	}
	
	/**
	 * Testing the dropping of an item through the parser with the array
	 * ['D'] when the player is holding an item in their selected field
	 */
	@Test
	public void parseDrop(){
		Game game = new Game(new Server(), basicParameters(), true);
		Player player = game.getPlayer(1);
		
		Item[][] items = game.getGameState().getWorld(player).getItems();
		Item item = new Key();
		player.setSelected(item);
		char[] message = new char[]{'D'};
		game.getParser().processClientEvent(message, null, 1);
		Item droppedItem = items[player.getPosition().getY()][player.getPosition().getX()];
		assertTrue(droppedItem.equals(item));
		assertFalse(droppedItem.equals(new Bag()));
	}
	
	/**
	 * Testing moving into a chest through the parser with the array ['M']
	 * when the player tries to move into a chest and then opening the chest
	 * by setting it to the openContainer field
	 */
	@Test
	public void parseOpenChest(){
		Game game = new Game(new Server(), basicParameters(), true);
		Player player = game.getPlayer(1);
		int x = player.getPosition().getX();
		int y = player.getPosition().getY();
		Area a = player.getPosition().getArea();
		Tile chest = new ChestTile(new Position(x, y-1, a), "easy");
		a.getArea()[y-1][x] = chest;

		char[] message = new char[]{'M', 'N'};
		game.getParser().processClientEvent(message, null, 1);
		
		assertTrue(player.getOpenContainer().equals(chest));
	}









	private WorldParameters basicParameters() {
		WorldParameters w = new WorldParameters(21, 21, 4, false);
		return w;
	}

	/**
	 * Used by the tests to create an area to test
	 */

	public Area create(int size, int trees, int buildings, int caves, int chests, int lootValue) {
		Area w = new Area(size, size, Area.AreaType.OUTSIDE, null);
		Generator g = new Generator(trees, buildings, caves, chests, lootValue);
		w.generateWorld(g);
		return w;
	}
}
