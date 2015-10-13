package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import model.state.Position;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CaveAnchorTile extends CaveTile{

	public CaveAnchorTile(Position position) {
		super(position);
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
