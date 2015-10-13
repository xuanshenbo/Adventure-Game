package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import model.logic.Game.Direction;
import model.state.Player;
import model.state.Position;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PortalTile implements Tile {

	@XmlTransient
	private Position entry;
	@XmlTransient
	private Position exit;

	public PortalTile(Position entry, Position exit){
		this.entry = entry;
		this.exit = exit;
	}

	@SuppressWarnings("unused")
	private PortalTile() {
		this(null, null);
	}

	@Override
	public void move(Player player, Direction direction) {
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
		return entry;
	}

	@Override
	public boolean isGround() {
		return false;
	}

	@Override
	public boolean isContainer() {
		return false;
	}

}
