package renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.PrintTool.p;

/**
 * Created by lucas on 7/10/15.
 */
public class GameCanvas extends JPanel {
    private BufferedImage image;
    private int width, height;
    private GameRenderer renderer;

    public GameCanvas(int width, int height){
        super();
        this.width = width;
        this.height = height;
        char[][] view = new char[15][15];
        char[][] objects = new char[15][15];
        this.renderer = new GameRenderer(800, 600, view, objects, this);
        setPreferredSize(new Dimension(width, height));
    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        if(image == null){
            g2.fillRect(0, 0, 800, 600);
        }else{
            g2.drawImage(image,0,0,this);
        }
    }

    public void updateImage(BufferedImage img){
        image = img;
    }

    public GameRenderer getRenderer(){
        return renderer;
    }
}
