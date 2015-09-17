/**
 * This creates a random world, taking in some parameters and randomly
 * placing these object in the world.
 * 
 * The arguments that are passed in on creation are:
 * int trees: this is the ratio of trees in the world.
 * int buildings: this is the amount of 5 by 5 buildings
 * int caves: this is the amount of caves that are 5*10 with a 2*2 entrance
 * 
 * MAJOR BUG: will infinite loop out if it cannot fit the object in the map
 */
package logic;

import static utilities.PrintTool.p;

import java.util.ArrayList;
import java.util.Random;

import state.Area;
import state.Area.AreaType;
import state.Position;
import state.Tile;
import state.Tile.TileType;

public class Generator {

	private int trees;
	private int buildings;
	private int caves;
	private int chests;

	public Generator(int trees, int buildings, int caves, int chests){
		this.trees = trees;
		this.buildings = buildings;
		this.caves = caves;
		this.chests = chests;
	}

	public int treeRatio(){
		return trees;
	}
	
	public int numberOfBuildings(){
		return buildings;
	}
	
	public void fillArea(Tile[][] area, ArrayList<Area> children){
		for(int row=1; row < area.length-1; row++){
			for(int col=1; col < area[0].length-1; col++){
				//generate trees
				if(new Random().nextInt(trees) == trees-1){
					area[row][col] = new Tile(TileType.TREE);
				}
			}
		}
		boolean[][] invalidPosition = new boolean[area.length][area[0].length];

		//place the buildings		
		for(int count = 0; count < buildings; count++){
			boolean placed = false;
			while(!placed){
				int randomRow = new Random().nextInt(area.length-4)+1;
				int randomCol = new Random().nextInt(area[0].length-6)+1;
				boolean placeClear = true;
				//check if the random place is open for a building
				for(int row = randomRow; row < randomRow+3; row++){
					for(int col = randomCol; col < randomCol+5; col++){
						if(invalidPosition[row][col]){
							placeClear = false;
							row = row+area.length;
							col = col+area[0].length;
						}
					}
				}
				//place the building down
				if(placeClear){
					for(int row = randomRow; row < randomRow+3; row++){
						for(int col = randomCol; col < randomCol+5; col++){
							area[row][col] = new Tile(TileType.BUILDING);
						}
					}
					//Create a boarder around the building so that no buildings can be side by side.
					for(int row = randomRow-1; row < randomRow+4; row++){
						for(int col = randomCol-1; col < randomCol+6; col++){
							invalidPosition[row][col] = true;
						}
					}
					//place the door and create the new area
					area[randomRow+2][randomCol+2] = new Tile(TileType.DOOR);
					area[randomRow+3][randomCol+2] = null;
					Area building = new Area(5, 5, AreaType.BUILDING, new Position(randomRow+2, randomCol+4));
					children.add(building);
					placed = true;
				}
			}			
		}
		//place the caves
		for(int count = 0; count < caves; count++){
			boolean placed = false;
			while(!placed){
				int randomRow = new Random().nextInt(area.length-3)+1;
				int randomCol = new Random().nextInt(area[0].length-3)+1;
				boolean placeClear = true;
				//check if the random place is open for a cave
				for(int row = randomRow; row < randomRow+2; row++){
					for(int col = randomCol; col < randomCol+2; col++){
						if(invalidPosition[row][col]){
							placeClear = false;
							row = row+area.length;
							col = col+area[0].length;
						}
					}
				}
				
				//place the building down
				if(placeClear){
					for(int row = randomRow; row < randomRow+2; row++){
						for(int col = randomCol; col < randomCol+2; col++){
							area[row][col] = new Tile(TileType.CAVE);
						}
					}
					//Create a boarder around the building so that no buildings can be side by side.
					for(int row = randomRow-1; row < randomRow+3; row++){
						for(int col = randomCol-1; col < randomCol+3; col++){
							invalidPosition[row][col] = true;
						}
					}
					//place the entrace and create the new area
					area[randomRow+1][randomCol+1] = new Tile(TileType.CAVEENTRANCE);
					Area building = new Area(5, 10, AreaType.CAVE, new Position(randomRow+1, randomCol+1));
					children.add(building);
					placed = true;
				}
			}
		}
		
		//place the chests
		for(int count = 0; count < chests; count++){
			
			boolean placed = false;
			while(!placed){
				int randomRow = new Random().nextInt(area.length-2)+1;
				int randomCol = new Random().nextInt(area[0].length-2)+1;
				
				if(!invalidPosition[randomRow][randomCol]){
					area[randomRow][randomCol] = new Tile(TileType.CHEST);					
					invalidPosition[randomRow][randomCol] = true;
					placed = true;
					
				}
				
			}
		}
	}
}
