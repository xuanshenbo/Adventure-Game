package renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.PrintTool.p;

/**
 * create a game canvas to show the out put screen
 * Created by lucas on 7/10/15
 * @author Mingmin Ying 300266387
 */
public class GameCanvas extends JPanel {
    private BufferedImage image;
    private int width, height;
    private GameRenderer renderer;

    /**
     * Game canvas that showing the main game, based on the width and height that are passed in
     * @param width: the width of the main game
     * @param height: the height of the main game
     */
    public GameCanvas(int width, int height){
        super();
        this.width = width;
        this.height = height;
        char[][] view = new char[31][31];
        char[][] objects = new char[31][31];
        this.renderer = new GameRenderer(800, 600, view, objects, this);
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * paint this game canvas
     * @param g graphics to draw
     */
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        if(image == null){
            g2.fillRect(0, 0, 800, 600);
        }else{
            g2.drawImage(image,0,0,this);
        }
    }

    /**
     * update the main screen image from the renderer
     * @param img the out put image from renderer
     */
    public void updateImage(BufferedImage img){
        image = img;
        repaint();
    }

    /**
     * return renderer
     * @return renderer
     */
    public GameRenderer getRenderer(){
        return renderer;
    }
}
