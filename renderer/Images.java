package renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import view.utilities.ImageLoader;
import model.state.Player;
import static utilities.PrintTool.p;

/**
 * This holds all the images needed for rendering
 * Created by lucas on 27/09/15.
 * @author Mingmin Ying 300266387
 */
public class Images {

    private int screenWidth, screenHeight;
    private double tileWidth, tileHeight;
    private int imageScale;

    //fields of different images
    private Image worldGroundImage;
    private Image caveGroundImage;
    private Image buildingGroundImage;
    private Image treeImage;
    private Image[] chestImage;
    private Image[] buildingImage;
    private Image[] doorImages;
    private Image[] keyImage;
    private Image[] caveImage;
    private Image shadow;
    private Image zombie;
    private Image bag;
    private Image[] cupcake;
    private Image[] pumpkin;
    private Image night;
    private ArrayList<ArrayList<Image>> avatarImages;


    /**
     * create a Images object. pass the tile size, screen size and image scale.
     * @param tileWidth width of a tile
     * @param tileHeight height of a tile
     * @param imageScale scale of images
     * @param screenWidth width of the screen
     * @param screenHeight height of the screen
     */
    public Images(double tileWidth, double tileHeight, int imageScale, int screenWidth, int screenHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.imageScale = imageScale;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.avatarImages = new ArrayList<ArrayList<Image>>();
        this.doorImages = new Image[16];
        this.cupcake = new Image[2];
        this.pumpkin = new Image[2];
        this.buildingImage = new Image[2];
        this.chestImage = new Image[2];
        this.caveImage = new Image[2];
        this.keyImage = new Image[2];
        loadImages();
    }

    /*
     * load all the images needed and assign them into fields
     */
    private  void loadImages(){
        worldGroundImage = loadImage("worldground.png", imageScale, imageScale*0.6f);

        treeImage = loadImage("tree.png", imageScale*2, imageScale*2);

        caveGroundImage = loadImage("caveground.png", imageScale, imageScale*0.6f);
        caveImage[0] = loadImage("cave3.png", imageScale*2, imageScale*2);
        caveImage[1] = loadImage("caveback.png", imageScale*2, imageScale*2);

        buildingGroundImage = loadImage("buildingground.png", imageScale, imageScale*0.6f);
        buildingImage[0] = loadImage("building.png", imageScale*5, imageScale*5);
        buildingImage[1] = loadImage("buildingback.png", imageScale*5, imageScale*5);

        shadow = loadImage("shadow.png", imageScale, imageScale);
        night = loadImage("night.png", (int)(screenWidth/tileWidth)+1, (int)(screenHeight/tileHeight/2));

        chestImage[0] = loadImage("chest.png", imageScale, imageScale);
        chestImage[1] = loadImage("chestback.png", imageScale, imageScale);


        keyImage[0] = loadImage("key.png", imageScale, imageScale*0.6f);
        keyImage[1] = loadImage("keyback.png", imageScale, imageScale*0.6f);

        cupcake[0] = loadImage("cupcake5.png", imageScale, imageScale);
        cupcake[1] = loadImage("cupcakeback5.png", imageScale, imageScale);

        bag = loadImage("bag.png", imageScale, imageScale);

        zombie = loadImage("zombie.png", imageScale, imageScale);

        //load door images
        for (int i = 0; i < 16; i++){
            doorImages[i] = loadImage("door/"+i+".png", imageScale, imageScale);
        }

        //load avatar images
        for (int i = 0; i < 4; i++){
            ArrayList<Image> avatarSequence = new ArrayList<Image>();
            for (int n = 0; n < 32; n++) {
                avatarSequence.add(loadImage("avatar/" + i + "/" + n + ".png", imageScale, imageScale));
            }
            this.avatarImages.add(avatarSequence);
        }
    }

    /*
     * load Image into a buffered image, and return that buffered image
     * @param filename path of the image
     * @param scaleW scale of image width
     * @param scaleH scale of image height
     * @return buffered image with the image
     */
    private Image loadImage(String filename, int scaleW, float scaleH) {
        Image image = ImageLoader.loadImage(filename);

        BufferedImage img = new BufferedImage((int)tileWidth*scaleW, (int)(tileWidth*scaleH), BufferedImage.TYPE_INT_ARGB);
        //draw the image to bufferedImage
        Graphics2D g = img.createGraphics();
        g.drawImage(image, 0, 0, (int) tileWidth * scaleW, (int) (tileWidth *scaleH), null);
        g.dispose();
        return img;
    }

    /**
     * get out world ground tile image
     * @return out world ground tile image
     */
    public Image ground() {
        return worldGroundImage;
    }

    /**
     * get tree image
     * @return tree image
     */
    public Image tree() {
        return treeImage;
    }

    /**
     * get chest image
     * @param dir view direction
     * @return chest image for the given view direction
     */
    public Image chest(int dir){
        return chestImage[dir];
    }

    /**
     * get building image
     * @param dir
     * @return building image for the given view direction
     */
    public Image building(int dir){
        return buildingImage[dir];
    }

    /**
     * get door image
     * @param doorIndex the image index in the door image sequence
     * @return door image for the given index
     */
    public Image door(int doorIndex){
        return doorImages[doorIndex];
    }

    /**
     * get key image
     * @param dir view direction
     * @return key image for the given view direction
     */
    public Image key(int dir) {
        return keyImage[dir];
    }

    /**
     * get avatar image
     * @param index player index
     * @param sequenceIndex animation image index in the animation sequence
     * @return avatar image for the given player and given index
     */
    public Image avatar(int index, int sequenceIndex){
        return avatarImages.get(0).get(sequenceIndex);
    }

    /**
     * get cave image
     * @param dir view direction
     * @return cave image for the given view direction
     */
    public Image cave(int dir) { return caveImage[dir];
    }

    /**
     * get cave ground tile image
     * @return cave ground tile image
     */
    public Image caveGround() { return caveGroundImage;
    }

    /**
     * get building ground tile image
     * @return building ground tile image
     */
    public Image buildingGround() { return buildingGroundImage;
    }

    /**
     * get shadow image
     * @return shadow image
     */
    public Image shadow() { return shadow;
    }

    /**
     * get zombie image
     * @return zombie image
     */
    public Image zombie() {
        return zombie;
    }

    /**
     * get cupcake image
     * @return cupcake image
     */
    public Image cupcake(int dir) {
        return cupcake[dir];
    }

    /**
     * get bag image
     * @return bag image
     */
    public Image bag(){
        return bag;
    }

    /**
     * get night image
     * @return night image
     */
    public Image getNightImage() {
        return night;
    }
}
