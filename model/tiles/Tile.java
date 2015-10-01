package model.tiles;

import model.state.Player;
import model.state.Position;

public interface Tile {

	public void move(Player player, int direction);
	public void interact(Player player);
	public char getType();
	public Position getPosition();
	public boolean isGround();
}
