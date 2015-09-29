package renderer;

import control.Client;
import control.Server;
import logic.Game;
import logic.Generator;
import state.Area;
import state.GameState;
import state.Player;
import state.Position;
import tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import static utilities.PrintTool.p;

/**
 * Created by lucas on 27/09/15.
 */
public class GuiForTest extends JFrame implements KeyListener{

    private static Game game;
    private RendererCanvas can;

    private GameRenderer renderer;

    public GuiForTest(Game game) {

        super("Adventure game");

        p("finish game generation");

        setSize(800, 600);
        setLayout(new BorderLayout());
        this.game = game;

//        generateGame(trees, buildings, caves, chests, width, height, playerCount, lootValue);
//
        renderer = new GameRenderer(800, 600, game.getState().getGameView(game.getState().getPlayer(1)), null, game.getState().getPlayerList());

        can = new RendererCanvas(renderer.getImage());

        add(can, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);

        addKeyListener(this);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //If press Left Arrow, go left
        if (e.getKeyCode() == 37) {
            p("Left");
            game.move(game.getState().getPlayer(1), 1);
            renderer.update(game.getState().getGameView(game.getState().getPlayer(1)), null, game.getState().getPlayerList());
            can.repaint();
        }
        //If press Up Arrow, go up
        if (e.getKeyCode() == 38) {
            p("Up");
            game.move(game.getState().getPlayer(1), 4);
            renderer.update(game.getState().getGameView(game.getState().getPlayer(1)), null, game.getState().getPlayerList());
            can.repaint();
        }
        //If press Right Arrow, go right
        if (e.getKeyCode() == 39) {
            p("Right");
            game.move(game.getState().getPlayer(1), 2);
            renderer.update(game.getState().getGameView(game.getState().getPlayer(1)), null, game.getState().getPlayerList());
            can.repaint();
        }
        //If press Down Arrow, go down
        if (e.getKeyCode() == 40) {
            p("Down");
            game.move(game.getState().getPlayer(1), 3);
            renderer.update(game.getState().getGameView(game.getState().getPlayer(1)), null, game.getState().getPlayerList());
            can.repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //width >= 20 & height >= 20
    private static Game generateGame(int trees, int buildings, int caves, int chests, int width, int height, int playerCount, int lootValue) {
        Generator g = new Generator(trees, buildings, caves, chests, lootValue);
        Area a = new Area(width, height, Area.AreaType.OUTSIDE, null);
        a.generateWorld(g);
        ArrayList<Player> p = placePlayers(playerCount, width, height, a);
        GameState state = new GameState(a, p);
        Game game = new Game(state);
//        this.game = game;
        return game;
    }

    private static ArrayList<Player> placePlayers(int playerCount, int width, int height, Area a) {
        double[] xCoords = {0.5, 0, 0.5, 1};
        double[] yCoords = {0, 0.5, 1, 0.5};
        ArrayList<Player> list = new ArrayList<Player>();
        for(int count = 0; count < playerCount; count++){
            int x = (int) ((width-1)*xCoords[count]);
            int y = (int) ((height-1)*yCoords[count]);
            int id = count+1;
            Position position = new Position(x, y, a);
            Player p = new Player(position, id);
            list.add(p);
        }
        return list;

    }

    //public to be used in GameFrame
    public class RendererCanvas extends JPanel{

        private Image bi;

        public RendererCanvas(Image bufferedImage){
            super();
            bi = bufferedImage;
//            setSize(new Dimension(800, 600));

        }

        public RendererCanvas(){
            super();
//            setSize(new Dimension(800, 600));

        }

        public Dimension getPreferredSize() {
            return new Dimension(800, 600);
        }

        public void paint(Graphics g){
            Graphics2D g2;
            g2 = (Graphics2D) g;
            g2.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
            g2.dispose();
        }

    }

    public static void main(String[] args){
        p("start");
        GuiForTest app = new GuiForTest(generateGame(20, 2, 1, 5, 20, 20, 4, 50));

    }


}
