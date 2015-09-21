package renderer;

import GUI.ImageLoader;
import state.Player;
import state.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameRenderer extends JPanel{

	private int size = 20;
	private int width,height;
	private Tile[][] area;
	private double tileHeight;
	private double tileWidth;
	private ArrayList<AvatarImages> avatarImages;
	private Image grassImage;
	private ArrayList<Player> players;

	public GameRenderer(int width, int height, Tile[][] area, ArrayList<Player> players){

		this.width = width;
		this.height = height;
		this.tileWidth = width/size;
		this.tileHeight = height/size;
		System.out.println("tileWidth: " + tileWidth + " tileHeight: " + tileHeight);
		this.area = area;
		this.players = players;
		this.avatarImages = new ArrayList<AvatarImages>();

		for (int i = 0; i < players.size(); i++){
			int avatarImageIndex = new Random().nextInt(3);
			this.avatarImages.add(new AvatarImages("avatar" + avatarImageIndex + ".png", tileWidth));
		}
		this.grassImage = loadImage("ground.png");

//		setWidthHeight(width, height);
		Dimension size = new Dimension(width, height);

		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setVisible(true);
	}

	private Image loadImage(String filename) {
		Image image = ImageLoader.loadImage(filename);

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		//draw the image to bufferedImage
		Graphics2D g = img.createGraphics();
		g.drawImage(image, 0, 0, (int)tileWidth, (int)tileHeight, null);
		g.dispose();
		return img;

	}

//	public void setWidthHeight(int w, int h){
////		if(w != width || h != height){
//		width = w;
//		height = h;
////			return true;
////		}
////		return false;
//	}

	public void paintComponent(Graphics g) {
		//clear this panel
		this.removeAll();

		render(g);
	}

	public void render(Graphics g) {
		//draw the game board

		for (int x = 0; x < area.length; x++) {
			for (int y = 0; y < area[x].length; y++) {
				drawTile(area[x][y], x, y, g);
			}
		}

		//draw players
		for (int i = 0; i < players.size(); i++){
			g.drawImage(avatarImages.get(i).getImages()[0][0],
					(int)(players.get(i).getPosition().getX()*tileWidth),
					(int)(players.get(i).getPosition().getY()*tileHeight - avatarImages.get(i).avatarHeight() + tileHeight),
					null);
		}
	}

	private void drawTile(Tile tile, int x, int y, Graphics g) {
		switch (tile.getType()){
			case TREE:
				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
				break;
			case BUILDING:
				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
				break;
			case DOOR:
				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
				break;
			case CAVE:
				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
				break;
			case CAVEENTRANCE:
				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
				break;
			case CHEST:
				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
				break;
			case GRASS:
				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
				break;
			case ROCK:
				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
				break;
			case WOOD:
				g.drawImage(grassImage, (int)(x*tileWidth), (int)(y*tileHeight), null);
				break;
			default:
				break;
		}
	}

}