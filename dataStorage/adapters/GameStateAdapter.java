package dataStorage.adapters;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import model.state.Area;
import model.state.GameState;
import model.state.Player;

/**
 * An adapter class for model.state.GameState. It is useful to create a
 * customised marshalling. In this case, GameStateAdapter class can set
 * model.state.GameState's fileds and does not require its constructor.
 *
 * @author Shenbo Xuan 300259386
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GameStateAdapter extends XmlAdapter<GameStateAdapter, GameState> {

	@XmlElementWrapper
	@XmlElement(name = "player")
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private Area world;

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	public Area getWorld() {
		return world;
	}

	public void setWorld(Area world) {
		this.world = world;
	}

	@Override
	public GameState unmarshal(GameStateAdapter adaptedGameState)
			throws Exception {
		return new GameState(adaptedGameState.getWorld(),
				adaptedGameState.getPlayerList());
	}

	@Override
	public GameStateAdapter marshal(GameState gameState) throws Exception {
		GameStateAdapter adaptedGameState = new GameStateAdapter();
		adaptedGameState.setWorld(gameState.getWorld());
		adaptedGameState.setPlayerList(gameState.getPlayerList());
		return adaptedGameState;
	}

}
