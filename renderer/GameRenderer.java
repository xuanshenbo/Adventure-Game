package renderer;

import GUI.ImageLoader;
import state.Player;
import tiles.Tile;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameRenderer{

	private int size = 20;
//	private int width,height;
	private Tile[][] area;
	private double tileHeight;
	private double tileWidth;
	private ArrayList<AvatarImages> avatarImages;
	private Image grassImage;
	private ArrayList<Player> players;
	private int animationIndex;
	private BufferedImage outPut;
	private Graphics2D graphic;

	//temp
	private Image treeImage;

	public GameRenderer(int width, int height, Tile[][] area, ArrayList<Player> players){

		this.outPut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.graphic = outPut.createGraphics();
//		this.width = width;
//		this.height = height;
		this.tileWidth = width/size;
		this.tileHeight = height/size;
		this.area = area;
		this.players = players;
		this.avatarImages = new ArrayList<AvatarImages>();
		this.animationIndex = 0;

		loadImages();

	}

	private  void loadImages(){
		for (int i = 0; i < players.size(); i++){
			int avatarImageIndex = new Random().nextInt(3);
			this.avatarImages.add(new AvatarImages("avatar" + avatarImageIndex + ".png", tileWidth));
		}
		this.grassImage = loadImage("ground.png", 1);
		this.treeImage = loadImage("tree.png", 3);
	}

	private Image loadImage(String filename, int scale) {
		Image image = ImageLoader.loadImage(filename);

		BufferedImage img = new BufferedImage((int)tileWidth*scale, (int)tileWidth*scale, BufferedImage.TYPE_INT_ARGB);
		//draw the image to bufferedImage
		Graphics2D g = img.createGraphics();
		g.drawImage(image, 0, 0, (int)tileWidth*scale, (int)tileHeight*scale, null);
//		g.drawImage(image, 0, 0, (int) (tileWidth * scale), (int) (tileHeight * scale), 0, 0, width, height, null);
		g.dispose();
		return img;
	}

	public void render() {
		//draw the game board
//		for (int x = 0; x < area.length; x++) {
//			for (int y = 0; y < area[x].length; y++) {
//				g.drawImage(grassImage, (int) (x * tileWidth), (int) (y * tileHeight), null);
//			}
//		}
		for (int y = 0; y < area[0].length; y++) {
			for (int x = 0; x < area.length; x++) {
				drawTile(area[x][y], x, y);
			}
			for (int i = 0; i < players.size(); i++){
				if (players.get(i).getPosition().getY() == y) {
					graphic.drawImage(avatarImages.get(i).getImages()[0][(int) (animationIndex)],
							(int) (players.get(i).getPosition().getX() * tileWidth),
							(int) (players.get(i).getPosition().getY() * tileHeight - avatarImages.get(i).avatarHeight() + tileHeight),
							null);
				}
			}
		}

		//draw players
//		for (int i = 0; i < players.size(); i++){
//			g.drawImage(avatarImages.get(i).getImages()[0][(int)(animationIndex)],
//					(int)(players.get(i).getPosition().getX()*tileWidth),
//					(int)(players.get(i).getPosition().getY()*tileHeight - avatarImages.get(i).avatarHeight() + tileHeight),
//					null);
//		}
		graphic.dispose();
		updateAnimation();
	}

	private void updateAnimation() {
		animationIndex++;
		if (animationIndex > 3){
			animationIndex = 0;
		}
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void drawTile(Tile tile, int x, int y) {
		switch (tile.getType()){
			case 'T':
				graphic.drawImage(treeImage, (int)(x*tileWidth-tileWidth), (int)(y*tileHeight-3*tileHeight+tileHeight/2), null);
				break;
//			case BUILDING:
//				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
//				break;
//			case DOOR:
//				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
//				break;
//			case CAVE:
//				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
//				break;
//			case CAVEENTRANCE:
//				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
//				break;
//			case CHEST:
//				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
//				break;
//			case GRASS:
//				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
//				break;
//			case ROCK:
//				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
//				break;
//			case WOOD:
//				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
//				break;
			default:
				break;
		}
	}

	public Image getImage(){
		return outPut;
	}

}