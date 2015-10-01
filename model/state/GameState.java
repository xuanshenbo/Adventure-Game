/**
 * The main class that stores the state of the game.
 */

package model.state;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import model.items.Item;
import model.npcs.Zombie;
import model.tiles.Tile;
import static utilities.PrintTool.p;

@XmlRootElement(namespace = "shelf")
public class GameState {

	private ArrayList<Player> playerList = new ArrayList<Player>(); // list of players in the game
	private ArrayList<Zombie> zombieList = new ArrayList<Zombie>(); // list of zombies in the game
	private Area world; // The game world
	private int viewPortSize = 15;
	private int time;

	public GameState(Area a, ArrayList<Player> p){
		this.world = a;
		this.playerList = p;
		time = 0;
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

	public void addZombie(Zombie z) {
		zombieList.add(z);
	}

	public void removeItem(Position position) {
		int x = position.getX();
		int y = position.getY();
		world.getItems()[y][x] = null;
	}

	public Item getItem(Position playerPosition) {
		int x = playerPosition.getX();
		int y = playerPosition.getY();
		return world.getItems()[y][x];
	}

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
			int x = (int) (Math.random()*areaArray.length);
			int y = (int) (Math.random()*areaArray[0].length);
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

		int left = player.getPosition().getX() - (viewPortSize/2);
		int right = player.getPosition().getX() + (viewPortSize/2);
		int top = player.getPosition().getY() - (viewPortSize/2);
		int bottom = player.getPosition().getY() + (viewPortSize/2);

		int r = 0;
		int c = 0;
		for(int row = left; row < right+1; row++){
			for(int col = top; col < bottom+1; col++){
				if(row>-1 && col >-1 && row <a.getArea().length && col < a.getArea()[0].length){
					if(a.getItems()[row][col] != null){
						objects[r][c] = a.getItems()[row][col].getType();
					}else{
						objects[r][c] = '\u0000';
					}
					boolean playerPos = false;
					for(Player p: playerList){
						if(p.getPosition().getX() == row && p.getPosition().getY() == col && p.getPosition().getArea() == a){
							view[r][c] = (char) (p.getId()+'0');
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
		List<char[][]> worldInfo = new ArrayList<char[][]>();
		worldInfo.add(view);
		worldInfo.add(objects);
		return worldInfo;
	}














	/**
	 * This method prints out the game state to the console
	 * used for debugging.
	 */
	public void printState(){
		Tile[][] a = world.getArea();

		for(int row = 0; row<a.length; row++){
			for(int col = 0; col<a[0].length; col++){
				boolean playerPos = false;
				for(Player p: playerList){
					if(p.getPosition().getX() == col && p.getPosition().getY() == row && p.getPosition().getArea() == world){
						System.out.print(p);
						playerPos = true;
					}
				}
				for(Zombie z: zombieList){
					if(z.getPosition().getX() == col && z.getPosition().getY() == row && z.getPosition().getArea() == world){
						System.out.print(z.getid());
						playerPos = true;
					}
				}
				if(!playerPos){
					System.out.print(a[row][col]);
				}
			}
			System.out.println("");
		}

		for(Area innerArea: world.getInternalAreas()){
			System.out.println("");
			System.out.println(innerArea.getEntrance());

			Tile[][] innerTiles = innerArea.getArea();

			for(int row = 0; row<innerTiles.length; row++){
				for(int col = 0; col<innerTiles[0].length; col++){
					boolean playerPos = false;
					for(Player p: playerList){
						if(p.getPosition().getX() == col && p.getPosition().getY() == row && p.getPosition().getArea() == innerArea){
							System.out.print(p);
							playerPos = true;
						}
					}
					if(!playerPos){
						System.out.print(innerTiles[row][col]);
					}
				}
				System.out.println("");
			}
		}		
	}
	
	public void printView(int id){
		char[][] playerOneView = getGameView(playerList.get(0)).get(0);
		System.out.println("\nPlayer 1 view");
		for(int row = 0; row<playerOneView.length; row++){
			for(int col = 0; col<playerOneView[0].length; col++){
				if(playerOneView[row][col] != '\u0000'){
					System.out.print(playerOneView[row][col]);
				}else{
					System.out.print("N");
				}
			}
			System.out.println("");
		}
	}


	// ================================================
	// getters from here
	// ================================================

	@XmlElementWrapper
	@XmlElement(name="player")
	public ArrayList<Player> getPlayerList(){
		return playerList;
	}

	//@XmlElement
	public Area getWorld() {
		return world;
	}

	@XmlElement
	public int getViewPortSize() {
		return viewPortSize;
	}
	
	@XmlElement
	public ArrayList<Zombie> getZombieList(){
		return zombieList;
	}

}
