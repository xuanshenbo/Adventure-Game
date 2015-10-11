package model.tiles;

import model.state.Position;

public class BuildingAnchorTile extends BuildingTile{

	public BuildingAnchorTile(Position position) {
		super(position);
	}

	@Override
	public char getType() {
		return 'b';
	}
	
	

}
