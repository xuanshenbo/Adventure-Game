package model.tiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import model.logic.Game.Direction;
import model.state.Player;
import model.state.Position;
import static utilities.PrintTool.p;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GroundTile implements Tile {

	@XmlType(name = "TileType")
	@XmlEnum
	public enum TileType {
		GRASS('G'),
		ROCK('R'),
		WOOD('W');

		public final char id;
		private TileType(char c){
			id = c;
		}
		public char encode(){
			return id;
		}
	}

	private TileType type;
	@XmlTransient
	private Position position;
	private int x;
	private int y;

	public GroundTile(TileType t, Position position) {
		this.type = t;
		this.position = position;
		x = position.getX();
		y = position.getY();
	}

	@SuppressWarnings("unused")
	private GroundTile() {
		this(null, null);
	}

	@Override
	public void move(Player player, Direction direction) {
		player.setPosition(position);

	}

	@Override
	public void interact(Player player) {

	}

	@Override
	public char getType() {
		return type.id;
	}

	public String toString(){
		return Character.toString(type.id);
	}

	public Position getPosition(){
		return position;
	}

	@Override
	public boolean isGround() {
		return true;
	}

	@Override
	public boolean isContainer() {
		return false;
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

	public void setType(TileType type) {
		this.type = type;
	}

	public void setPosition(Position position) {
		this.position = position;
	}


}
