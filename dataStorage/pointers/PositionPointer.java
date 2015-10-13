package dataStorage.pointers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * A pointer class for the model.state.Position class to avoid serialization
 * cycles. Every time a cycle is detected at run time, rather than saving the
 * original model.state.Position class, this pointer class will be saved.
 *
 * @author Shenbo Xuan 300259386
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PositionPointer {

	private int x;
	private int y;

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
