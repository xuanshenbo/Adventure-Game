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
import model.tiles.Tile;
import control.Server;
import static utilities.PrintTool.p;

public class Game {

	private GameState gameState;
	private Server server;
	private Clock clock;

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
		ArrayList<Player> playerList = placePlayers(
				parameters.getPlayerCount(), height, width, area);
		this.gameState = new GameState(area, playerList);
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
		gameState.printState(false);
	}

	/**
	 * This method checks how many sombeis are in the world and adds Zombies if
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
			sendToServer(player, 'M');
		}
		if (toTile.isContainer()) {
			Item[] items = ((Cabinet) toTile).open();
			char[] itemArray = new char[items.length];
			for (int i = 0; i < items.length; i++) {
				itemArray[i] = items[i].getType();
			}
			// send signal to server to send to clients containing the inventory
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
		sendToServer(player, 'P');
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
		sendToServer(player, 'P');
	}

	/**
	 * Adds a random Zombie to the game.
	 */

	public void addZombie() {
		Position position = gameState.getRandomValidTile();
		Zombie z = new Zombie(new RandomZombie(), position);
		gameState.addZombie(z);
	}

	/*********************************
	 *
	 * GETTERS AND SETTERS
	 *
	 * *******************************
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

	/**
	 * The following receives the user event from the client and process the
	 * logic needed to update the game.
	 *
	 * @param input
	 * @param out
	 * @param id
	 */
	public synchronized void processClientEvent(char[] message, Writer out,
			int id) {
		switch (message[0]) {
		case 'M'://move [M, direction]
			move(gameState.getPlayer(id), parseDirection(message[1]));
			break;
		case 'U': //use [I, int inventorySlot)
			use(gameState.getPlayer(id), message[1]);
			break;
		case 'P'://Pickup [P, _]
			pickUp(gameState.getPlayer(id));
		}
	}

	/**
	 * The following transferred a char sent from the client into a Direction
	 *
	 * @param dir
	 * @return
	 */
	public synchronized Direction parseDirection(char dir) {
		switch (dir) {
		case 'N':
			return Direction.UP;
		case 'S':
			return Direction.DOWN;
		case 'W':
			return Direction.LEFT;
		case 'E':
			return Direction.RIGHT;
		default:
			return null;
		}
	}

	public void sendToServer(Player player, char action){
		char[] message;
		if(action == 'M'){
			List<char[][]> view = getGameView(player.getId());

			message = new char[451];
			message[0] = action;
			int index = 1;
			for(int r = 0; r < 15; r++){
				for(int c = 0; c < 15; c++){
					message[index++] = view.get(0)[r][c];
					message[index++] = view.get(1)[r][c];
				}
			}
		}else if(action == 'P'){
			char[] inventory = new char[player.getInventory().length];
			int happiness = player.getHappiness();
			message = new char[inventory.length+2];
			message[0] = action;
			message[1] = (char)(happiness +'0');
			for(int i = 2; i < inventory.length; i++){
				message[i] = inventory[i];
			}

		}else{
			message = new char[0];
		}

		try {
			server.getWriters()[player.getId()].write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
