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
package model.logic;

import static utilities.PrintTool.p;

import java.util.ArrayList;
import java.util.Random;

import model.items.Item;
import model.items.Key;
import model.state.Area;
import model.state.Position;
import model.state.Area.AreaType;
import model.tiles.BuildingTile;
import model.tiles.CaveEntranceTile;
import model.tiles.CaveTile;
import model.tiles.ChestTile;
import model.tiles.DoorTile;
import model.tiles.GroundTile;
import model.tiles.Tile;
import model.tiles.TreeTile;
import model.tiles.GroundTile.TileType;

public class Generator {

	private int trees;
	private int buildings;
	private int caves;
	private int chests;
	private int lootValue;

	public Generator(int trees, int buildings, int caves, int chests, int lootValue){
		this.trees = trees;
		this.buildings = buildings;
		this.caves = caves;
		this.chests = chests;
		this.lootValue = lootValue;
	}

	public Generator(WorldParameters parameters) {
		this.trees = parameters.getTrees();
		this.buildings = parameters.getBuildings();
		this.caves = parameters.getCaves();
		this.chests = parameters.getChests();
		this.lootValue = parameters.getLootValue();
	}

	public int treeRatio(){
		return trees;
	}

	public int numberOfBuildings(){
		return buildings;
	}

	public void placeLoot(Area area){
		ArrayList<Area> children = area.getInternalAreas();
		for(int row = 0; row<area.getArea().length; row++){
			for(int col = 0; col < area.getArea()[0].length; col++){
				if(area.getArea()[row][col] instanceof GroundTile){
					if(Math.random()*100 < lootValue){
						area.getItems()[row][col] = randomItem();
					}
				}
			}
		}
	}

	private Item randomItem() {
		int itemValue = (int) (Math.random()*10);
		if(itemValue < 2){
			//return amazing item
		}else if(itemValue < 5){
			//return ok item
		}
		return new Key();
	}

	public void fillTiles(Area area){
		ArrayList<Area> children = area.getInternalAreas();
		Tile[][] areaArray = area.getArea();

		for(int row=0; row < areaArray.length; row++){
			for(int col=0; col < areaArray[0].length; col++){
				areaArray[row][col] = new GroundTile(TileType.GRASS, new Position(row, col, area));
			}
		}

		for(int row=1; row < areaArray.length-1; row++){
			for(int col=1; col < areaArray[0].length-1; col++){
				//generate trees
				if(new Random().nextInt(trees) == trees-1){
					areaArray[row][col] = new TreeTile();
				}
			}
		}
		boolean[][] invalidPosition = new boolean[areaArray.length][areaArray[0].length];

		//place the buildings
		for(int count = 0; count < buildings; count++){
			boolean placed = false;
			while(!placed){
				int randomRow = new Random().nextInt(areaArray.length-4)+1;
				int randomCol = new Random().nextInt(areaArray[0].length-6)+1;
				boolean placeClear = true;

				//check if the random place is open for a building
				for(int row = randomRow; row < randomRow+3; row++){
					for(int col = randomCol; col < randomCol+5; col++){
						if(invalidPosition[row][col]){
							placeClear = false;
							row = row+areaArray.length;
							col = col+areaArray[0].length;
						}
					}
				}
				//place the building down
				if(placeClear){
					for(int row = randomRow; row < randomRow+3; row++){
						for(int col = randomCol; col < randomCol+5; col++){
							areaArray[row][col] = new BuildingTile();
						}
					}
					//Create a boarder around the building so that no buildings can be side by side.
					for(int row = randomRow-1; row < randomRow+4; row++){
						for(int col = randomCol-1; col < randomCol+6; col++){
							invalidPosition[row][col] = true;
						}
					}

					//place the door and create the new area
					Position entrance = new Position(randomRow+2, randomCol+2, area);
					areaArray[randomRow+3][randomCol+2] = new GroundTile(TileType.GRASS, new Position(randomRow+3, randomCol+2, area));
					Area building = new Area(5, 5, AreaType.BUILDING, new Position(randomCol+2, randomRow+2, area));
					Position exit = new Position(4, 2, building);

					areaArray[randomRow+2][randomCol+2] = new DoorTile(entrance, exit);
					building.getArea()[4][2] = new DoorTile(exit, entrance);
					children.add(building);
					placed = true;
				}
			}
		}
		//place the caves
		for(int count = 0; count < caves; count++){
			boolean placed = false;
			while(!placed){
				int randomRow = new Random().nextInt(areaArray.length-3)+1;
				int randomCol = new Random().nextInt(areaArray[0].length-3)+1;
				boolean placeClear = true;
				//check if the random place is open for a cave
				for(int row = randomRow; row < randomRow+2; row++){
					for(int col = randomCol; col < randomCol+2; col++){
						if(invalidPosition[row][col]){
							placeClear = false;
							row = row+areaArray.length;
							col = col+areaArray[0].length;
						}
					}
				}
				//place the cave entrance down
				if(placeClear){
					for(int row = randomRow; row < randomRow+2; row++){
						for(int col = randomCol; col < randomCol+2; col++){
							areaArray[row][col] = new CaveTile();
						}
					}
					//Create a boarder around the building so that no buildings can be side by side.
					for(int row = randomRow-1; row < randomRow+3; row++){
						for(int col = randomCol-1; col < randomCol+3; col++){
							invalidPosition[row][col] = true;
						}
					}
					Position entrance = new Position(randomRow+1, randomCol+1, area);
					//create the new area and place the entrance and exit
					Area cave = new Area(5, 10, AreaType.CAVE, new Position(randomCol+1, randomRow+1, area));
					Position exit = new Position(0, 0, cave);
					areaArray[randomRow+1][randomCol+1] = new CaveEntranceTile(entrance, exit);
					area.addCaveEntrance(entrance);
					cave.getArea()[0][0] = new CaveEntranceTile(exit, entrance);
					children.add(cave);
					placed = true;
				}
			}
		}

		//place the chests
		for(int count = 0; count < chests; count++){

			boolean placed = false;
			while(!placed){
				int randomRow = new Random().nextInt(areaArray.length-2)+1;
				int randomCol = new Random().nextInt(areaArray[0].length-2)+1;

				if(!invalidPosition[randomRow][randomCol]){
					areaArray[randomRow][randomCol] = new ChestTile();
					invalidPosition[randomRow][randomCol] = true;
					placed = true;

				}

			}
		}
	}
}
