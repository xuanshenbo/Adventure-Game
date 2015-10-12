package renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import view.ImageLoader;
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
    private Image chestImage;
    private Image chestBackImage;
    private Image buildingImage;
    private Image buildingBackImage;
    private Image[] doorImages;
    private Image keyImage;
    private Image caveImage;
    private Image shadow;
    private Image zombie;
    private Image bag;
    private Image cupcake;


    private ArrayList<Image> avatarImages;


    public Images(double tileWidth, double tileHeight, int imageScale) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.imageScale = imageScale;

        this.avatarImages = new ArrayList<Image>();
        this.doorImages = new Image[16];

        loadImages();

    }

    private  void loadImages(){
        //load environment images
        this.worldGroundImage = loadImage("worldground.png", imageScale, imageScale*0.6f);
        this.caveGroundImage = loadImage("caveground.png", imageScale, imageScale*0.6f);
        this.treeImage = loadImage("tree.png", imageScale*2, imageScale*2);
        this.buildingImage = loadImage("building.png", imageScale*5, imageScale*5);
        this.buildingBackImage = loadImage("buildingback.png", imageScale*5, imageScale*5);
        this.caveImage = loadImage("cave2.png", imageScale*2, imageScale*2);
        this.shadow = loadImage("shadow.png", imageScale, imageScale);

        //load items images
        this.chestImage = loadImage("chest.png", imageScale, imageScale);
        this.chestBackImage = loadImage("chestback.png", imageScale, imageScale);
        this.keyImage = loadImage("key.png", imageScale, imageScale*0.6f);
        this.cupcake = loadImage("cupcake2.png", imageScale, imageScale);
        this.bag = loadImage("bag.png", imageScale, imageScale);

        //load characters images
        this.zombie = loadImage("zombie.png", imageScale, imageScale);

        //load door images
        for (int i = 0; i < 16; i++){
            doorImages[i] = loadImage("door/"+i+".png", imageScale, imageScale);
        }

        for (int i = 0; i < 4; i++){
//            int avatarImageIndex = new Random().nextInt(3);
//            p(avatarImageIndex);
//            this.avatarImages.add(new AvatarImages("avatar" + avatarImageIndex + ".png", tileWidth));
            this.avatarImages.add(loadImage("avatar.png", imageScale, imageScale));
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

    public Image chest(){
        return chestImage;
    }

    public Image building(){
        return buildingImage;
    }

    public Image door(int doorIndex){
        return doorImages[doorIndex];
    }

    public Image key() {
        return keyImage;
    }

    public ArrayList<Image> avatar(){
        return avatarImages;
    }

    public Image cave() { return caveImage;
    }

    public Image caveGround() { return caveGroundImage;
    }

    public Image shadow() { return shadow;
    }

    public Image zombie() {
        return zombie;
    }

    public Image cupcake() {
        return cupcake;
    }

    public Image bag(){
        return bag;
    }

    public Image buildingBack() {
        return buildingBackImage;
    }

    public Image chestBack() {
        return chestBackImage;
    }
}
