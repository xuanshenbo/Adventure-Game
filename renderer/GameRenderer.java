package renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.PrintTool.p;

public class GameRenderer{

	private int size;
	private int offsetX, offsetY;
	private int width,height;

	private int imageScale = 1;

	private char[][] view;
	private char[][] objects;
	private double tileHeight;
	private double tileWidth;
	//private ArrayList<Player> players;
	private int animationIndex;
	private BufferedImage outPut;
	private Graphics2D graphic;

	private Images images;
	private char type;

	public GameRenderer(int width, int height, char[][] view, char[][] objects){

		size = view.length;
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
		this.images = new Images(tileWidth, tileHeight, imageScale);

		this.animationIndex = 0;

		render();

	}

	public void render() {
		//draw the game board



		graphic = outPut.createGraphics();
		graphic.clearRect(0,0,outPut.getWidth(), outPut.getHeight());

		double halfTileWidth = tileWidth/2 * imageScale;
		double halfTileHeight = tileHeight/2 *imageScale;
		double startX = width/2, startY = -tileWidth*imageScale/2;

		//draw ground

		Image groundImage;
		char groundType = '\u0000';

		for (int y = 0; y < view.length; y++) {
			if (groundType == 'R'){
				break;
			}
			for (int x = 0; x < view[y].length; x++) {
				if (view[y][x] == 'R'){
					groundType = 'R';
					break;
				}
			}
		}

		if (groundType == 'R'){
			groundImage = images.caveGround();
		} else {
			groundImage = images.ground();
		}

		for (int y = 0; y < view.length; y++) {
			for (int x = 0; x < view[y].length; x++) {
				if(view[y][x] != '\u0000') {
					graphic.drawImage(groundImage, (int) (startX + halfTileWidth*x - images.ground().getWidth(null)/2), (int) (startY + halfTileHeight*x + tileWidth*imageScale/2), null);
				}
			}
			startX -= halfTileWidth;
			startY += halfTileHeight;
		}

		startX = width/2;
		startY = -tileWidth*imageScale/2;

		//draw view and objects
		for (int y = 0; y < view.length; y++) {
			for (int x = 0; x < view[y].length; x++) {
				drawTile(view[y][x], startX + halfTileWidth * x, startY + halfTileHeight * x);
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
				graphic.drawImage(images.tree(), (int)(x)-images.tree().getWidth(null)/2, (int)(y), null);
				break;
			case '1':
//				graphic.drawImage(images.avatar().get(0).getImages()[0][(int) (animationIndex)],
				graphic.drawImage(images.avatar().get(0),
						(int) (x)+offsetX-images.avatar().get(0).getWidth(null)/2,
						(int) (y+offsetY),
						null);
				p((y+offsetY));
				break;
			case '2':
//				graphic.drawImage(images.avatar().get(1).getImages()[0][(int) (animationIndex)],
				graphic.drawImage(images.avatar().get(1),
						(int) (x)-images.avatar().get(1).getWidth(null)/2,
						(int) (y),
						null);
				break;
			case '3':
//				graphic.drawImage(images.avatar().get(2).getImages()[0][(int) (animationIndex)],
				graphic.drawImage(images.avatar().get(2),
						(int) (x)-images.avatar().get(2).getWidth(null)/2,
						(int) (y),
						null);
				break;
			case '4':
//				graphic.drawImage(images.avatar().get(3).getImages()[0][(int) (animationIndex)],
				graphic.drawImage(images.avatar().get(3),
						(int) (x)-images.avatar().get(3).getWidth(null)/2,
						(int) (y),
						null);
				break;
			case 'O':
				graphic.drawImage(images.chest(), (int) (x)-images.chest().getWidth(null)/2, (int) (y), null);
				break;
			case 'C':
				graphic.drawImage(images.cave(), (int) (x)-images.cave().getWidth(null)/2, (int) (y), null);
				break;
			case 'B':
				graphic.drawImage(images.building(), (int) (x)-images.building().getWidth(null)/2, (int) (y), null);
				break;
			case 'D':
				graphic.drawImage(images.door(), (int) (x)-images.door().getWidth(null)/2, (int) (y), null);
				break;
			case 'k':
				graphic.drawImage(images.key(), (int) (x)-images.key().getWidth(null)/2, (int) (y), null);
				break;
			case 'Z':
				graphic.drawImage(images.key(), (int) (x)-images.key().getWidth(null)/2, (int) (y), null);
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

	public void update(char type, char[][] view, char[][] objects) {
		this.type = type;
		this.view = view;
		this.objects = objects;
		//this.players = players;

		setRenderingMap();

		render();
	}

	private void setRenderingMap() {
		for (int y = 0; y < view.length; y++) {
			for (int x = 0; x < view[y].length; x++) {
				if (view[y][x] == 'C'){
					if (x < 14) {
						view[y][x + 1] = 'N';
					}
					if (y < 14) {
						view[y + 1][x] = 'N';
					}
				}
			}
		}
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