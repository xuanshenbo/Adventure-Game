package utilities;

/* This is class provides a static mathod that deep-clones an object.
 * for now it is not quite useful. I will modify it later so that
 * Felix might be able to send binary which is smaller which will make
 * the network faster
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * This is an ObjectCloner class It deep clones an object using serialization
 *
 * @author Shenbo Xuan 300259386
 *
 */
public class ObjectCloner {

	// so that nobody can accidentally create an ObjectCloner object
	private ObjectCloner() {
	}

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
