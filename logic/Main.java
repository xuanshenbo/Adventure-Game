package logic;

import java.util.ArrayList;

import state.Area;
import state.Area.AreaType;
import state.GameState;
import state.Player;
import state.Position;
import static utilities.PrintTool.p;

public class Main {

	public Main(int trees, int buildings, int caves, int chests, int width, int height, int playerCount){
		Generator g = new Generator(trees, buildings, caves, chests);
		Area a = new Area(width, height, AreaType.OUTSIDE, null);
		a.generateWorld(g);
		ArrayList<Player> p = placePlayers(playerCount, width, height, a);
		GameState state = new GameState(a, p);
		Game game = new Game(state);

	}

	private ArrayList<Player> placePlayers(int playerCount, int width, int height, Area a) {
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

	public static void main(String[] args){
		Main g = new Main(20, 2, 1, 5, 10, 20, 4);
	}


}
