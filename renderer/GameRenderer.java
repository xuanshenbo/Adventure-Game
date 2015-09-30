package renderer;

import GUI.ImageLoader;
import state.Player;
import tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import static utilities.PrintTool.p;

public class GameRenderer{

	private int size = 15;
	private int offsetX, offsetY;
//	private int width,height;
	private char[][] map;
	private char[][] items;
	private double tileHeight;
	private double tileWidth;
	private ArrayList<Player> players;
	private int animationIndex;
	private BufferedImage outPut;
	private Graphics2D graphic;

	private Images images;

	public GameRenderer(int width, int height, char[][] view, char[][] objects, ArrayList<Player> players){

		this.outPut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		this.width = width;
//		this.height = height;
		offsetX = 0;
		offsetY = 0;
		this.tileWidth = width/size;
		this.tileHeight = height/size;
		this.map = view;
		this.items = objects;
		this.players = players;
		this.images = new Images(tileWidth, tileHeight, players);

		this.animationIndex = 0;

		render();

	}

	public void render() {
		//draw the game board
		graphic = outPut.createGraphics();
		graphic.clearRect(0,0,outPut.getWidth(), outPut.getHeight());

		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if(map[x][y] != null) {
					graphic.drawImage(images.getGroundImage(), (int) (x * tileWidth), (int) (y * tileHeight), null);
				}
			}
		}
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				drawTile(map[x][y], x, y);
			}
//			for (int i = 0; i < players.size(); i++){
//				if (players.get(i).getPosition().getY() == y) {
//					graphic.drawImage(avatarImages.get(i).getImages()[0][(int) (animationIndex)],
//							(int) (players.get(i).getPosition().getX() * tileWidth),
//							(int) (players.get(i).getPosition().getY() * tileHeight - avatarImages.get(i).avatarHeight() + tileHeight),
//							null);
//				}
//			}
		}
		graphic.dispose();
		updateAnimation();

	}

	private void updateAnimation() {
		animationIndex++;
		if (animationIndex > 3){
			animationIndex = 0;
		}
	}

	private void drawTile(String tile, int x, int y) {

		if (tile == null){
			return;
		}
		switch (tile){

			case "T":
				graphic.drawImage(images.getTreeImage(), (int)(x*tileWidth-tileWidth), (int)(y*tileHeight-3*tileHeight+tileHeight/2), null);
				break;
			case "1":
				graphic.drawImage(images.getAvatarImages().get(0).getImages()[0][(int) (animationIndex)],
						(int) (x * tileWidth)+offsetX,
						(int) (y * tileHeight - images.getAvatarImages().get(0).avatarHeight() + tileHeight)+offsetY,
						null);
				break;
			case "2":
				graphic.drawImage(images.getAvatarImages().get(1).getImages()[0][(int) (animationIndex)],
						(int) (x * tileWidth),
						(int) (y * tileHeight - images.getAvatarImages().get(1).avatarHeight() + tileHeight),
						null);
				break;
			case "3":
				graphic.drawImage(images.getAvatarImages().get(2).getImages()[0][(int) (animationIndex)],
						(int) (x * tileWidth),
						(int) (y * tileHeight - images.getAvatarImages().get(2).avatarHeight() + tileHeight),
						null);
				break;
			case "4":
				graphic.drawImage(images.getAvatarImages().get(3).getImages()[0][(int) (animationIndex)],
						(int) (x * tileWidth),
						(int) (y * tileHeight - images.getAvatarImages().get(3).avatarHeight() + tileHeight),
						null);
				break;
			case "O":
				graphic.drawImage(images.getChestImage(), (int) (x * tileWidth), (int) (y * tileHeight), null);
				break;
			case "B":
				graphic.drawImage(images.getBuildingImage(), (int) (x * tileWidth), (int) (y * tileHeight), null);
				break;
			case "D":
				graphic.drawImage(images.getDoorImage(), (int) (x * tileWidth), (int) (y * tileHeight), null);
				break;
			default:
				break;
		}
	}

	public Image getImage(){
		return outPut;
	}

//	public Image getOneImage(){
//		return loadImage("tree.png", 3);
//	}

	public void update(String[][] map, String[][] items, ArrayList<Player> players) {
		this.map = map;
		this.items = items;
		this.players = players;
		render();
	}

//	public int getOffsetX(){
//		return offsetX;
//	}
//
//	public int getOffsetY(){
//		return offsetY;
//	}
//
//	public boolean setOffsetX(int offsetX){
//		this.offsetX += offsetX;
//		if (offsetX > tileWidth/2){
//			offsetX = -(int)(tileWidth/2);
//			return true;
//		}
//		if (offsetX < -tileWidth/2){
//			offsetX = (int)(tileWidth/2);
//			return true;
//		}
//		return false;
//	}
//
//	public boolean setOffsetY(int offsetY){
//		this.offsetY += offsetY;
//		if (offsetY > tileHeight/2){
//			offsetY = -(int)(tileHeight/2);
//			return true;
//		}
//		if (offsetY < -tileHeight/2){
//			offsetY = (int)(tileHeight/2);
//			return true;
//		}
//		return false;
//	}
}