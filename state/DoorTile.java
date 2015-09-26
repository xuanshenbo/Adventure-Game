package state;


public class DoorTile implements TileInterface {
	
	private Position position;
	
	public DoorTile(Position position){
		position = position;
	}

	@Override
	public void move(Player player, int direction) {
		
	}

}
