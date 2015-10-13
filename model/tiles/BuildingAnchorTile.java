package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import model.state.Position;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BuildingAnchorTile extends BuildingTile{

	public BuildingAnchorTile(Position position) {
		super(position);
	}

	@SuppressWarnings("unused")
	private BuildingAnchorTile() {
		this(null);
	}

	@Override
	public char getType() {
		return 'b';
	}



}
