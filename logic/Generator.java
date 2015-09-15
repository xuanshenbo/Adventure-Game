/**
 * This holds the variables that are needed to create a new area.
 * The arguements that are passed in on creation are:
 * int trees: this is the ratio of trees in the world.
 */
package logic;

import static utilities.PrintTool.p;
import java.util.Random;
import state.Tile;
import state.Tile.TileType;

public class Generator {

	private int trees;
	private int buildings;

	public Generator(int trees, int buildings){
		this.trees = trees;
		this.buildings = buildings;
	}

	public int treeRatio(){
		return trees;
	}
	
	public int numberOfBuildings(){
		return buildings;
	}
	
	public void fillArea(Tile[][] area){
		for(int row=1; row < area.length-1; row++){
			for(int col=1; col < area[0].length-1; col++){
				//generate trees
				if(new Random().nextInt(trees) == trees-1){
					area[row][col] = new Tile(TileType.TREE);
				}
			}
		}

		//place the buildings
		boolean[][] buildingTest = new boolean[area.length][area[0].length];
		
		for(int count = 0; count < buildings; count++){
			boolean placed = false;
			while(!placed){
				int randomRow = new Random().nextInt(area.length-5);
				int randomCol = new Random().nextInt(area[0].length-5);
				boolean bad = false;
				for(int row = randomRow; row < randomRow+5; row++){
					for(int col = randomCol; col < randomCol+5; col++){
						if(buildingTest[row][col]){
							bad = true;
							row = row+area.length;
							col = col+area[0].length;
						}
					}
				}
				if(!bad){
					for(int row = randomRow; row < randomRow+5; row++){
						for(int col = randomCol; col < randomCol+5; col++){
							area[row][col] = new Tile(TileType.BUILDING);
							buildingTest[row][col] = true;
						}
					}
					placed = true;
				}
			}
		}
	}
}
