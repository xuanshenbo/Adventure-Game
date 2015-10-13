package renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import view.utilities.ImageLoader;
import model.state.Player;
import static utilities.PrintTool.p;

/**
 * Created by lucas on 27/09/15.
 */
public class Images {

    private double tileWidth, tileHeight;
    private int imageScale;

    private ArrayList<Player> players;

    //ground images
    private Image worldGroundImage;
    private Image caveGroundImage;

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


    private ArrayList<ArrayList<Image>> avatarImages;


    public Images(double tileWidth, double tileHeight, int imageScale) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.imageScale = imageScale;

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

    private  void loadImages(){
        //load environment images
        this.worldGroundImage = loadImage("worldground.png", imageScale, imageScale*0.6f);
        this.caveGroundImage = loadImage("caveground.png", imageScale, imageScale*0.6f);
        this.treeImage = loadImage("tree.png", imageScale*2, imageScale*2);

        buildingImage[0] = loadImage("building.png", imageScale*5, imageScale*5);
        buildingImage[1] = loadImage("buildingback.png", imageScale*5, imageScale*5);

        caveImage[0] = loadImage("cave3.png", imageScale*2, imageScale*2);
        caveImage[1] = loadImage("caveback.png", imageScale*2, imageScale*2);

        this.shadow = loadImage("shadow.png", imageScale, imageScale);

        //load items images
        chestImage[0] = loadImage("chest.png", imageScale, imageScale);
        chestImage[1] = loadImage("chestback.png", imageScale, imageScale);


        keyImage[0] = loadImage("key.png", imageScale, imageScale*0.6f);
        keyImage[1] = loadImage("keyback.png", imageScale, imageScale*0.6f);

        cupcake[0] = loadImage("cupcake5.png", imageScale, imageScale);
        cupcake[1] = loadImage("cupcakeback5.png", imageScale, imageScale);

        pumpkin[0] = loadImage("pumpkin.png", imageScale, imageScale);
        pumpkin[1] = loadImage("pumpkinback.png", imageScale, imageScale);

        this.bag = loadImage("bag.png", imageScale, imageScale);

        //load characters images
        this.zombie = loadImage("zombie.png", imageScale, imageScale);

        //load door images
        for (int i = 0; i < 16; i++){
            doorImages[i] = loadImage("door/"+i+".png", imageScale, imageScale);
        }

        for (int i = 0; i < 4; i++){
            ArrayList<Image> avatarSequence = new ArrayList<Image>();
            for (int n = 0; n < 32; n++) {
                avatarSequence.add(loadImage("avatar/" + i + "/" + n + ".png", imageScale, imageScale));
            }
            this.avatarImages.add(avatarSequence);
        }
    }

    private Image loadImage(String filename, int scaleW, float scaleH) {
        Image image = ImageLoader.loadImage(filename);

        BufferedImage img = new BufferedImage((int)tileWidth*scaleW, (int)(tileWidth*scaleH), BufferedImage.TYPE_INT_ARGB);
        //draw the image to bufferedImage
        Graphics2D g = img.createGraphics();
        g.drawImage(image, 0, 0, (int) tileWidth * scaleW, (int) (tileWidth *scaleH), null);
        g.dispose();
        return img;
    }

    public Image ground() {
        return worldGroundImage;
    }

    public Image tree() {
        return treeImage;
    }

    public Image chest(int dir){
        return chestImage[dir];
    }

    public Image building(int dir){
        return buildingImage[dir];
    }

    public Image door(int doorIndex){
        return doorImages[doorIndex];
    }

    public Image key(int dir) {
        return keyImage[dir];
    }

    public Image avatar(int index, int sequenceIndex){
        return avatarImages.get(0).get(sequenceIndex);
    }

    public Image cave(int dir) { return caveImage[dir];
    }

    public Image caveGround() { return caveGroundImage;
    }

    public Image shadow() { return shadow;
    }

    public Image zombie() {
        return zombie;
    }

    public Image cupcake(int dir) {
        return cupcake[dir];
    }

    public Image bag(){
        return bag;
    }

    public Image pumpkin(int dir) {
        return pumpkin[dir];
    }
}
