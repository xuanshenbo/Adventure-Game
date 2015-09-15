/**
 * This is the area class that holds a 2d array of tiles.
 * To create a new Area you will need to give it a height and
 * width. To generate a new random area you will need to create
 * a generator and call generateWorld
 */

package state;

import java.util.ArrayList;
import java.util.Random;

import static utilities.PrintTool.p;
import state.Tile.TileType;
import logic.Generator;

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
	private Position entrance; // the position on the parent of the entrance to this area
	private ArrayList<Area> children = new ArrayList<Area>();

	public Area(int height, int width, AreaType t, Position p){
		type = t;
		area = new Tile[height][width];
		entrance = p;
	}

	public AreaType getType() {
		return type;
	}

	public Tile[][] getArea() {
		return area;
	}

	public Position getEntrance() {
		return entrance;
	}

	public void setEntrance(Position entrance) {
		this.entrance = entrance;
	}

	public void generateWorld(Generator g){
		g.fillArea(area, children);
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
		for(Area a: children){
			System.out.println(a.getEntrance());
			a.printArea();
		}
	}
}
