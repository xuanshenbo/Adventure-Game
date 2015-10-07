/**
 * This is the main area class, it is made up of tiles and internal
 * areas. It holds no logic and is just a state class.
 * It it a certian type of area, building, cave etc. It also holds
 * another 2d array that holds all the objects on the ground
 * in the area.
 * To create a new Area you will need to give it a height and
 * width. To generate a new random area you will need to create
 * a generator and call generateWorld
 */

package dataStorage.pointers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import model.items.Item;
import model.state.Area.AreaType;
import model.tiles.Tile;

@XmlAccessorType(XmlAccessType.FIELD)
public class AreaPointer {

	private AreaType type;
	@XmlTransient
	private Tile[][] area;
	@XmlTransient
	private Item[][] items;

	public void setType(AreaType type) {
		this.type = type;
	}

	public void setArea(Tile[][] area) {
		this.area = area;
	}

	public void setItems(Item[][] items) {
		this.items = items;
	}

}
