package renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.PrintTool.p;

public class GameRenderer{

	private int size = 15;
	private int offsetX, offsetY;
	private int width,height;
	private char[][] view;
	private char[][] objects;
	private double tileHeight;
	private double tileWidth;
	//private ArrayList<Player> players;
	private int animationIndex;
	private BufferedImage outPut;
	private Graphics2D graphic;

	private Images images;

	public GameRenderer(int width, int height, char[][] view, char[][] objects){

		this.outPut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.width = width;
		this.height = height;
		offsetX = 0;
		offsetY = 0;
		this.tileWidth = width/size;
		this.tileHeight = height/size;
		this.view = view;
		this.objects = objects;
		//this.players = players;
		this.images = new Images(tileWidth, tileHeight);

		this.animationIndex = 0;

		render();

	}

	public void render() {
		//draw the game board



		graphic = outPut.createGraphics();
		graphic.clearRect(0,0,outPut.getWidth(), outPut.getHeight());

//		for (int y = 0; y < view.length; y++) {
//			for (int x = 0; x < view[y].length; x++) {
//				if(view[y][x] != '\u0000') {
//					graphic.drawImage(images.ground(), (int) (x * tileWidth), (int) (y * tileHeight), null);
//				}
//			}
//		}
//
//
//		for (int y = 0; y < view[0].length; y++) {
//			for (int x = 0; x < view.length; x++) {
//				drawTile(view[y][x], x, y);
//				drawTile(objects[y][x], x, y);
//			}
////			for (int i = 0; i < players.size(); i++){
////				if (players.get(i).getPosition().getY() == y) {
////					graphic.drawImage(avatarImages.get(i).getImages()[0][(int) (animationIndex)],
////							(int) (players.get(i).getPosition().getX() * tileWidth),
////							(int) (players.get(i).getPosition().getY() * tileHeight - avatarImages.get(i).avatarHeight() + tileHeight),
////							null);
////				}
////			}
//		}

		double halfTileWidth = tileWidth;
		double halfTileHeight = tileHeight;
		double startX = width/2, startY = -height/2;


		for (int y = 0; y < view.length; y++) {
			for (int x = 0; x < view[y].length; x++) {
				if(view[y][x] != '\u0000') {
					graphic.drawImage(images.ground(), (int) (startX + halfTileWidth*x), (int) (startY + halfTileHeight*x), null);
				}
			}
			startX -= halfTileWidth;
			startY += halfTileHeight;
		}

		startX = width/2;
		startY = -height/2;

		for (int y = 0; y < view.length; y++) {
			for (int x = 0; x < view[y].length; x++) {
				drawTile(view[y][x], startX + halfTileWidth*x, startY + halfTileHeight*x);
				drawTile(objects[y][x], startX + halfTileWidth*x, startY + halfTileHeight*x);
			}
			startX -= halfTileWidth;
			startY += halfTileHeight;
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

	private void drawTile(char tile, double x, double y) {

		if (tile == '\u0000'){
			return;
		}
		
		switch (tile){

			case 'T':
				graphic.drawImage(images.tree(), (int)(x), (int)(y), null);
				break;
			case '1':
				graphic.drawImage(images.avatar().get(0).getImages()[0][(int) (animationIndex)],
						(int) (x)+offsetX,
						(int) (y)+offsetY,
						null);
				break;
			case '2':
				graphic.drawImage(images.avatar().get(1).getImages()[0][(int) (animationIndex)],
						(int) (x),
						(int) (y),
						null);
				break;
			case '3':
				graphic.drawImage(images.avatar().get(2).getImages()[0][(int) (animationIndex)],
						(int) (x),
						(int) (y),
						null);
				break;
			case '4':
				graphic.drawImage(images.avatar().get(3).getImages()[0][(int) (animationIndex)],
						(int) (x),
						(int) (y),
						null);
				break;
			case 'O':
				graphic.drawImage(images.chest(), (int) (x), (int) (y), null);
				break;
			case 'B':
				graphic.drawImage(images.building(), (int) (x), (int) (y), null);
				break;
			case 'D':
				graphic.drawImage(images.door(), (int) (x), (int) (y), null);
				break;
			case 'k':
				graphic.drawImage(images.key(), (int) (x), (int) (y), null);
				break;
			case 'Z':
				graphic.drawImage(images.key(), (int) (x), (int) (y), null);
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

	public void update(char[][] view, char[][] objects) {
		this.view = view;
		this.objects = objects;
		//this.players = players;

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