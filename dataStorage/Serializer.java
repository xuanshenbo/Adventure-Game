package dataStorage;

import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import dataStorage.pointers.AreaPointer;
import model.state.GameState;

/**
 *
 * @author Shenbo Xuan 300259386
 *
 */
public class Serializer {

	// So no one can accidently create a Serializer class
	private Serializer() {}

	public static void serialize(GameState game) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(new Class[] {
				GameState.class, AreaPointer.class });
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to System.out
		m.marshal(game, System.out);

		// Write to File
		File file = null;
		if (game.getLoadedFile() != null) {
			file = new File(game.getLoadedFile());
			save(m, game, file);
		} else {
			saveAs(m, game);
		}

	}

	public static void serializeAs(GameState game) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(new Class[] {
				GameState.class, AreaPointer.class });
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to System.out
		m.marshal(game, System.out);

		saveAs(m, game);
	}

	private static void save(Marshaller marshaller, GameState game, File file)
			throws JAXBException {
		marshaller.marshal(game, file);
	}

	private static void saveAs(Marshaller marshaller, GameState game)
			throws JAXBException {
		// prompt the user to enter their name
		String fileName = JOptionPane.showInputDialog(null,
				"Save as: (Please enter the file name without extension)");

		String fileNameXml = fileName + ".xml";

		save(marshaller, game, new File(fileNameXml));
	}
}
