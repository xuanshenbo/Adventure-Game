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

import model.items.Bag;
import model.items.Item;
import model.npcs.ChaseZombie;
import model.npcs.RandomZombie;
import model.npcs.RunZombie;
import model.npcs.Zombie;
import model.state.Area;
import model.state.Container;
import model.state.GameState;
import model.state.Player;
import model.state.Position;
import model.state.Area.AreaType;
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
	private int maxZombies = 0;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT;
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
	public Game(Server server, WorldParameters parameters, boolean test) {
		this.server = server;
		this.clock = new Clock(2000, this);
		int height = parameters.getHeight();
		int width = parameters.getWidth();
		Generator g = new Generator(parameters);
		Area area = new Area(height, width, AreaType.OUTSIDE, null);
		maxZombies = 5;
		area.generateWorld(g);
		ArrayList<Player> playerList = placePlayers(parameters.getPlayerCount(), height, width, area);
		this.gameState = new GameState(area, playerList);
		parser = new ServerParser(this, server, test);
		if(!test){
			clock.start();
		}
	}

	/**
	 * An alternative constructor that just creates a gameState to be checked,
	 * this is used only for testing.
	 * @param state
	 */
	public Game(GameState state) {
		this.gameState = state;
	}

	/**
	 * An alternative constructor that creates the game based on the size of the world, the
	 * density of the objects(trees, caves, buildings) in the world and a difficulty
	 * @param server: the server to connect with
	 * @param height: the height of the world
	 * @param width: the width of the world
	 * @param difficulty: the difficulty("easy", "medium", "hard")
	 * @param density: the density of the objects in the world.
	 */

	public Game(Server server, int height, int width, String difficulty, int density){
		this.server = server;
		this.clock = new Clock(2000, this);
		Area area = new Area(height, width, AreaType.OUTSIDE, null);
		maxZombies = getMaxZombies(height*width, difficulty);
		Generator g = new Generator(difficulty, density, height, width);
		area.generateWorld(g);
		ArrayList<Player> playerList = placePlayers(4, height, width, area);
		this.gameState = new GameState(area, playerList);
		parser = new ServerParser(this, server, false);
		clock.start();
	}


	/**
	 * Sets the maximum Zombies allowed in the world at one time, this is based on the
	 * difficulty that has been set when creating the game
	 * @param totalTiles: total tiles of the world
	 * @param difficulty: the difficulty setting
	 * @return: how many Zombies there will be in the world at one time
	 */
	private int getMaxZombies(int totalTiles, String difficulty) {
		int maxZombies = 0;

		if(difficulty.equals("easy")){
			maxZombies = totalTiles/100;
		}else if(difficulty.equals("medium")){
			maxZombies = totalTiles/70;
		}else{
			maxZombies = totalTiles/50;
		}
		return maxZombies;
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
		for(Player player: gameState.getPlayerList()){
			parser.sendToServer(player, 'T');
		}
		updateZombies();
		if(frameActivated){
			for(Player p: gameState.getPlayerList()){
				parser.sendToServer(p, 'M');
			}
		}
	}

	/**
	 * This method checks how many Zombies are in the world and adds Zombies if
	 * needed, it then checks the time and changes the zombies actions based on
	 * the time. From there is makes all the Zombies move.
	 */

	public void updateZombies() {
		if (gameState.getZombieList().size() < maxZombies) {
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
					player.loseHappiness();
					parser.sendToServer(player, 'H');
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
		if (Math.abs(playerX - zombieX) < 5 && Math.abs(playerY - zombieY) < 5) {
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
				if(otherPlayer.isInGame()){
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
			//gameState.printView(1);
		}
		if (toTile != null && toTile.isContainer()) {
			ChestTile container = (ChestTile) toTile;
			if(player.hasOpenedChest(container) || player.getKey()){
				player.addChest(container);
				player.setOpenContainer(container);
				Item[] items = container.open();
				parser.sendContainer(player, items);
			}
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
	 * Called when a player opens their inventory
	 * @param player
	 */

	public void sendInventory(Player player) {
		Item[] inventory = player.getInventory();
		parser.sendInventory(player, inventory);

	}

	/**
	 * Called when a player uses an item in their inventory
	 */

	public void use(Player player, int inventorySlot){
		Item item = player.getItemFromInventory(inventorySlot);
		if(item != null){
			parser.sendMessage(player, "You used "+item);
		}
		if(item != null){
			Item[] inventory = player.use(inventorySlot);

			//parser.sendToServer(player, 'I');
			parser.sendToServer(player, 'H');
			if(inventory != null){
				parser.sendContainer(player, inventory);
			}
		}
	}

	/**
	 * Called when a player tries to pick up an object of the ground
	 *
	 * @param player
	 */
	public void pickUp(Player player) {
		Position playerPosition = player.getPosition();
		Item item = gameState.getItem(playerPosition);
		boolean pickedUp = false;
		if (item != null) {
			pickedUp = player.collect(item);
			if(pickedUp){
				gameState.removeItem(playerPosition);
			}
		}if(pickedUp){
			parser.sendToServer(player, 'I');
			parser.sendToServer(player, 'M');
		}
	}

	/**
	 * Called when the player tries to drop an object on the ground
	 * @param player
	 */
	public void drop(Player player, int inventorySlot) {
		Position playerPosition = player.getPosition();
		Item item = player.getItemFromInventory(inventorySlot);
		if(item != null && noObjects(playerPosition)){
			player.removeItem(inventorySlot);
			gameState.addItem(playerPosition, item);
		}
		parser.sendToServer(player, 'I');
		parser.sendToServer(player, 'M');

	}

	/**
	 * Called when a player tries to drop an item on the ground, it checks
	 * that there is not an object on the ground already.
	 * @param playerPosition
	 * @return
	 */
	public boolean noObjects(Position playerPosition) {
		int row = playerPosition.getY();
		int col = playerPosition.getX();
		Area area = playerPosition.getArea();
		return area.getItems()[row][col] == null;
	}

	public void moveInventory(Player player, int fromSlot, int toSlot) {
		Item fromItem = player.getItemFromInventory(fromSlot);
		Item toItem = player.getItemFromInventory(toSlot);
		if(toItem instanceof Bag){
			Bag bag = (Bag) toItem;
			boolean notBag = bag.addItem(fromItem);
			if(!notBag){
				player.removeItem(fromSlot);
			}
			return;
		}
		player.getInventory()[fromSlot] = toItem;
		player.getInventory()[toSlot] = fromItem;

		parser.sendInventory(player, player.getInventory());;

	}

	public void moveFromContainerToInventory(Player player, int containerSlot) {
		p(containerSlot);
		player.moveToInventory(containerSlot);
		parser.sendInventory(player, player.getInventory());

	}

	/**
	 * Called when a player selects an item.
	 * @param player
	 * @param i
	 */

	public void select(Player player, int inventorySlot) {
		player.setSelected(inventorySlot);

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



	public void activatePlayer(int id) {
		gameState.getPlayer(id).makeActive();

	}

	public void deActivatePlayer(int id) {
		gameState.getPlayer(id).makeInactive();

	}

	/**
	 * This sends an array of available players to the server
	 * when requested.
	 * @param id:
	 */
	public void getAvailablePlayers(int id) {
		char[] players = new char[4];
		for(int i = 0; i < players.length; i++){
			p(gameState);
			p(gameState.getPlayer(i+1));
			if(!gameState.getPlayer(i+1).isInGame()){
				players[i] = 'Y';
			}else{
				players[i] = 'N';
			}
		}
		Player player = gameState.getPlayer(id);
		if(player == null){
			player = new Player(0);
		}
		System.out.println("Game 441: before sending players");//debug
		parser.sendPlayers(player, players);
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

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
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
