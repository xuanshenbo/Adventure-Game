package dataStorage.adapters;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.sun.xml.internal.bind.CycleRecoverable;

import dataStorage.pointers.AreaPointer;
import model.items.Item;
import model.state.Area;
import model.state.Position;
import model.tiles.Tile;

/**
 * An adapter class for model.state.Area. It is useful to create a customised
 * marshalling. In this case, AreaAdapter class can set model.state.Area's
 * fileds and does not require its constructor.
 *
 * @author Shenbo Xuan 300259386
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AreaAdapter extends XmlAdapter<AreaAdapter, Area> implements
		CycleRecoverable {
	@XmlType(name = "AreaType")
	@XmlEnum
	public enum AreaType {
		OUTSIDE('G'), BUILDING('W'), CAVE('R');

		public final char id;

		private AreaType(char c) {
			id = c;
		}

		// for serializing final fields purpose.
		private AreaType() {
			this('\u0000');
		}

	}

	// serialization testing
	@XmlElementWrapper
	@XmlAnyElement
	private Tile[] areasSerializable;

	private int height;
	private int width;

	private model.state.Area.AreaType type;
	@XmlTransient
	private Item[][] items;
	@XmlElement
	private Position entrance;
	@XmlElementWrapper
	@XmlElement(name = "internalArea")
	private ArrayList<Area> internalAreas = new ArrayList<Area>();
	@XmlElement
	private Position exitPosition;
	@XmlElementWrapper
	@XmlElement(name = "caveEntrance")
	private ArrayList<Position> caveEntrances = new ArrayList<Position>();

	public Tile[] getAreasSerializable() {
		return areasSerializable;
	}

	public void setAreasSerializable(Tile[] areasSerializable) {
		this.areasSerializable = areasSerializable;
	}

	public model.state.Area.AreaType getType() {
		return type;
	}

	public void setType(model.state.Area.AreaType type) {
		this.type = type;
	}

	public Item[][] getItems() {
		return items;
	}

	public void setItems(Item[][] items) {
		this.items = items;
	}

	public Position getEntrance() {
		return entrance;
	}

	public void setEntrance(Position entrance) {
		this.entrance = entrance;
	}

	public ArrayList<Area> getInternalAreas() {
		return internalAreas;
	}

	public void setInternalAreas(ArrayList<Area> internalAreas) {
		this.internalAreas = internalAreas;
	}

	public Position getExitPosition() {
		return exitPosition;
	}

	public void setExitPosition(Position exitPosition) {
		this.exitPosition = exitPosition;
	}

	public ArrayList<Position> getCaveEntrances() {
		return caveEntrances;
	}

	public void setCaveEntrances(ArrayList<Position> caveEntrances) {
		this.caveEntrances = caveEntrances;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public Area unmarshal(AreaAdapter adaptedArea) throws Exception {
		Area area = new Area(adaptedArea.getHeight(), adaptedArea.getWidth(),
				adaptedArea.getType(), adaptedArea.getEntrance());
		area.setItems(adaptedArea.getItems());
		area.setAreasSerializable(adaptedArea.getAreasSerializable());
		area.setArea(to2dArray(adaptedArea.getAreasSerializable(),
				adaptedArea.getHeight(), adaptedArea.getWidth()));
		area.setInternalAreas(adaptedArea.getInternalAreas());
		area.setExitPosition(adaptedArea.getExitPosition());
		area.setCaveEntrance(adaptedArea.getCaveEntrances());
		return area;
	}

	@Override
	public AreaAdapter marshal(Area area) throws Exception {
		AreaAdapter adaptedArea = new AreaAdapter();
		adaptedArea.setHeight(area.getHeight());
		adaptedArea.setWidth(area.getWidth());
		adaptedArea.setAreasSerializable(area.getAreasSerializable());
		adaptedArea.setType(area.getType());
		adaptedArea.setItems(area.getItems());
		adaptedArea.setEntrance(area.getEntrance());
		adaptedArea.setInternalAreas(area.getInternalAreas());
		adaptedArea.setExitPosition(area.getExitPosition());
		adaptedArea.setCaveEntrances(area.getCaveEntrances());
		return adaptedArea;
	}

	private Tile[][] to2dArray(Tile[] areasSerializable, int height, int width) {
		Tile[][] twoDArray = new Tile[height][width];

		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				twoDArray[w][h] = areasSerializable[h * width + w];
			}
		}

		return twoDArray;
	}

	@Override
	public Object onCycleDetected(Context arg0) {
		AreaPointer areaPointer = new AreaPointer();
		areaPointer.setType(type);
		areaPointer.setAreaSerialzable(areasSerializable);
		areaPointer.setItems(items);
		return areaPointer;
	}

}
