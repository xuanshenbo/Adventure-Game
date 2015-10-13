/**
 * This is a wrapper class to hold the parameters for the random world
 * to be generated with.
 */

package model.logic;

import static utilities.PrintTool.p;

public class WorldParameters {

	private int buildings;
	private int width;
	private int height;
	private int lootValue;
	private int caves;
	private int chests;
	private int playerCount;
	private int trees;



	public WorldParameters(int height, int width, int playerCount, boolean test){
		this.height = height;
		this.width = width;
		this.playerCount = playerCount;
		buildings = 2;
		lootValue = 5;
		caves = 1;
		chests = 5;
		trees = 20;
		if(test){
			buildings = 0;
			lootValue = 0;
			caves = 0;
			trees = 1;
		}
	}

	public int getTrees() {
		return trees;
	}

	public void setTrees(int trees) {
		this.trees = trees;
	}

	public int getBuildings() {
		return buildings;
	}

	public void setBuildings(int buildings) {
		this.buildings = buildings;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLootValue() {
		return lootValue;
	}

	public void setLootValue(int lootValue) {
		this.lootValue = lootValue;
	}

	public int getCaves() {
		return caves;
	}

	public void setCaves(int caves) {
		this.caves = caves;
	}

	public int getChests() {
		return chests;
	}

	public void setChests(int chests) {
		this.chests = chests;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

}
