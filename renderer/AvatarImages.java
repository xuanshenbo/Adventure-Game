package renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

import view.ImageLoader;

public class AvatarImages {
    private static final int avater_width = 70;
    private static final int avater_height = 124;
    private double scale;

    private Image[][] images;

//    private int imageWidth;
//    private int imageHeight;
//    private int size = 0;

    public AvatarImages(String fileName, double tileWidth){
        this(fileName, avater_width, avater_height, 4, tileWidth);
    }

    public AvatarImages(String fileName, int width, int height, int size, double tileWidth){
//        this.imageWidth = width;
//        this.imageHeight = height;
//        this.size = size;
        this.scale = tileWidth/avater_width;
        this.images = split(ImageLoader.loadImage(fileName), width, height);
        //System.out.println("avatarWidth: " + this.scale*avater_width + " avatarHeight: " + this.scale*avater_height);
    }

    public Image[][] split(Image image, int width, int height){
        int cols = (int) image.getWidth(null) / width;
        int rows = (int) image.getHeight(null) / height;
        System.out.println("cols: " + cols + " rows: " + rows);
        Image[][] result = new Image[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                result[i][j] = clip(image, j * width, i * height, width, height);
            }
        }

        return result;
    }

    public Image clip(Image image, int x, int y, int width, int height){
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //draw the image
        Graphics2D g = img.createGraphics();
        g.drawImage(image, 0, 0, (int) (avater_width * scale), (int) (avater_height * scale), x, y, x + avater_width, y + avater_height, null);
        g.dispose();
        return img;
    }

    public Image[][] getImages(){
        return images;
    }

    public double avatarHeight(){
        return avater_height * scale;
    }

}
