package logic;

import java.util.ArrayList;

import state.Area;
import state.Area.AreaType;
import state.GameState;
import state.ID;
import state.Player;
import state.Position;
import static utilities.PrintTool.p;

public class StartGame {

	public StartGame(int trees, int buildings, int caves, int chests, int width, int height, int playerCount){
		Generator g = new Generator(trees, buildings, caves, chests);
		Area a = new Area(width, height, AreaType.OUTSIDE, null);
		a.generateWorld(g);
		ArrayList<Player> p = placePlayers(playerCount, width, height);
		GameState state = new GameState(a, p);
	}

	private ArrayList<Player> placePlayers(int playerCount, int width, int height) {
		double[] xCoords = {0.5, 0, 0.5, 1};
		double[] yCoords = {0, 0.5, 1, 0.5};
		ArrayList<Player> list = new ArrayList<Player>();
		for(int count = 0; count < playerCount; count++){
			int x = (int) (width*xCoords[count]);
			int y = (int) (height*yCoords[count]);
			ID id = new ID(count+1);
			Position position = new Position(x, y);
			Player p = new Player(position, id);
			list.add(p);
		}
		return list;

	}

	public void clockTick() {
		// TODO Auto-generated method stub
	}

	public static void main(String[] args){
		StartGame g = new StartGame(10, 2, 1, 5, 10, 20, 4);
	}

}
