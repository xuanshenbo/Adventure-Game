package utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import control.*;
import dataStorage.*;
import GUI.*;
import interpreter.*;
import logic.*;
import renderer.*;
import state.*;

/**
 * Provides a static method deepCopy(Object) to deep clone an object using serialization.
 *
 * @author Shenbo Xuan 300259386
 *
 */
public class ObjectCloner {

	// so that nobody can accidentally create an ObjectCloner object
	private ObjectCloner() {}

	/**
	 * returns a deep cloned object
	 *
	 * @param oldObj
	 * @return
	 * @throws Exception
	 */
	public static Object deepCopy(Object oldObj) throws Exception {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			// serialize and pass the object
			oos.writeObject(oldObj);
			oos.flush();
			ByteArrayInputStream bin = new ByteArrayInputStream(
					bos.toByteArray());
			ois = new ObjectInputStream(bin);
			// return the new object
			return ois.readObject();
		} catch (Exception e) {
			System.out.println("Exception in ObjectCloner = " + e);
			throw (e);
		} finally {
			oos.close();
			ois.close();
		}
	}
}
