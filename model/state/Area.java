/**
 * This is the main area class, it is made up of tiles and internal
 * areas. It holds no logic and is just a state class.
 * It it a certian type of area, building, cave etc. It also holds
 * another 2d array that holds all the objects on the ground
 * in the area.
 * To create a new Area you will need to give it a height and
 * width. To generate a new random area you will need to create
 * a generator and call generateWorld
 */

package model.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.sun.xml.internal.bind.CycleRecoverable;

import dataStorage.pointers.AreaPointer;
import static utilities.PrintTool.p;
import model.items.Item;
import model.logic.Generator;
import model.tiles.GroundTile;
import model.tiles.Tile;
import model.tiles.GroundTile.TileType;

//@XmlType(propOrder = { "type", "area", "items", "entrance", "internalAreas", "exitPosition" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Area implements CycleRecoverable {

	@XmlType(name = "AreaType")
	@XmlEnum
	public enum AreaType {
		OUTSIDE('G'),
		BUILDING('W'),
		CAVE('R');

		public final char id;

		private AreaType(char c){
			id = c;
		}

		// for serializing final fields purpose.
		private AreaType() {
			this('\u0000');
		}

	}

	private final AreaType type; // the type of area this is and determines the ground texture
	@XmlTransient
	private final Tile[][] area; // the array of tiles that make up the area
	@XmlTransient
	private final Item[][] items; // the array of items in the area based on their location
	private Position entrance; // the position on the parent of the entrance to this area
	@XmlElementWrapper
	@XmlElement(name="internalArea")
	private ArrayList<Area> internalAreas = new ArrayList<Area>();
	private Position exitPosition;
	@XmlElementWrapper
	@XmlElement(name="caveEntrance")
	private ArrayList<Position> caveEntrances = new ArrayList<Position>();
	@XmlTransient
	private GameState gameState;// the gameState that stores this area.

	public Area(int height, int width, AreaType t, Position p){
		type = t;
		area = new Tile[height][width];
		items = new Item[height][width];
		entrance = p;

		for(int row = 0; row < area.length; row++){
			for(int col = 0; col < area[0].length; col++){
				Position position = new Position(col, row, this);
				if(t.equals(AreaType.CAVE)){
					area[row][col] = new GroundTile(TileType.ROCK, position);
				}else if(t.equals(AreaType.BUILDING)){
					area[row][col] = new GroundTile(TileType.WOOD, position);
				}
			}
		}
	}

	public void addGameState(GameState gameState){
		this.gameState = gameState;
	}

	@SuppressWarnings("unused")
	// for serializing final fields purpose.
	private Area() {
		this(0, 0, null, null);
	}

	/**
	 * Finds the nearest cave entrance to the position, it is called by the
	 * RunZombie strategy
	 * @param position: the position of the Zombie
	 * @return: the nearest cave to the Zombie
	 */
	public Position getNearestCaveEntrance(Position position) {
		double bestDistance = Double.MAX_VALUE;
		Position closestCave = position;
		for(Position cave: caveEntrances){
			double dx = Math.abs(position.getX() - cave.getX());
			double dy = Math.abs(position.getY() - cave.getY());
			double distance = Math.sqrt((dx*dx)*(dy*dy));
			if(distance < bestDistance){
				bestDistance = distance;
				closestCave = cave;
			}
		}
		return closestCave;
	}

	/**
	 * Finds the nearest player to the position, it is called by the
	 * ChaseZombie strategy
	 * @param position: the position of the Zombie
	 * @return
	 */
	public Position getNearestPlayer(Position position) {
		return gameState.getNearestPlayer(position);
	}

	/**
	 * Turns the tile array into a char array, this is used to make the view
	 * for the client
	 * @return: the char array.
	 */
	public char[][] getCharArray(){
		char[][] charArray = new char[area.length][area[0].length];
		for(int row=0; row<charArray.length; row++){
			for(int col=0; col<charArray[0].length; col++){
				charArray[row][col] = area[row][col].getType();
			}
		}
		return charArray;
	}

	/**
	 * gets a specific internal area based on the Players position
	 * @param playerPosition: the current players position
	 * @return: the area that is link to the players position or null if
	 * not on an entrance.
	 */
	public Area getInternalArea(Position playerPosition) {
		for(Area a: internalAreas){
			if(a.entrance.equals(playerPosition)){
				return a;
			}
		}
		return null;
	}

	/**
	 * Sets the exit position of this area
	 */
	public void setExitPosition(Position p){
		exitPosition = p;
	}

	/**
	 * Sets the entrance position of this area
	 */
	public void setEntrance(Position entrance) {
		this.entrance = entrance;
	}

	/**
	 * Adds a cave entrance to the world, this is called when the
	 * cave is made.
	 * @param caveEntrance
	 */
	public void addCaveEntrance(Position caveEntrance) {
		caveEntrances.add(caveEntrance);

	}

	/**
	 * Creates the world, this is called on the first area created and uses
	 * the generator
	 */
	public void generateWorld(Generator g){
		g.fillTiles(this);
		g.placeLoot(this);
	}

	public String toString(){
		return (entrance+" "+type);
	}

	public void printTile(Position p){
		//System.out.print(area[p.getY()][p.getX()]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(area);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Area other = (Area) obj;
		if (!Arrays.deepEquals(area, other.area))
			return false;
		return true;
	}

	// ================================================
	// getters from here
	// ================================================

	public AreaType getType() {
		return type;
	}

	public Tile[][] getArea() {
		return area;
	}

	public Item[][] getItems(){
		return items;
	}

	public Position getEntrance() {
		return entrance;
	}

	public ArrayList<Area> getInternalAreas(){
		return internalAreas;
	}

	public Position getExitPosition() {
		return exitPosition;
	}

	/**========================
	 * TESTING METHODS
	 * ========================
	 */

	/**
	 * This prints out the area for testing
	 */
	public void printArea(){
		p("TEST");
		for(int row = 0; row < area.length; row++){
			for(int col = 0; col < area[0].length; col++){
				if(area[row][col] == null){
					//System.out.print(type.id);
				}else{
					//System.out.print(area[row][col]);
				}
			}
			System.out.println("");
		}
		System.out.println("");
		for(Area a: internalAreas){
			System.out.println(a.getEntrance());
			a.printArea();
		}
	}

	@Override
	public Object onCycleDetected(Context arg0) {
		AreaPointer areaPointer = new AreaPointer();
		areaPointer.setType(type);
		areaPointer.setArea(area);
		areaPointer.setItems(items);
		return areaPointer;
	}

}
