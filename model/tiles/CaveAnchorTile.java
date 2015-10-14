package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import model.state.Position;


/**
 * This Class is used by the renderer to locate where the corner of 
 * the caves are so that it can draw them correctly
 * @author tuckergare
 *
 */


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CaveAnchorTile extends CaveTile {

	private char id = 'c';
	@XmlTransient
	private Position position;
	private int x;
	private int y;

	public CaveAnchorTile(Position position) {
		super(position);
		this.position = position;
		x = position.getX();
		y = position.getY();
	}

	@SuppressWarnings("unused")
	private CaveAnchorTile() {
		this(null);
	}

	@Override
	public char getType() {
		return 'c';
	}

	public char getId() {
		return id;
	}

	public void setId(char id) {
		this.id = id;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
