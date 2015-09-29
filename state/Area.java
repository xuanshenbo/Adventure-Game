/**
 * This is the area class that holds a 2d array of tiles.
 * To create a new Area you will need to give it a height and
 * width. To generate a new random area you will need to create
 * a generator and call generateWorld
 */

package state;

import items.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static utilities.PrintTool.p;
import tiles.GroundTile;
import tiles.Tile;
import logic.Generator;
import tiles.GroundTile.TileType;


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

	public AreaType getType() {
		return type;
	}

	public Tile[][] getTileArray() {
		return area;
	}

	public Item[][] getItemArray(){
		return items;
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

	public ArrayList<Area> getInternalAreas(){
		return internalAreas;
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
	public Position exitPosition() {
		return exitPosition;
	}

	public Position getEntrance() {
		return entrance;
	}

	public void setEntrance(Position entrance) {
		this.entrance = entrance;
	}

	public void generateWorld(Generator g){
		g.fillTiles(this);
	}

	public String toString(){
		return (entrance+" "+type);
	}

	public void printArea(){
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
}
