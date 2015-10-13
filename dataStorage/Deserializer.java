package dataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import view.styledComponents.MenuBar;
import model.state.GameState;

/**
 * Provide static load method to load a saved game from an xml file.
 *
 * @author Shenbo Xuan 300259386
 *
 */
public class Deserializer {

	// So no one can accidently create a Deserializer class
	private Deserializer() {
	}

	/**
	 * Return a GameState that is stored in the given file
	 *
	 * @param fileName
	 * @return GameState
	 * @throws JAXBException
	 */
	public static GameState deserialize() throws JAXBException {
		File file = getFile();

		if (file == null) {
			return null;
		}

		JAXBContext context = JAXBContext
				.newInstance(new Class[] { GameState.class });

		Unmarshaller um = context.createUnmarshaller();

		return (GameState) um.unmarshal(file);

	}

	// get a file from user
	private static File getFile() {
		System.out.println("here");
		// Create a file chooser
		final JFileChooser fc = new JFileChooser("games/");

		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			return file;
		}

		return null;
	}
}
