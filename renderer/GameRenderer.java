package renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.PrintTool.p;

public class GameRenderer{

	//all different sizes needed
	private int size;
	private int offsetX, offsetY;
	private int width,height;
	private double tileHeight, tileWidth;
	private int viewWidth, viewHeight;

	//image size
	private int imageScale = 1;
	
	private char[][] objects;
	private char[][] view;
	private int animationIndex;
	private BufferedImage outPut;
	private Shape background;
	private Graphics2D graphic;

	private Images images;
	private char type;
	private RenderState renderState;
	private GameCanvas canvas;

	private boolean doRender = false;

	public GameRenderer(int width, int height, char[][] view, char[][] objects, GameCanvas canvas){

		size = view.length;
		this.outPut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.width = width;
		this.height = height;
		offsetX = 0;
		offsetY = 0;
		this.tileWidth = width/size;
		this.tileHeight = height/size;
		this.viewWidth = view[0].length;
		this.viewHeight = view.length;
		this.renderState = new RenderState(view);
		this.objects = objects;
		this.view = view;
		//this.players = players;
		this.images = new Images(tileWidth, tileHeight, imageScale);

		this.canvas = canvas;
		this.background = new Rectangle(width, height);

		this.animationIndex = 0;

		render();

	}

	public void render() {
		//draw the game board

		graphic = outPut.createGraphics();
		graphic.setColor(Color.BLACK);
		graphic.fill(background);

		double halfTileWidth = tileWidth/2 * imageScale;
		double halfTileHeight = tileHeight/2 *imageScale;
		double startX = width/2, startY = -tileWidth*imageScale/2;
		int groundOffsetY = images.ground().getWidth(null)/2;

		//draw ground

		Image groundImage;

		if (type == 'R'){
			groundImage = images.caveGround();
		} else {
			groundImage = images.ground();
		}

		for (int y = 0; y < viewHeight; y++) {
			for (int x = 0; x < viewWidth; x++) {
				if(renderState.getMap()[y][x] != '\u0000') {
					graphic.drawImage(groundImage,
							(int) (startX + halfTileWidth*x - groundOffsetY),
							(int) (startY + halfTileHeight*x + tileWidth*imageScale/2),
							null);
				}
			}
			startX -= halfTileWidth;
			startY += halfTileHeight;
		}

		startX = width/2;
		startY = -tileWidth*imageScale/2;

		//draw view and objects
		for (int y = 0; y < viewHeight; y++) {
			for (int x = 0; x < viewWidth; x++) {
				drawTile(renderState.getMap()[y][x], startX + halfTileWidth * x, startY + halfTileHeight * x);
				drawTile(renderState.getNpc()[y][x], startX + halfTileWidth * x, startY + halfTileHeight * x);
				drawTile(objects[y][x], startX + halfTileWidth*x, startY + halfTileHeight*x);

			}
			startX -= halfTileWidth;
			startY += halfTileHeight;
		}

		graphic.dispose();
		canvas.updateImage(outPut);

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

	public void update(char type, char[][] view, char[][] objects) {
		this.type = type;

		for (int y = 0; y < viewHeight; y++) {
			for (int x = 0; x < viewWidth; x++) {
				if (view[y][x] != this.view[y][x] || objects[y][x] != this.objects[y][x]) {
					doRender = true;
				}
			}
		}

		if (doRender) {
			this.view = view;
			this.objects = objects;

			this.renderState.initialize(view);


			setRenderingMap();
			render();
			doRender = false;
		}
	}

	private void setRenderingMap() {
		for (int y = 0; y < viewHeight; y++) {
			for (int x = 0; x < viewWidth; x++) {
				if (renderState.getMap()[y][x] == 'C'){
					if (x < 14) {
						renderState.getMap()[y][x + 1] = 'N';
					}
					if (y < 14) {
						renderState.getMap()[y + 1][x] = 'N';
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