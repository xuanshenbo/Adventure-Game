package dataStorage.pointers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import com.sun.xml.internal.bind.CycleRecoverable;

import model.items.Item;
import model.state.Area.AreaType;
import model.tiles.Tile;

/**
 * A pointer class for the model.state.Area class to avoid serialization cycles.
 * Every time a cycle is detected at run time, rather than saving the original
 * model.state.Area class, this pointer class will be saved.
 *
 * @author Shenbo Xuan 300259386
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AreaPointer {

	@XmlElementWrapper
	@XmlAnyElement
	private Tile[] areasSerializable;
	private AreaType type;
	@XmlTransient
	private Item[][] items;

	public void setType(AreaType type) {
		this.type = type;
	}

	public void setAreaSerialzable(Tile[] areasSerializable) {
		this.areasSerializable = areasSerializable;
	}

	public void setItems(Item[][] items) {
		this.items = items;
	}
}
