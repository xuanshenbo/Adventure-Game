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
	private int viewDir = 0;

	//image size
	private int imageScale = 3;
	
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

	private int doorIndex = 0;
	private int times = 0;

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
		this.images = new Images(tileWidth, tileHeight, 1);

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

		double imageY = (width/2)/2*imageScale;
		double screenOffsetY = (height/2 - imageY);

		double startX = width/2;
		double startY = -tileHeight/2 + screenOffsetY;
		int groundOffsetY = images.ground().getWidth(null)/2;

		double screenX, screenY;

		//draw ground

		Image groundImage;

		if (type == 'R'){
			groundImage = images.caveGround();
		} else {
			groundImage = images.ground();
		}

		//draw from the normal view direction
		if (viewDir == 0) {

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
			startY = screenOffsetY;

			//draw view and objects
			for (int y = 0; y < viewHeight; y++) {
				for (int x = 0; x < viewWidth; x++) {
					screenX = startX + halfTileWidth * x;
					screenY = startY + halfTileHeight * x;
					//draw map elements such as chests
					drawTile(renderState.getMap()[y][x], screenX, screenY);
					//in the building tile, draw building and redraw the players and zombies in the right place
					if (renderState.getBuilding()[y][x] == 'b'){
						drawTile(renderState.getBuilding()[y][x], screenX, screenY);
						if (x > 2) {
							drawTile(renderState.getBuilding()[y][x - 2], screenX - tileWidth, screenY - tileHeight);
						}
						if (x > 0 && x < 29
								&& y > 3 && y < 31){
							for (int i = 4; i >= 0; i--){
								screenX = (startX + halfTileWidth*i) + halfTileWidth * (x+1);
								screenY = (startY - halfTileHeight*i) + halfTileHeight * (x+1);
								drawItem(objects[y][x], screenX, screenY);
								drawTile(renderState.getNpc()[y-i][x+1], screenX, screenY);
								screenX = (startX + halfTileWidth*i) + halfTileWidth * (x+2);
								screenY = (startY - halfTileHeight*i) + halfTileHeight * (x+2);
								drawItem(objects[y][x], screenX, screenY);
								drawTile(renderState.getNpc()[y-i][x+2], screenX, screenY);
							}
						}
					}
					//in the cave tile, draw cave
					else if (renderState.getBuilding()[y][x] == 'c'){
						drawTile(renderState.getBuilding()[y][x], screenX, screenY);
					}
					//in all other tiles, draw objects and npcs
					else {
						drawItem(objects[y][x], screenX, screenY);
						drawTile(renderState.getNpc()[y][x], screenX, screenY);
					}
				}
				startX -= halfTileWidth;
				startY += halfTileHeight;
			}

		}

		//draw from the back view direction
		else if (viewDir == 1){

			for (int y = viewHeight -1; y >= 0; y--) {
				for (int x = viewWidth -1; x >= 0; x--) {
					if(renderState.getMap()[y][x] != '\u0000') {
						screenX = startX + halfTileWidth * (viewWidth-1-x);
						screenY = startY + halfTileHeight * (viewWidth-1-x);
						graphic.drawImage(groundImage, (int)screenX, (int)screenY, null);
					}
				}
				startX -= halfTileWidth;
				startY += halfTileHeight;
			}

			startX = width/2;
			startY = screenOffsetY;

			for (int y = viewHeight -1; y >= 0; y--) {
				for (int x = viewWidth -1; x >= 0; x--) {
					screenX = startX + halfTileWidth * (viewWidth-1-x);
					screenY = startY + halfTileHeight * (viewWidth-1-x);
					//draw map elements such as chests
					drawTile(renderState.getMap()[y][x], screenX, screenY);
					//in the building tile, draw building and redraw the players and zombies in the right place
					if (renderState.getBuilding()[y][x] == 'b'){
						screenX += tileWidth*0.5;
						screenY += tileHeight*3.5;
						drawTile(renderState.getBuilding()[y][x], screenX, screenY);
					}
					//in the cave tile, draw cave
					else if (renderState.getBuilding()[y][x] == 'c'){
						drawTile(renderState.getBuilding()[y][x], screenX, screenY);
					}
					//in all other tiles, draw objects and npcs
					else {
						drawItem(objects[y][x], screenX, screenY);
						drawTile(renderState.getNpc()[y][x], screenX, screenY);
					}
				}
				startX -= halfTileWidth;
				startY += halfTileHeight;
			}

		}

		//door animation control
		times +=1;
		if (times > 2) {
			times = 0;
			doorIndex += 1;
			if (doorIndex > 10) {
				doorIndex = 0;
			}
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
				imageY = (int) (y - images.chest(viewDir).getHeight(null)*3/4);
				graphic.drawImage(images.chest(viewDir), imageX, imageY, null);
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
				imageX = (int) (x - tileWidth);
				imageY = (int) (y - images.cave(viewDir).getHeight(null));
				graphic.drawImage(images.cave(viewDir), imageX, imageY, null);
				break;
			case 'b':
				imageX = (int) (x - tileWidth*2);
				imageY = (int) (y - images.building(viewDir).getHeight(null) + tileHeight/2);
				graphic.drawImage(images.building(viewDir), imageX, imageY, null);
				break;
			case 'D':
				imageX = (int) x;
				imageY = (int) (y - images.door(doorIndex).getHeight(null) + tileHeight/2);
				graphic.drawImage(images.door(doorIndex), imageX, imageY, null);
				break;
			case 'E':
				imageX = (int) x;
				imageY = (int) (y - images.door(doorIndex).getHeight(null) + tileHeight/2);
				graphic.drawImage(images.door(doorIndex), imageX, imageY, null);
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
				imageY = (int) (y - images.key().getHeight(null)+tileHeight/2);
				graphic.drawImage(images.key(), imageX, imageY, null);
				break;
			case 'c':
				imageX = (int) x;
				imageY = (int) (y - images.cupcake(0).getHeight(null) + tileHeight/2);
				graphic.drawImage(images.cupcake(viewDir), imageX, imageY, null);
				break;
			case 'b':
				imageX = (int) x;
				imageY = (int) (y - images.bag().getHeight(null) + tileHeight/2);
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

	public void rotate(){
		viewDir+=1;
		if (viewDir>1)
			viewDir = 0;
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