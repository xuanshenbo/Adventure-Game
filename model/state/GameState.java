/**
 * The main class that stores the state of the game.
 */

package model.state;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import model.items.Item;
import model.npcs.Zombie;
import model.tiles.Tile;
import static utilities.PrintTool.p;

@XmlRootElement(namespace = "savedGame")
@XmlAccessorType(XmlAccessType.FIELD)
public class GameState {

	@XmlElementWrapper
	@XmlElement(name="player")
	private ArrayList<Player> playerList = new ArrayList<Player>(); // list of players in the game
	@XmlElementWrapper
	@XmlElement(name="zombie")
	private ArrayList<Zombie> zombieList = new ArrayList<Zombie>(); // list of zombies in the game
	private Area world; // The game world
	private int viewPortSize = 31;
	private int time;
	private boolean day;
	private String loadedFile;

	public GameState(Area a, ArrayList<Player> p){
		this.world = a;
		this.playerList = p;
		time = 0;
		day = true;
		world.addGameState(this);
	}

	@SuppressWarnings("unused")
	private GameState() {
		this(null, null);
	}

	/**
	 * Goes through the player list and checks the players id
	 * @param id: the id of the player to return
	 * @return: the player with the id or null if no player with
	 * that id
	 */
	public Player getPlayer(int id) {
		for(Player p: playerList){
			if(p.getId() == id){
				return p;
			}
		}
		return null;
	}

	/**
	 * Goes through the player list and returns the nearest players
	 * position to the pased in parameter. This is used by the ChaseZombie
	 * @param position: position of the Zombie
	 * @return: nearest player to the Zombie
	 */
	public Position getNearestPlayer(Position position){
		double bestDistance = Double.MAX_VALUE;
		Position closestPlayer = position;
		for(Player player: playerList){
			Position playerPosition = player.getPosition();
			double dx = Math.abs(position.getX() - playerPosition.getX());
			double dy = Math.abs(position.getY() - playerPosition.getY());
			double distance = Math.sqrt((dx*dx)*(dy*dy));
			if(distance < bestDistance){
				bestDistance = distance;
				closestPlayer = playerPosition;
			}
		}
		return closestPlayer;
	}

	/**
	 * Adding a zombie to the world
	 * @param z: Zombie to be added
	 */
	public void addZombie(Zombie z) {
		zombieList.add(z);
	}

	/**
	 * Removes an object from a game, used when the player picks
	 * them up.
	 * @param position
	 */
	public void removeItem(Position position) {
		int x = position.getX();
		int y = position.getY();
		world.getItems()[y][x] = null;
	}

	/**
	 * returns the item that is in the position of the player, used
	 * to add it to the players inventory
	 * @param playerPosition
	 * @return
	 */
	public Item getItem(Position playerPosition) {
		int x = playerPosition.getX();
		int y = playerPosition.getY();
		return world.getItems()[y][x];
	}

	/**
	 * This checks where the player is and returns the area that they
	 * are in.
	 * @param player
	 * @return
	 */
	public Area getWorld(Player player) {
		if(player.getPosition().getArea() == world){
			return world;
		} else{
			for(Area childArea: world.getInternalAreas()){
				if(player.getPosition().getArea() == childArea){
					return childArea;
				}
			}
		}
		return null;
	}

	/**
	 * This returns a random tile in the top level area that is ground
	 * @return
	 */

	public Position getRandomValidTile() {
		Tile[][] areaArray = world.getArea();
		Position validPosition = null;
		boolean positionFound = false;
		while(!positionFound){
			int x = (int) (Math.random()*areaArray[0].length);
			int y = (int) (Math.random()*areaArray.length);
			Position randomPosition = new Position(x, y, world);
			Tile randomTile = areaArray[y][x];
			if(randomTile.isGround()){
				boolean noCharacters = true;
				for(Player p: playerList){
					if(p.getPosition() == randomPosition){
						noCharacters = false;
					}
				}
				for(Zombie z: zombieList){
					if(z.getPosition() == randomPosition){
						noCharacters = true;
					}
				}
				if(noCharacters){
					validPosition = randomPosition;
					positionFound = true;
				}
			}
		}
		return validPosition;
	}

	/**
	 * This methods returns a 15 by 15 array of tiles that are around the player,
	 * this is for the network to send down the pipe to the client to draw.
	 * @param player
	 * @return
	 */
	public List<char[][]> getGameView(Player player){
		char[][] view = new char[viewPortSize][viewPortSize];
		char[][] objects = new char[viewPortSize][viewPortSize];

		Area a = getWorld(player);
		// TODO Auto-generated method stub
		int left = player.getPosition().getX() - (viewPortSize/2);
		int right = player.getPosition().getX() + (viewPortSize/2);
		int top = player.getPosition().getY() - (viewPortSize/2);
		int bottom = player.getPosition().getY() + (viewPortSize/2);

		int r = 0;
		int c = 0;
		for(int row = top; row < bottom+1; row++){
			for(int col = left; col < right+1; col++){
				if(row>-1 && col >-1 && row <a.getArea().length && col < a.getArea()[0].length){
					if(a.getItems()[row][col] != null){
						objects[r][c] = a.getItems()[row][col].getType();
					}else{
						objects[r][c] = '\u0000';
					}
					boolean playerPos = false;
					for(Player p: playerList){
						if(p.getPosition().getX() == col && p.getPosition().getY() == row && p.getPosition().getArea() == a){
							view[r][c] = (char) (p.getId()+'0');
							playerPos = true;
						}
					}
					for(Zombie z: zombieList){
						if(z.getPosition().getX() == col && z.getPosition().getY() == row && z.getPosition().getArea() == a && !playerPos){
							view[r][c] = (char) (z.getid());
							playerPos = true;
						}
					}
					if(!playerPos){
						view[r][c] = a.getArea()[row][col].getType();
					}
				}
				c++;
			}
			c=0;
			r++;
		}

		System.out.println("\nPlayer 1 view");
		for(int row = 0; row<objects.length; row++){
			for(int col = 0; col<objects[0].length; col++){
				if(objects[row][col] != '\u0000'){
					System.out.print(objects[row][col]);
				}else{
					System.out.print("N");
				}
			}
			System.out.println("");
		}

		List<char[][]> worldInfo = new ArrayList<char[][]>();
		worldInfo.add(view);
		worldInfo.add(objects);
		return worldInfo;
	}

	public void setLoadedFile(String loadedFile) {
		this.loadedFile = loadedFile;
	}

	public void setTime(int time){
		this.time = time;
	}

	public void setDay(boolean day){
		this.day = day;
	}

	public void addItem(Position playerPosition, Item item) {
		int col = playerPosition.getX();
		int row = playerPosition.getY();
		Area a = playerPosition.getArea();
		a.getItems()[row][col] = item;
	}

	// ================================================
	// getters from here
	// ================================================

	public ArrayList<Player> getPlayerList(){
		ArrayList<Player> activePlayerList = new ArrayList<Player>();
		for(Player player: playerList){
			if(player.isInGame()){
				activePlayerList.add(player);
			}
		}
		return activePlayerList;
	}

	public Area getWorld() {
		return world;
	}

	public int getViewPortSize() {
		return viewPortSize;
	}

	public ArrayList<Zombie> getZombieList(){
		return zombieList;
	}

	public int getTime(){
		return time;
	}

	public boolean getDay(){
		return day;
	}

	public String getLoadedFile() {
		return loadedFile;
	}

	//===================================
	// DEBUGGING AND TESTING METHODS
	//===================================


	/**
	 * This method prints out the game state to the console
	 * used for debugging.
	 * @param innerAreas: true or false to draw the inner areas.
	 */
	public void printState(boolean innerAreas){
		String suffix = "am";
		if(day){
			suffix = "am";
		}else{
			suffix = "pm";
		}
		System.out.println("Time: "+time+suffix);
		Tile[][] a = world.getArea();

		for(int row = 0; row<a.length; row++){
			for(int col = 0; col<a[0].length; col++){
				boolean playerPos = false;
				for(Player p: playerList){
					if(p.getPosition().getX() == col && p.getPosition().getY() == row && p.getPosition().getArea() == world){
						//System.out.print(p);
						playerPos = true;
					}
				}
				for(Zombie z: zombieList){
					if(z.getPosition().getX() == col && z.getPosition().getY() == row && z.getPosition().getArea() == world && !playerPos){
						//System.out.print(z.getid());
						playerPos = true;
					}
				}
				if(!playerPos){
					//System.out.print(a[row][col]);
				}
			}
			//System.out.println("");
		}

//		for(int row = 0; row<a.length; row++){
//			for(int col = 0; col<a[0].length; col++){
//				System.out.print("("+a[row][col].getPosition()+")");
//			}
//			System.out.println("");
//		}
		if(innerAreas){
			for(Area innerArea: world.getInternalAreas()){
				System.out.println("");
				System.out.println(innerArea.getEntrance());

				Tile[][] innerTiles = innerArea.getArea();

				for(int row = 0; row<innerTiles.length; row++){
					for(int col = 0; col<innerTiles[0].length; col++){
						boolean playerPos = false;
						for(Player p: playerList){
							if(p.getPosition().getX() == col && p.getPosition().getY() == row && p.getPosition().getArea() == innerArea){
								//System.out.print(p);
								playerPos = true;
							}
						}
						if(!playerPos){
							//System.out.print(innerTiles[row][col]);
						}
					}
					//System.out.println("");
				}
			}
		}
		//System.out.println("");
	}

	public void printView(int id){
		char[][] playerOneView = getGameView(playerList.get(0)).get(0);
		//System.out.println("\nPlayer 1 view");
		for(int row = 0; row<playerOneView.length; row++){
			for(int col = 0; col<playerOneView[0].length; col++){
				if(playerOneView[row][col] != '\u0000'){
					//System.out.print(playerOneView[row][col]);
				}else{
					//System.out.print("N");
				}
			}
			//System.out.println("");
		}
	}
}
