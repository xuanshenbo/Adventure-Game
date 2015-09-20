package renderer;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AvatarImages {
    private static final int avater_width = 70;
    private static final int avater_height = 124;

    private Image[][] images;

    private int imageWidth;
    private int imageHeight;
    private int size = 0;

    public AvatarImages(String fileName){
        this(fileName, avater_width, avater_height, 4);
    }

    public AvatarImages(String fileName, int width, int height, int size){
        this.imageWidth = width;
        this.imageHeight = height;
        this.size = size;
        this.images = split(loadImage(fileName), width, height);
    }

    public Image loadImage(String fileName){
        Image result = null;
        FileInputStream image = null;
        try{
            image = new FileInputStream(fileName);
            result = new Image(image);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }finally{
            try{
                if(image != null) image.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public Image[][] split(Image source, int width, int height){
        int cols = (int) source.getWidth() / width;
        int rows = (int) source.getHeight() / height;
        Image[][] result = new Image[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                result[i][j] = clip(source, width, height, j * width, i * height);
            }
        }

        return result;
    }

    public Image clip(Image source, int width, int height, int x, int y){
        PixelReader reader = source.getPixelReader();
        return new WritableImage(reader, x, y, width, height);
    }

}
