/**
 * This holds the variables that are needed to create a new area.
 * The arguements that are passed in on creation are:
 * int trees: this is the ratio of trees in the world.
 */
package logic;

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
		for(int row=0; row < area.length; row++){
			for(int col=0; col < area[0].length; col++){
				//generate trees
				if(new Random().nextInt(trees) == trees-1){
					area[row][col] = new Tile(TileType.TREE);
				}
			}
		}
	}

}
