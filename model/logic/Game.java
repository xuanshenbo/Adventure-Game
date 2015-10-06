/**
 * The main Game logic class, this holds the game tick and has the logical
 * updates that have to be done every tick
 */

package model.logic;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.items.Item;
import model.npcs.ChaseZombie;
import model.npcs.RandomZombie;
import model.npcs.RunZombie;
import model.npcs.Zombie;
import model.state.Area;
import model.state.GameState;
import model.state.Player;
import model.state.Position;
import model.state.Area.AreaType;
import model.tiles.Cabinet;
import model.tiles.ChestTile;
import model.tiles.Tile;
import control.Server;
import static utilities.PrintTool.p;

public class Game {

	private GameState gameState;
	private Server server;
	private Clock clock;
	private boolean frameActivated = false;
	private ServerParser parser;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	public Game(GameState state) {
		this.gameState = state;
	}

	/**
	 * Creates a new game. Creates a new World for the players and places the
	 * players in the world. Also starts a clock thread that will keep the
	 * day/night cycle and the zombie movement.
	 *
	 * @param server
	 *            : the server that will be running the game
	 * @param parameters
	 *            : the parameters of the game world to be created
	 */
	public Game(Server server, WorldParameters parameters) {
		this.server = server;
		this.clock = new Clock(2000, this);
		int height = parameters.getHeight();
		int width = parameters.getWidth();
		Area area = new Area(height, width, AreaType.OUTSIDE, null);
		Generator g = new Generator(parameters);
		area.generateWorld(g);
		ArrayList<Player> playerList = placePlayers(parameters.getPlayerCount(), height, width, area);
		playerList.get(0).makeActive();//FOR TESTING!!!!!!!
		//playerList.get(1).makeActive();
		this.gameState = new GameState(area, playerList);
		parser = new ServerParser(this, server);
		clock.start();
	}

	/**
	 * This is the method that the clock calls, from here the updates need to
	 * happen, ie moving Zombies and updating the time.
	 */
	public void tick() {
		String day;
		if (gameState.getDay()) {
			day = "AM";
		} else {
			day = "PM";
		}
		gameState.setTime(gameState.getTime() + 1);
		if (gameState.getTime() == 12) {
			gameState.setTime(0);
			if (gameState.getDay()) {
				gameState.setDay(false);
			} else {
				gameState.setDay(true);
			}
		}
		updateZombies();
		//gameState.printState(false);
		if(frameActivated){
			for(Player p: gameState.getPlayerList()){
				parser.sendToServer(p, 'M');
			}
		}
//		p("Printing gameState");
//		gameState.printState(false);
	}

	/**
	 * This method checks how many Zombies are in the world and adds Zombies if
	 * needed, it then checks the time and changes the zombies actions based on
	 * the time. From there is makes all the Zombies move.
	 */

	public void updateZombies() {
		if (gameState.getZombieList().size() < 5) {
			addZombie();
		}
		for (Zombie zombie : gameState.getZombieList()) {

			if (gameState.getDay()) {
				zombie.setStrategy(new RunZombie());
			} else {
				zombie.setStrategy(new RandomZombie());
			}
			for (Player player : gameState.getPlayerList()) {
				if (playerInRange(player, zombie)) {
					zombie.setStrategy(new ChaseZombie());
				}
			}
			zombie.tick();
		}
	}

	/**
	 * Checks if the player and the Zombie are in 3 blocks of each other.
	 *
	 * @param player
	 *            : Player to be checked againist the Zombie
	 * @param zombie
	 *            : Zombie to be checked if it is in range
	 * @return: if the Zombie is in range of the player.
	 */
	private boolean playerInRange(Player player, Zombie zombie) {
		int playerX = player.getPosition().getX();
		int playerY = player.getPosition().getY();
		int zombieX = zombie.getPosition().getX();
		int zombieY = zombie.getPosition().getY();
		if (Math.abs(playerX - zombieX) < 4 && Math.abs(playerY - zombieY) < 4) {
			return true;
		}
		return false;
	}

	/**
	 * This method moves the player in the direction that is passed in.
	 *
	 * @param player
	 *            : player to move
	 * @param direction
	 *            : direction to move in.
	 */
	public void move(Player player, Direction direction) {
		Position playerPosition = player.getPosition();
		Tile toTile = destinationTile(direction, player);

		if (toTile != null) {
			toTile.move(player, direction);
			//check other players are in range and send update to players as needed
			for(Player otherPlayer: gameState.getPlayerList()){
				if(player.isInGame()){
					int playerX = player.getPosition().getX();
					int playerY = player.getPosition().getY();
					int otherPlayerX = otherPlayer.getPosition().getX();
					int otherPlayerY = otherPlayer.getPosition().getY();

					if(Math.abs(playerX-otherPlayerX) < 8 && Math.abs(playerY-otherPlayerY) < 8){
						parser.sendToServer(otherPlayer, 'M');
					}
				}
			}
			//update players view
			parser.sendToServer(player, 'M');
			gameState.printView(1);
		}
		if (toTile != null && toTile.isContainer()) {
			Item[] items = ((ChestTile) toTile).open();
			char[] itemArray = new char[items.length];
			for (int i = 0; i < items.length; i++) {
				if(items[i] != null){
					itemArray[i] = items[i].getType();
				}
			}
			parser.sendInventory(player, itemArray);
		}
	}

	/**
	 * This method will return the Tile the that is in the direction the player
	 * want to move in
	 *
	 * @param direction
	 *            : direction of the player
	 * @param player
	 *            : player that is moving
	 * @return: Tile of destination
	 */
	private Tile destinationTile(Direction direction, Player player) {
		int x = player.getPosition().getX();
		int y = player.getPosition().getY();
		Tile[][] areaArray = gameState.getWorld(player).getArea();

		if (direction == Direction.UP) {
			if (y == 0) {
				return null;
			}
			return areaArray[y - 1][x];

		} else if (direction == Direction.DOWN) {
			if (y == areaArray.length - 1) {
				return null;
			}
			return areaArray[y + 1][x];

		} else if (direction == Direction.RIGHT) {
			if (x == areaArray[0].length - 1) {
				return null;
			}
			return areaArray[y][x + 1];

		} else if (direction == Direction.LEFT) {
			if (x == 0) {
				return null;
			}
			return areaArray[y][x - 1];
		}
		return null;
	}

	/**
	 * Called when a player uses an item in their inventory
	 */

	public void use(Player player, int inventorySlot){
		player.use(inventorySlot);
		parser.sendToServer(player, 'P');
	}

	/**
	 * Called when a player tries to pick up an object of the ground
	 *
	 * @param player
	 */
	public void pickUp(Player player) {
		Position playerPosition = player.getPosition();
		Item item = gameState.getItem(playerPosition);
		if (item != null) {
			player.collect(item);
			gameState.removeItem(playerPosition);
		}
		parser.sendToServer(player, 'P');
		parser.sendToServer(player, 'M');
	}

	/**
	 * Adds a random Zombie to the game.
	 */

	public void addZombie() {
		Position position = gameState.getRandomValidTile();
		Zombie z = new Zombie(new RandomZombie(), position);
		gameState.addZombie(z);
	}


	/**
	 * Activates the gameFrame when the game starts to prevent the
	 * game from drawing before the client has started the frame
	 */
	public void activateFrame(){
		frameActivated = true;
	}


	/*=================================
	 *
	 * GETTERS AND SETTERS
	 *
	 *=================================
	 */

	public List<char[][]> getGameView(int id) {
		Player player = gameState.getPlayer(id);
		return gameState.getGameView(player);
	}

	public Player getPlayer(int id) {
		return gameState.getPlayer(id);
	}

	public ArrayList<Player> getPlayerList() {
		return gameState.getPlayerList();
	}

	public GameState getGameState() {
		return gameState;
	}

	public ServerParser getParser() {
		return parser;
	}

	/**
	 * This Method is only called when the game is first made and places the
	 * players into the world, this will probably be redundant as we progress
	 *
	 * @param playerCount
	 *            : how many players in the game
	 * @param width
	 *            : width of the game space
	 * @param height
	 *            : height of the game space
	 * @param a
	 *            : the outside area that the players spawn in
	 * @return: a list of players in the game
	 */

	private ArrayList<Player> placePlayers(int playerCount, int height,
			int width, Area a) {
		double[] xCoords = { 0, 0.5, 1, 0.5 };
		double[] yCoords = { 0.5, 0, 0.5, 1 };
		ArrayList<Player> list = new ArrayList<Player>();
		for (int count = 0; count < playerCount; count++) {
			int x = (int) ((width - 1) * xCoords[count]);
			int y = (int) ((height - 1) * yCoords[count]);
			int id = count + 1;
			Position position = new Position(x, y, a);
			Player p = new Player(position, id);
			list.add(p);
		}
		return list;
	}
}
