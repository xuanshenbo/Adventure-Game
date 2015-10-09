package renderer;

import GUI.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    private Image buildingImage;
    private Image doorImage;
    private Image keyImage;
    private Image caveImage;
    private Image shadow;


    private ArrayList<Image> avatarImages;

    public Images(double tileWidth, double tileHeight, int imageScale) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.imageScale = imageScale;

        this.avatarImages = new ArrayList<Image>();

        loadImages();

    }

    private  void loadImages(){
        //load environment images
        this.worldGroundImage = loadImage("worldground.png", imageScale, imageScale*1.05f);
        this.caveGroundImage = loadImage("caveground.png", imageScale, imageScale*1.05f);
        this.treeImage = loadImage("tree.png", imageScale*2, imageScale*2);
        this.buildingImage = loadImage("building.png", imageScale*5, imageScale*5);
        this.doorImage = loadImage("door.png", imageScale, imageScale);
        this.caveImage = loadImage("cave2.png", imageScale*2, imageScale*2);
        this.shadow = loadImage("shadow.png", imageScale, imageScale);

        //load items images
        this.chestImage = loadImage("chest.png", imageScale, imageScale);
        this.keyImage = loadImage("key.png", imageScale, imageScale);

        //load characters images
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
//        g.drawImage(image, 0, 0, (int) tileWidth * scaleW, (int) tileHeight * scaleH, null);
        g.drawImage(image, 0, 0, (int) tileWidth * scaleW, (int) (tileWidth *scaleH), null);
//		g.drawImage(image, 0, 0, (int) (tileWidth * scale), (int) (tileHeight * scale), 0, 0, width, height, null);
        g.drawImage(image, 0, 0, 2, 2, null);
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

    public Image door(){
        return doorImage;
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
}
