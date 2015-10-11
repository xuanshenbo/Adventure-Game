package model.tiles;

import model.state.Position;

public class CaveAnchorTile extends CaveTile{

	public CaveAnchorTile(Position position) {
		super(position);
	}

	@Override
	public char getType() {
		return 'c';
	}
	
	
	
}
