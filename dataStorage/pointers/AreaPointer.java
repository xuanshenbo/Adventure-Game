/**
 * A pointer class for the model.state.Area class to avoid serialization cycles.
 * Every time a cycle is detected at run time, rather than saving the original
 * model.state.Area class, this pointer class will be saved.
 *
 * @author Shenbo Xuan 300259386
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
