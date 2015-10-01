/**
 * The main Game logic class, this holds the game tick and has the logical
 * updates that have to be done every tick
 */

package model.logic;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.items.Item;
import model.npcs.RandomZombie;
import model.npcs.RunZombie;
import model.npcs.Zombie;
import model.state.Area;
import model.state.GameState;
import model.state.Player;
import model.state.Position;
import model.state.Area.AreaType;
import model.tiles.Tile;
import control.Server;
import static utilities.PrintTool.p;

public class Game{

	private GameState gameState;
	private Server server;
	private Clock clock;

	public Game(GameState state) {
		this.gameState = state;
	}

	public Game(Server server, WorldParameters parameters){
		this.server = server;
		this.clock = new Clock(2000, this);
		int height = parameters.getHeight();
		int width = parameters.getWidth();
		Area area = new Area(height, width, AreaType.OUTSIDE, null);
		Generator g = new Generator(parameters);
		area.generateWorld(g);
		ArrayList<Player> playerList = placePlayers(parameters.getPlayerCount(), height, width, area);
		this.gameState = new GameState(area, playerList);
		clock.start();
	}
	
	/**
	 * This is the method that the clock calls, from here the updates need to happen, ie moving
	 * Zombies and updating the time.
	 */
	
	public void tick(){
		String day;
		if(gameState.getDay()){
			day = "AM";
		}else{
			day = "PM";
		}
		p("time:"+gameState.getTime()+day);
		gameState.setTime(gameState.getTime()+1);
		if(gameState.getTime() == 12){
			gameState.setTime(0);
			if(gameState.getDay()){
				gameState.setDay(false);
			}else{
				gameState.setDay(true);
			}
		}
		
		p("printing gameState for testing zombies");
		if(gameState.getZombieList().size() < 1){
			addZombie();
		}
		for(Zombie z:gameState.getZombieList()){
			if(gameState.getDay()){
				z.setStrategy(new RandomZombie());
			}else{
				z.setStrategy(new RunZombie());
			}
			z.tick();
		}

		gameState.printState();
	}


	/**
	 * This method moves the player in the direction that is passed in.
	 * @param p: player to move
	 * @param n: direction to move in.
	 */
	public void move(Player player, int direction){
		Position playerPosition = player.getPosition();
		Tile toTile = destinationTile(direction, player);

		if(toTile != null){
			toTile.move(player, direction);
		}		

		
		
		
	}
	/**
	 * Called when a player tries to pick up an object of the ground
	 * @param player
	 */
	public void pickUp(Player player){
		Position playerPosition = player.getPosition();
		Item item = gameState.getItem(playerPosition);
		if(item != null){
			player.collect(item);
			gameState.removeItem(playerPosition);
		}
	}	
	
	/**
	 * Adds a random Zombie to the game.
	 */
	
	public void addZombie(){
		Position position = gameState.getRandomValidTile();
		Zombie z = new Zombie(new RandomZombie(), position);
		gameState.addZombie(z);
	}
	
	/*********************************
	 * 
	 *  GETTERS AND SETTERS
	 * 
	 * *******************************
	 */

	public List<char[][]> getGameView(int id){
		Player player = gameState.getPlayer(id);
		return gameState.getGameView(player);
	}

	public Player getPlayer(int id){
		return gameState.getPlayer(id);
	}
	public ArrayList<Player> getPlayerList() {
		return gameState.getPlayerList();
	}

	/**
	 * This method will return the Tile the that is in the direction
	 * the player want to move in
	 * @param direction: direction of the player
	 * @param player: player that is moving
	 * @return: Tile of destination
	 */
	private Tile destinationTile(int direction, Player player) {
		int x = player.getPosition().getX();
		int y = player.getPosition().getY();
		Tile[][] areaArray = gameState.getWorld(player).getArea();

		if(direction == 1){//up
			if(x == 0){
				return null;
			}
			return areaArray[x-1][y];

		}else if(direction == 2){//down
			if(x == areaArray.length-1){
				return null;
			}
			return areaArray[x+1][y];

		}else if(direction == 3){//right
			if(y == areaArray[0].length-1){
				return null;
			}
			return areaArray[x][y+1];

		}else if(direction == 4){//left
			if(y == 0){
				return null;
			}
			return areaArray[x][y-1];
		}
		return null;
	}
	
	public GameState getGameState() {
		 return gameState;
	}
	
	/**
	 * This Method is only called when the game is first made and places the 
	 * players into the world, this will probably be redundant as we progress
	 * @param playerCount
	 * @param width
	 * @param height
	 * @param a
	 * @return
	 */

	private ArrayList<Player> placePlayers(int playerCount, int width, int height, Area a) {
		double[] xCoords = {0.5, 0, 0.5, 1};
		double[] yCoords = {0, 0.5, 1, 0.5};
		ArrayList<Player> list = new ArrayList<Player>();
		for(int count = 0; count < playerCount; count++){
			int x = (int) ((width-1)*xCoords[count]);
			int y = (int) ((height-1)*yCoords[count]);
			int id = count+1;
			Position position = new Position(x, y, a);
			Player p = new Player(position, id);
			list.add(p);
		}
		return list;
	}
}
