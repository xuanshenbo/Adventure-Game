package model.tiles;

import javax.xml.bind.annotation.XmlRootElement;

import model.logic.Game.Direction;
import model.state.Player;
import model.state.Position;

@XmlRootElement
public class GroundTile implements Tile {

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
	private Position position;

	public GroundTile(TileType t, Position position) {
		this.type = t;
		this.position = position;
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

}
