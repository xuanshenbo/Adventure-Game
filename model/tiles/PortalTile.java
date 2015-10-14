package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import model.logic.Game.Direction;
import model.state.Player;
import model.state.Position;

/**
 * Not implemented
 * @author tuckergare
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PortalTile implements Tile {

	@XmlTransient
	private Position entry;
	@XmlTransient
	private Position exit;
	private int entryX;
	private int entryY;
	private int exitX;
	private int exitY;

	public PortalTile(Position entry, Position exit){
		this.entry = entry;
		this.exit = exit;
		entryX = entry.getX();
		entryY = entry.getY();
		exitX = exit.getX();
		exitY = exit.getY();
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

	public Position getEntry() {
		return entry;
	}

	public void setEntry(Position entry) {
		this.entry = entry;
	}

	public Position getExit() {
		return exit;
	}

	public void setExit(Position exit) {
		this.exit = exit;
	}

	public int getEntryX() {
		return entryX;
	}

	public void setEntryX(int entryX) {
		this.entryX = entryX;
	}

	public int getEntryY() {
		return entryY;
	}

	public void setEntryY(int entryY) {
		this.entryY = entryY;
	}

	public int getExitX() {
		return exitX;
	}

	public void setExitX(int exitX) {
		this.exitX = exitX;
	}

	public int getExitY() {
		return exitY;
	}

	public void setExitY(int exitY) {
		this.exitY = exitY;
	}


}
