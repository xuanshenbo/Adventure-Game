package model.logic;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import model.state.Area;
import model.state.GameState;
import model.state.Player;
import model.state.Position;
import model.state.Area.AreaType;
import control.Client;
import control.Server;
import static utilities.PrintTool.p;

public class Main {
	public Main(int trees, int buildings, int caves, int chests, int width, int height, int playerCount, Server ss, int lootValue){
		Generator g = new Generator(trees, buildings, caves, chests, lootValue);
		Area a = new Area(width, height, AreaType.OUTSIDE, null);
		a.generateWorld(g);
		ArrayList<Player> p = placePlayers(playerCount, width, height, a);
		GameState state = new GameState(a, p);
		Game game = new Game(state);


		char[][] charArray = game.getGameState().getWorld(p.get(1)).getCharArray();
		ss.updateMap(charArray);
	}

	private ArrayList<Player> placePlayers(int playerCount, int width, int height, Area a) {
		double[] xCoords = {0.5, 0, 0.5, 1};
		double[] yCoords = {0, 0.5, 1, 0.5};
		ArrayList<Player> list = new ArrayList<Player>();
		for(int count = 0; count < playerCount; count++){
			int x = (int) ((width-1)*xCoords[count]);
			int y = (int) ((height-1)*yCoords[count]);
			p("player"+(count+1)+" XGEN: "+x+" YGEN"+y);
			int id = count+1;
			Position position = new Position(x, y, a);
			Player p = new Player(position, id);
			list.add(p);
		}
		return list;

	}

	public static void main(String[] args){
		/*Server ss = new Server();
		Main g = new Main(20, 25, 10, 25, 200, 200, 4, ss, 50);
		ss.start();
		try {
			Socket socket = new Socket(ss.getAddress(),ss.PORT);
			Client client = new Client(socket);
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}



}
