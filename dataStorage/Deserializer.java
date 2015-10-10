package dataStorage;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.state.GameState;

/**
 * Provide static load method to load a saved game.
 * @author Shenbo Xuan 300259386
 *
 */
public class Deserializer {

	// So no one can accidently create a Serializer class
	private Deserializer() {}

	/**
	 * Return a GameState that is stored in the given file
	 * @param fileName
	 * @return GameState
	 * @throws JAXBException
	 */
	public static GameState deserialize(String fileName) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(new Class[] {
				GameState.class });

		Unmarshaller um = context.createUnmarshaller();

		try {
			return (GameState) um.unmarshal(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File: " + fileName + " not found");
			return null;
		}
	}
}
