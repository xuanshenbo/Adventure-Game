package renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.PrintTool.p;

/**
 * This is the Game Renderer, parsing all the map information
 * and render the out put image for the main canvas to draw.
 *
 * Created by lucas on 7/10/15
 * @author Mingmin Ying 300266387
 */
public class GameRenderer{
	//size of the screen
	private int width,height;
	//size of each tile
	private double tileHeight, tileWidth;
	//size of 2d array of the showing map
	private int viewWidth, viewHeight;
	//direction, 0 for normal view and 1 for back view
	private int viewDir = 0;
	//image size
	private int imageScale = 3;
	//items in the map
	private char[][] objects;
	//map elements in the map
	private char[][] view;
	//out put image
	private BufferedImage outPut;
	//background triangle
	private Shape background;
	//the graphic to render the out put image
	private Graphics2D graphic;

	//all the images needed for rendering
	private Images images;
	//ground type for render the ground with the right tile texture
	private char type;
	//a 2d array parser for parsing the received 2d array
	private RenderState renderState;
	//the canvas which implement JPanel to show the main screen
	private GameCanvas canvas;

	private boolean doRender = false;

	private int animationIndex;
	private int doorIndex = 0;
	private int playerAnimationIndex = 1;
	private char playerDir = 's';
	private int times = 0;

	private boolean night;

	private boolean doAnimation = false;
	private int animationBound = 7;

	/**
	 * create a game renderer. pass the size of the game, two 2d arrays of map elements into
	 * this for rendering usage. Initialize the renderer
	 * @param width width of the game screen
	 * @param height height of the game screen
	 * @param view basic map elements such as buildings, caves, trees, etc.
	 * @param objects items in the game
	 * @param canvas game canvas which show the rendered image
	 */
	public GameRenderer(int width, int height, char[][] view, char[][] objects, GameCanvas canvas){

		this.outPut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.width = width;
		this.height = height;
		this.viewWidth = view[0].length;
		this.viewHeight = view.length;
		this.tileWidth = width/viewWidth * imageScale;
		this.tileHeight = tileWidth/2;
		this.renderState = new RenderState(view);
		this.objects = objects;
		this.view = view;
		this.images = new Images(tileWidth, tileHeight, 1, width, height);
		this.canvas = canvas;
		this.background = new Rectangle(width, height);
		this.animationIndex = 0;

		render();

	}

	/**
	 * render the output screen, using the paint algorithm to render all elements
	 * in the right place, implements day and night view, the out put is determined
	 * by the view direction.
	 */
	public void render() {
		//initialize the graphic in the out put buffered image, fill with black back ground
		graphic = outPut.createGraphics();
		graphic.setColor(Color.BLACK);
		graphic.fill(background);

		//set all the parameters for placing all elements in the right place
		double halfTileWidth = tileWidth/2;
		double halfTileHeight = tileHeight/2;
		double imageY = (width/2)/2*imageScale;
		double screenOffsetY = (height/2 - imageY);
		double startX = width/2;
		double startY = -tileHeight/2 + screenOffsetY;
		double screenX, screenY;

		//set the ground image match the ground type
		Image groundImage;
		if (type == 'R'){
			groundImage = images.caveGround();
		} else if (type == 'W'){
			groundImage = images.buildingGround();
		} else {
			groundImage = images.ground();
		}

		//draw from the normal view direction
		if (viewDir == 0) {
			//draw background tiles
			for (int y = 0; y < viewHeight; y++) {
				for (int x = 0; x < viewWidth; x++) {
					//draw the ground tile in the map
					if(renderState.getMap()[y][x] != '\u0000') {
						screenX = startX + halfTileWidth * x;
						screenY = startY + halfTileHeight * x;
						graphic.drawImage(groundImage, (int)screenX, (int)screenY, null);
					}
				}
				startX -= halfTileWidth;
				startY += halfTileHeight;
			}

			//reset startX and startY for rendering the map elements
			startX = width/2;
			startY = screenOffsetY;

			//draw view and objects
			for (int y = 0; y < viewHeight; y++) {
				for (int x = 0; x < viewWidth; x++) {
					screenX = startX + halfTileWidth * x;
					screenY = startY + halfTileHeight * x;
					//draw map elements such as chests and trees
					drawTile(renderState.getMap()[y][x], screenX, screenY);

					// in the building tile, draw building and redraw all
					// the other elements in the right place
					if (renderState.getBuilding()[y][x] == 'b'){
						drawTile(renderState.getBuilding()[y][x], screenX, screenY);
						//draw the door if the door is in the map
						if (x > 1) {
							drawTile(renderState.getMap()[y][x - 2], screenX - tileWidth, screenY - tileHeight);
						}
						// after drawing a building, redraw objects and charactors to
						// keep all elements in the right place
						if (x > 0 && x < 29
								&& y > 3 && y < 31){
							//redraw objects and chatactors
							for (int i = 4; i >= 0; i--){
								for (int j = 1; j < 3; j++) {
									screenX = (startX + halfTileWidth * i) + halfTileWidth * (x + j);
									screenY = (startY - halfTileHeight * i) + halfTileHeight * (x + j);
									drawItem(objects[y - i][x + j], screenX, screenY);
									drawTile(renderState.getNpc()[y - i][x + j], screenX, screenY);
								}
							}
							//redraw trees and chests
							if (x < 27){
								for (int i = 4; i >= 0; i--){
									for (int j = 1; j < 5; j++) {
										screenX = (startX + halfTileWidth * i) + halfTileWidth * (x + j);
										screenY = (startY - halfTileHeight * i) + halfTileHeight * (x + j);
										drawTile(renderState.getMap()[y - i][x + j], screenX, screenY);
									}
								}
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

			//draw background tiles
			for (int y = viewHeight -1; y >= 0; y--) {
				for (int x = viewWidth -1; x >= 0; x--) {
					//draw the ground tile in the map
					if(renderState.getMap()[y][x] != '\u0000') {
						screenX = startX + halfTileWidth * (viewWidth-1-x);
						screenY = startY + halfTileHeight * (viewWidth-1-x);
						graphic.drawImage(groundImage, (int)screenX, (int)screenY, null);
					}
				}
				startX -= halfTileWidth;
				startY += halfTileHeight;
			}

			//reset startX and startY for rendering the map elements
			startX = width/2;
			startY = screenOffsetY;

			//draw view and objects
			for (int y = viewHeight -1; y >= 0; y--) {
				for (int x = viewWidth -1; x >= 0; x--) {
					screenX = startX + halfTileWidth * (viewWidth-1-x);
					screenY = startY + halfTileHeight * (viewWidth-1-x);

					//draw map elements such as chests
					drawTile(renderState.getMap()[y][x], screenX, screenY);

					//in the building tile, draw building
					if (renderState.getBuilding()[y][x] == 'b'){
						screenX += tileWidth*0.5;
						screenY += tileHeight*3.5;
						drawTile(renderState.getBuilding()[y][x], screenX, screenY);
					}

					//in the cave tile, draw cave
					else if (renderState.getBuilding()[y][x] == 'c'){
						screenX += tileWidth*0.8;
						screenY += tileHeight*1.6;
						drawTile(renderState.getBuilding()[y][x], screenX, screenY);
					}

					//in all other tiles, draw objects and npcs,
					else {
						drawItem(objects[y][x], screenX, screenY);
						if (renderState.getNpc()[y][x] != '\u0000'
								&& renderState.getNpc()[y][x] != 'N') {
							drawTile(renderState.getNpc()[y][x], screenX, screenY);
							// if a building is under any charactors,
							// redraw the building to the right place
							if (x > 4 && x < 31
									&& y > 0 && y < 28) {
								for (int i = 0; i < 4; i++) {
									for (int j = 1; j < 4; j++) {
										if (renderState.getBuilding()[y + i][x - j] == 'b') {
											screenX = startX + halfTileWidth * i + halfTileWidth
													* (viewWidth - 1 - x + j) + tileWidth*0.5;
											screenY = startY - halfTileHeight * i + halfTileHeight
													* (viewWidth - 1 - x + j) + tileHeight*3.5;
											drawTile(renderState.getBuilding()[y + i][x - j], screenX, screenY);
										}
									}
								}
							}
						}
					}
				}
				startX -= halfTileWidth;
				startY += halfTileHeight;
			}

		}

		if (night){
			graphic.drawImage(images.getNightImage(), 0, 0, null);
		}

		//door animation control
		updateAnimation();

		graphic.dispose();
		canvas.updateImage(outPut);

	}

	/*
	 * recursively do the loop to go through
	 * image sequences to show the animation.
	 */
	private void updateAnimation() {
		times +=1;
		if (times > 2) {
			times = 0;
			doorIndex += 1;
			if (doorIndex > 10) {
				doorIndex = 0;
			}
		}
		animationIndex+=1;
		if (animationIndex > 3){
			animationIndex = 0;
			playerAnimationIndex+=1;
			if (playerAnimationIndex > animationBound){
				playerAnimationIndex = animationBound - 6;
			}
		}

		if (!doAnimation){
			playerAnimationIndex = animationBound-7;
		}
	}

	/*
	 * passing the tile and x & y coordinates, choose the right
	 * image to draw into the output screen with the right position .
	 * @param tile a char indicate the element to draw
	 * @param x x coordinate in the screen
	 * @param y y coordinate in the screen
	 */
	private void drawTile(char tile, double x, double y) {

		//if nothing to draw, return
		if (tile == '\u0000'){
			return;
		}

		int imageX, imageY;
		int shadowWidth, shadowHeight;

		switch (tile){
			case '1':
			//draw player 1
				imageX = (int) x;
				imageY = (int) (y - images.avatar(0, playerAnimationIndex).getHeight(null));
				shadowWidth = images.avatar(0, playerAnimationIndex).getWidth(null);
				shadowHeight = images.avatar(0, playerAnimationIndex).getHeight(null)/2;
				graphic.drawImage(images.shadow(), imageX, imageY+shadowHeight*3/2, shadowWidth, shadowHeight, null);
				graphic.drawImage(images.avatar(0, playerAnimationIndex), imageX, imageY, null);
				break;
			case '2':
			//draw player 2
				imageX = (int) x;
				imageY = (int) (y - images.avatar(1, playerAnimationIndex).getHeight(null));
				shadowWidth = images.avatar(1, playerAnimationIndex).getWidth(null);
				shadowHeight = images.avatar(1, playerAnimationIndex).getHeight(null)/2;
				graphic.drawImage(images.shadow(), imageX, imageY+shadowHeight*3/2, shadowWidth, shadowHeight, null);
				graphic.drawImage(images.avatar(1, playerAnimationIndex), imageX, imageY, null);
				break;
			case '3':
			//draw player 3
				imageX = (int) x;
				imageY = (int) (y - images.avatar(2, playerAnimationIndex).getHeight(null));
				shadowWidth = images.avatar(2, playerAnimationIndex).getWidth(null);
				shadowHeight = images.avatar(2, playerAnimationIndex).getHeight(null)/2;
				graphic.drawImage(images.shadow(), imageX, imageY+shadowHeight*3/2, shadowWidth, shadowHeight, null);
				graphic.drawImage(images.avatar(2, playerAnimationIndex), imageX, imageY, null);
				break;
			case '4':
			//draw player 4
				imageX = (int) x;
				imageY = (int) (y - images.avatar(3, playerAnimationIndex).getHeight(null));
				shadowWidth = images.avatar(3, playerAnimationIndex).getWidth(null);
				shadowHeight = images.avatar(3, playerAnimationIndex).getHeight(null)/2;
				graphic.drawImage(images.shadow(), imageX, imageY+shadowHeight*3/2, shadowWidth, shadowHeight, null);
				graphic.drawImage(images.avatar(3, playerAnimationIndex), imageX, imageY, null);
				break;
			case 'O':
			//draw a chest
				imageX = (int) x;
				imageY = (int) (y - images.chest(viewDir).getHeight(null)*3/4);
				graphic.drawImage(images.chest(viewDir), imageX, imageY, null);
				break;
			case 'T':
			//draw a tree
				imageX = (int)(x-images.tree().getWidth(null)/2 + tileWidth/2);
				imageY = (int)(y-images.tree().getHeight(null));
				shadowWidth = images.tree().getWidth(null);
				shadowHeight = images.tree().getHeight(null)/2;
				graphic.drawImage(images.shadow(), imageX, imageY+shadowHeight*3/2, shadowWidth, shadowHeight, null);
				graphic.drawImage(images.tree(), imageX, imageY, null);
				break;
			case 'c':
			//draw a cave
				imageX = (int) (x - tileWidth);
				imageY = (int) (y - images.cave(viewDir).getHeight(null));
				graphic.drawImage(images.cave(viewDir), imageX, imageY, null);
				break;
			case 'b':
			//draw a building
				imageX = (int) (x - tileWidth*2);
				imageY = (int) (y - images.building(viewDir).getHeight(null) + tileHeight/2);
				graphic.drawImage(images.building(viewDir), imageX, imageY, null);
				break;
			case 'D':
			//draw a door
				imageX = (int) x;
				imageY = (int) (y - images.door(doorIndex).getHeight(null) + tileHeight/2);
				graphic.drawImage(images.door(doorIndex), imageX, imageY, null);
				break;
			case 'E':
			//draw a entrance
				imageX = (int) x;
				imageY = (int) (y - images.door(doorIndex).getHeight(null) + tileHeight/2);
				graphic.drawImage(images.door(doorIndex), imageX, imageY, null);
				break;
			case 'Z':
			//draw a zombie!!!zombie!!!zombie!!!
				imageX = (int) x;
				imageY = (int) (y - images.zombie().getHeight(null));
				graphic.drawImage(images.zombie(), imageX, imageY, null);
				break;
			default:
				break;
		}
	}

	/*
	 * passing the tile and x & y coordinates, choose the right
	 * image to draw into the output screen with the right position .
	 * @param item a char indicate the element to draw
	 * @param x x coordinate in the screen
	 * @param y y coordinate in the screen
	 */
	private void drawItem(char item, double x, double y) {

		//if nothing to draw, return
		if (item == '\u0000') {
			return;
		}

		int imageX, imageY;

		switch (item) {
			case 'k':
			//draw a key
				imageX = (int) x;
				imageY = (int) (y - images.key(viewDir).getHeight(null)+tileHeight/2);
				graphic.drawImage(images.key(viewDir), imageX, imageY, null);
				break;
			case 'c':
			//draw a cupcake
				imageX = (int) x;
				imageY = (int) (y - images.cupcake(0).getHeight(null) + tileHeight/2);
				graphic.drawImage(images.cupcake(viewDir), imageX, imageY, null);
				break;
			case 'b':
			//draw a bag
				imageX = (int) x;
				imageY = (int) (y - images.bag().getHeight(null) + tileHeight/2);
				graphic.drawImage(images.bag(), imageX, imageY, null);
				break;
			default:
				break;
		}
	}

	/**
	 * get the output image
	 * @return a buffered image of output screen
	 */
	public Image getImage(){
		return outPut;
	}

	/**
	 * update the map elements for rendering
	 * @param type ground tile type
	 * @param view basic map elements such as buildings, caves, trees, etc.
	 * @param objects items in the game
	 */
	public void update(char type, char[][] view, char[][] objects) {
		this.type = type;

		//check whether something changes in the map, if so,
		//update the map elements
		for (int y = 0; y < viewHeight; y++) {
			for (int x = 0; x < viewWidth; x++) {
				if (view[y][x] != this.view[y][x] || objects[y][x] != this.objects[y][x]) {
					doRender = true;
				}
			}
		}

		//handle a message for the gui and skip that message
		if ((view[23][9] != '\u0000' && view[23][10] == '\u0000')
				|| (view[22][16] != '\u0000' && view[23][16] == '\u0000')){
			doRender = false;
		}

		//update map elements
		if (doRender) {
			this.view = view;
			this.objects = objects;
			this.renderState.initialize(view);
			doRender = false;
		}
	}

	/**
	 * rotate 180 degrees
	 */
	public void rotate(){
		viewDir+=1;
		if (viewDir>1)
			viewDir = 0;
	}

	/**
	 * get the direction of view
	 * @return view direction
	 */
	public int getViewDir() {
		return viewDir;
	}

	/**
	 * set do animation to true, start animation
	 */
	public void doAnimation(){
		doAnimation = true;
	}

	/**
	 * set do animation to false, stop animaiton
	 */
	public void stopAnimation(){
		doAnimation = false;
	}

	/**
	 * set the facing direction of a player, and set the right image sequence
	 * for animations
	 * @param dir direction the player moves to
	 */
	public void setPlayerFacingDir(int dir){
		animationBound = dir;
		if(playerAnimationIndex < animationBound - 7)
		playerAnimationIndex = animationBound - 6;
	}

	/**
	 * set to day view or night view
	 * @param dayNight a char present day or night
	 */
	public void updateDayNight(char dayNight) {
		if (dayNight == 'N'){
			night = true;
		} else {
			night = false;
		}
	}
}