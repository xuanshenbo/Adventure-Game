package state;


public class GrassTile implements TileInterface {	

	private Position position;
	
	public GrassTile(Position position){
		this.position = position;
	}

	@Override
	public void move(Player player, int direction) {
		player.setPosition(position);
	}

}
