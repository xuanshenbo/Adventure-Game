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
 * This represnts a tree in the game, the player cannot move onto a tree
 * @author tuckergare
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TreeTile implements Tile {

	@XmlTransient
	private Position position;
	private char id = 'T';
	private int x;
	private int y;

	public TreeTile(Position position){
		this.position = position;
		x = position.getX();
		y = position.getY();
	}

	@SuppressWarnings("unused")
	private TreeTile() {
		this(null);
	}

	@Override
	public void move(Player player, Direction direction) {

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
		return position;
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

	public void setPosition(Position position) {
		this.position = position;
	}


}
