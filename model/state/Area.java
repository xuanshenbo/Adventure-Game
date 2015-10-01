/**
 * This is the area class that holds a 2d array of tiles.
 * To create a new Area you will need to give it a height and
 * width. To generate a new random area you will need to create
 * a generator and call generateWorld
 */

package model.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import static utilities.PrintTool.p;
import model.items.Item;
import model.logic.Generator;
import model.tiles.GroundTile;
import model.tiles.Tile;
import model.tiles.GroundTile.TileType;

@XmlType(propOrder = { "type", "area", "items", "entrance", "internalAreas", "exitPosition" })
public class Area {

	public enum AreaType {
		OUTSIDE('G'),
		BUILDING('W'),
		CAVE('R');

		public final char id;

		private AreaType(char c){
			id = c;
		}

	}

	private final AreaType type; // the type of area this is and determines the ground texture
	private final Tile[][] area; // the array of tiles that make up the area
	private final Item[][] items; // the array of items in the area based on their location
	private Position entrance; // the position on the parent of the entrance to this area
	private ArrayList<Area> internalAreas = new ArrayList<Area>();
	private Position exitPosition;
	private ArrayList<Position> caveEntrances = new ArrayList<Position>();

	public Area(int height, int width, AreaType t, Position p){
		type = t;
		area = new Tile[height][width];
		items = new Item[height][width];
		entrance = p;

		for(int row = 0; row < area.length; row++){
			for(int col = 0; col < area[0].length; col++){
				Position position = new Position(row, col, this);
				if(t.equals(AreaType.CAVE)){
					area[row][col] = new GroundTile(TileType.ROCK, position);
				}else if(t.equals(AreaType.BUILDING)){
					area[row][col] = new GroundTile(TileType.WOOD, position);
				}
			}
		}
	}
	

	public Position getNearestCaveEntrance(Position oldPosition) {
		double bestDistance = Double.MAX_VALUE;
		Position closestCave = oldPosition;
		for(Position cave: caveEntrances){
			double dx = Math.abs(oldPosition.getX() - cave.getX());
			double dy = Math.abs(oldPosition.getY() - cave.getY());
			double distance = Math.sqrt((dx*dx)*(dy*dy));
			if(distance < bestDistance){
				bestDistance = distance;
				closestCave = cave;
			}
		}
		return closestCave;
	}

	public Position getNearestPlayer(Position oldPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unused")
	private Area() {
		this(0, 0, null, null);
	}

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

	public void setExitPosition(Position p){
		exitPosition = p;
	}

	public void setEntrance(Position entrance) {
		this.entrance = entrance;
	}
	
	public void addCaveEntrance(Position caveEntrance) {
		caveEntrances.add(caveEntrance);
		
	}

	public void generateWorld(Generator g){
		g.fillTiles(this);
		g.placeLoot(this);
	}

	public String toString(){
		return (entrance+" "+type);
	}

	public void printArea(){
		p("TEST");
		for(int row = 0; row < area.length; row++){
			for(int col = 0; col < area[0].length; col++){
				if(area[row][col] == null){
					System.out.print(type.id);
				}else{
					System.out.print(area[row][col]);
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

	public void printTile(Position p){
		System.out.print(area[p.getY()][p.getX()]);
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

	@XmlTransient
	public AreaType getType() {
		return type;
	}

	@XmlTransient
	public Tile[][] getArea() {
		return area;
	}

	@XmlTransient
	public Item[][] getItems(){
		return items;
	}

	@XmlTransient
	public Position getEntrance() {
		return entrance;
	}

	@XmlTransient
	public ArrayList<Area> getInternalAreas(){
		return internalAreas;
	}

	@XmlTransient
	public Position getExitPosition() {
		return exitPosition;
	}

}
