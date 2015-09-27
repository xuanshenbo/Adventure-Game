package tiles;

import state.Player;
import state.Position;

public interface Tile {

	public void move(Player player, int direction);
	public void interact(Player player);
	public char getType();
	public Position getPosition();
}
