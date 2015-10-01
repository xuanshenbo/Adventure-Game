package renderer;

import GUI.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import model.state.Player;
import static utilities.PrintTool.p;

/**
 * Created by lucas on 27/09/15.
 */
public class Images {

    private double tileWidth, tileHeight;
    private ArrayList<Player> players;

    private Image treeImage;
    private Image groundImage;
    private Image chestImage;
    private Image buildingImage;
    private Image doorImage;
    private Image keyImage;

    private ArrayList<AvatarImages> avatarImages;

    public Images(double tileWidth, double tileHeight, ArrayList<Player> players) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.players = players;

        this.avatarImages = new ArrayList<AvatarImages>();

        loadImages();

    }

    private  void loadImages(){
        //load environment images
        this.groundImage = loadImage("ground.png", 1, 1);
        this.treeImage = loadImage("tree.png", 3, 3);
        this.buildingImage = loadImage("building.png", 1, 1);
        this.doorImage = loadImage("door.png", 1, 1);

        //load items images
        this.chestImage = loadImage("chest.png", 1, 1);
        this.keyImage = loadImage("key.png", 1, 1);

        //load characters images
        for (int i = 0; i < players.size(); i++){
            int avatarImageIndex = new Random().nextInt(3);
            p(avatarImageIndex);
            this.avatarImages.add(new AvatarImages("avatar" + avatarImageIndex + ".png", tileWidth));
        }
    }

    private Image loadImage(String filename, int scaleW, int scaleH) {
        Image image = ImageLoader.loadImage(filename);

        BufferedImage img = new BufferedImage((int)tileWidth*scaleW, (int)tileWidth*scaleH, BufferedImage.TYPE_INT_ARGB);
        //draw the image to bufferedImage
        Graphics2D g = img.createGraphics();
        g.drawImage(image, 0, 0, (int) tileWidth * scaleW, (int) tileHeight * scaleH, null);
//		g.drawImage(image, 0, 0, (int) (tileWidth * scale), (int) (tileHeight * scale), 0, 0, width, height, null);
        g.dispose();
        return img;
    }

    public Image getGroundImage() {
        return groundImage;
    }

    public Image getTreeImage() {
        return treeImage;
    }

    public Image getChestImage(){
        return chestImage;
    }

    public Image getBuildingImage(){
        return buildingImage;
    }

    public Image getDoorImage(){
        return doorImage;
    }

    public Image getKeyImage() {
        return keyImage;
    }

    public ArrayList<AvatarImages> getAvatarImages(){
        return avatarImages;
    }

}
