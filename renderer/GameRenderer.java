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
		this.tileWidth = width/size * imageScale;
//		this.tileHeight = height/size * imageScale;
		this.tileHeight = tileWidth/2;
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

		double halfTileWidth = tileWidth/2;
		double halfTileHeight = tileHeight/2;
		double startX = width/2, startY = -tileHeight/2;
		int groundOffsetY = images.ground().getWidth(null)/2;

		double screenX, screenY;

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
					screenX = startX + halfTileWidth * x;
					screenY = startY + halfTileHeight * x;
					graphic.drawImage(groundImage, (int)screenX, (int)screenY, null);
				}
			}
			startX -= halfTileWidth;
			startY += halfTileHeight;
		}

		startX = width/2;
		startY = 0;

		//draw view and objects
		for (int y = 0; y < viewHeight; y++) {
			for (int x = 0; x < viewWidth; x++) {
				screenX = startX + halfTileWidth * x;
				screenY = startY + halfTileHeight * x;
				drawTile(renderState.getMap()[y][x], screenX, screenY);
				drawTile(renderState.getNpc()[y][x], screenX, screenY);
				drawItem(objects[y][x], screenX, screenY);
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

		int imageX, imageY;
		int shadowWidth, shadowHeight;

		switch (tile){
			case '1':
				imageX = (int) x;
				imageY = (int) (y - images.avatar().get(0).getHeight(null));
				shadowWidth = images.avatar().get(0).getWidth(null);
				shadowHeight = images.avatar().get(0).getHeight(null)/2;
				graphic.drawImage(images.shadow(), imageX, imageY+shadowHeight*3/2, shadowWidth, shadowHeight, null);
				graphic.drawImage(images.avatar().get(0), imageX, imageY, null);
				break;
			case '2':
				imageX = (int) x;
				imageY = (int) (y - images.avatar().get(1).getHeight(null));
				shadowWidth = images.avatar().get(0).getWidth(null);
				shadowHeight = images.avatar().get(0).getHeight(null)/2;
				graphic.drawImage(images.shadow(), imageX, imageY+shadowHeight*3/2, shadowWidth, shadowHeight, null);
				graphic.drawImage(images.avatar().get(1), imageX, imageY, null);
				break;
			case '3':
				imageX = (int) x;
				imageY = (int) (y - images.avatar().get(2).getHeight(null));
				shadowWidth = images.avatar().get(0).getWidth(null);
				shadowHeight = images.avatar().get(0).getHeight(null)/2;
				graphic.drawImage(images.shadow(), imageX, imageY+shadowHeight*3/2, shadowWidth, shadowHeight, null);
				graphic.drawImage(images.avatar().get(2), imageX, imageY, null);
				break;
			case '4':
				imageX = (int) x;
				imageY = (int) (y - images.avatar().get(3).getHeight(null));
				shadowWidth = images.avatar().get(0).getWidth(null);
				shadowHeight = images.avatar().get(0).getHeight(null)/2;
				graphic.drawImage(images.shadow(), imageX, imageY+shadowHeight*3/2, shadowWidth, shadowHeight, null);
				graphic.drawImage(images.avatar().get(3), imageX, imageY, null);
				break;
			case 'O':
				imageX = (int) x;
				imageY = (int) (y - images.chest().getHeight(null));
				graphic.drawImage(images.chest(), imageX, imageY, null);
				break;
			case 'T':
				imageX = (int)(x-images.tree().getWidth(null)/2 + tileWidth/2);
				imageY = (int)(y-images.tree().getHeight(null));
				shadowWidth = images.tree().getWidth(null);
				shadowHeight = images.tree().getHeight(null)/2;
				graphic.drawImage(images.shadow(), imageX, imageY+shadowHeight*3/2, shadowWidth, shadowHeight, null);
				graphic.drawImage(images.tree(), imageX, imageY, null);
				break;
			case 'c':
				imageX = (int) x;
				imageY = (int) (y - (images.cave().getHeight(null)-tileHeight));
				graphic.drawImage(images.cave(), imageX, imageY, null);
				break;
			case 'b':
				imageX = (int) (x - tileWidth);
				imageY = (int) (y - images.building().getHeight(null) + tileHeight*2.5);
				graphic.drawImage(images.building(), imageX, imageY, null);
				break;
			case 'D':
				imageX = (int) x;
				imageY = (int) (y - images.door().getHeight(null));
				graphic.drawImage(images.door(), imageX, imageY, null);
				break;
			case 'Z':
				imageX = (int) x;
				imageY = (int) (y - images.key().getHeight(null));
				graphic.drawImage(images.zombie(), imageX, imageY, null);
				break;
			default:
				break;
		}
	}

	private void drawItem(char item, double x, double y) {

		if (item == '\u0000') {
			return;
		}

		int imageX, imageY;
		int shadowWidth, shadowHeight;

		switch (item) {
			case 'k':
				imageX = (int) x;
				imageY = (int) (y - images.key().getHeight(null));
				graphic.drawImage(images.key(), imageX, imageY, null);
				break;
			case 'c':
				imageX = (int) x;
				imageY = (int) (y - images.cupcake().getHeight(null));
				graphic.drawImage(images.cupcake(), imageX, imageY, null);
				break;
			case 'b':
				imageX = (int) x;
				imageY = (int) (y - images.bag().getHeight(null));
				graphic.drawImage(images.bag(), imageX, imageY, null);
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
			doRender = false;
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