package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import model.state.Position;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CaveAnchorTile extends CaveTile {

	private char id = 'c';
	@XmlTransient
	private Position position;

	public CaveAnchorTile(Position position) {
		super(position);
		this.position = position;
	}

	@SuppressWarnings("unused")
	private CaveAnchorTile() {
		this(null);
	}

	@Override
	public char getType() {
		return 'c';
	}

}
