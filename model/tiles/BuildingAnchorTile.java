package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import model.state.Position;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BuildingAnchorTile extends BuildingTile{

	private char id = 'b';
	@XmlTransient
	private Position position;

	public BuildingAnchorTile(Position position) {
		super(position);
		this.position = position;
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
