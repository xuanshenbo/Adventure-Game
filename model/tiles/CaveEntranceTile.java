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
 * This is the cave entrance tile, it controls when the player moves into the
 * cave and moves it to the position of the exit.
 * @author tuckergare
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CaveEntranceTile implements Tile {

	private char id = 'E';
	@XmlTransient
	private Position entry;
	@XmlTransient
	private Position exit;
	private int entryX;
	private int entryY;
	private int exitX;
	private int exitY;

	public CaveEntranceTile(Position entry, Position exit){
		this.entry = entry;
		this.exit = exit;
		entryX = entry.getX();
		entryY = entry.getY();
		exitX = exit.getX();
		exitY = exit.getY();
	}

	@SuppressWarnings("unused")
	private CaveEntranceTile() {
		this(null, null);
	}
	
	/**
	 * When this is called the player is transported to the entry or exit
	 * depending on what tile they entered on.
	 */
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
		return id;
	}

	public String toString(){
		return Character.toString(id);
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

	public char getId() {
		return id;
	}

	public void setId(char id) {
		this.id = id;
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
