package model.logic;

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
		if(test){
			buildings = 2;
			lootValue = 1;
			caves = 1;
			chests = 5;
			trees = 20;
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
