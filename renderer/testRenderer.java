package renderer;

import logic.Generator;
import state.Area;
import state.Player;
import state.Position;

import java.util.ArrayList;

/**
 * Created by lucas on 22/09/15.
 */
public class testRenderer {

    private ArrayList<Player> players;
    private Area area;

    public testRenderer(int trees, int buildings, int caves, int chests, int width, int height, int playerCount, int lootValue){
        Generator g = new Generator(trees, buildings, caves, chests, lootValue);
        area = new Area(width, height, Area.AreaType.OUTSIDE, null);
        area.generateWorld(g);
        players = placePlayers(playerCount, width, height, area);
    }

    private ArrayList<Player> placePlayers(int playerCount, int width, int height, Area a) {
        double[] xCoords = {0.5, 0.2, 0.5, 0.8};
        double[] yCoords = {0.2, 0.5, 0.8, 0.5};
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

    public Area getArea() {
        return area;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
