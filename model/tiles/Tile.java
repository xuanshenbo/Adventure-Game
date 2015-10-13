package model.tiles;

import javax.xml.bind.annotation.XmlSeeAlso;

import model.logic.Game.Direction;
import model.state.Player;
import model.state.Position;

@XmlSeeAlso({ BuildingAnchorTile.class, BuildingTile.class,
		CaveAnchorTile.class, CaveEntranceTile.class, CaveTile.class,
		ChestTile.class, DoorTile.class, GroundTile.class, PortalTile.class,
		TreeTile.class })
public interface Tile {

	/**
	 * What happens when a player moves on this tile
	 *
	 * @param player
	 *            : player that moved into the tile
	 * @param direction
	 *            : direction player moved into the tile with.
	 */
	public void move(Player player, Direction direction);

	public void interact(Player player);

	public char getType();

	public Position getPosition();

	public boolean isGround();

	public boolean isContainer();
}
