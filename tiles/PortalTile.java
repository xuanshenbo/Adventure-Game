package tiles;

import state.Player;
import state.Position;

public class PortalTile implements Tile {
	
	private Position entry;
	private Position exit;

	public PortalTile(Position entry, Position exit){
		this.entry = entry;
		this.exit = exit;
	}

	@Override
	public void move(Player player, int direction) {
		if(player.getPosition() == exit){
			player.setPosition(entry);
		}else{
			player.setPosition(exit);
		}
	}

	@Override
	public void interact(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public char getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

}
