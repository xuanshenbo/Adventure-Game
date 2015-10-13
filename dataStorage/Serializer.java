package dataStorage;

import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import dataStorage.pointers.*;
import model.state.GameState;
import model.tiles.*;

/**
 * Provide static save methods for the game. It uses the binding method to save
 * the state of the game.
 *
 * @author Shenbo Xuan 300259386
 *
 */
public class Serializer {

	// file name of all the saved games will be stored
	private static final String GAMES_PATH = "games/";

	// So no one can accidently create a Serializer class
	private Serializer() {
	}

	/**
	 * If the current game is loaded from a file, save the current game and
	 * overwrite the file. Otherwise, call serializeAs() method.
	 *
	 * @param game
	 *            The game that is to be saved.
	 * @throws JAXBException
	 */
	public static void serialize(GameState game) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(new Class[] {
				GameState.class, AreaPointer.class, BuildingAnchorTile.class,
				BuildingTile.class, CaveAnchorTile.class,
				CaveEntranceTile.class, CaveTile.class, ChestTile.class,
				DoorTile.class, GroundTile.class, PortalTile.class,
				TreeTile.class, PositionPointer.class });
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to System.out
		//m.marshal(game, System.out);

		// Write to File
		File file = null;
		if (game.getLoadedFile() != null) {
			file = new File(game.getLoadedFile());
			save(m, game, file);
		} else {
			saveAs(m, game);
		}

	}

	/**
	 * Save the game to a new file that is created by user.
	 *
	 * @param game
	 *            The game that is to be saved.
	 * @throws JAXBException
	 */
	public static void serializeAs(GameState game) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(new Class[] {
				GameState.class, AreaPointer.class });
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to System.out
		m.marshal(game, System.out);

		saveAs(m, game);
	}

	/*
	 * ===================================================================
	 * Helper methods below.
	 * ===================================================================
	 */

	// marshall the given game to the given file
	private static void save(Marshaller marshaller, GameState game, File file)
			throws JAXBException {
		marshaller.marshal(game, file);
		Messages.saveSuccessNotice(file);
	}

	// opens up a dialog ask user for file name. Then use save method to save
	private static void saveAs(Marshaller marshaller, GameState game)
			throws JAXBException {
		String fileName = null;

		// looping until user has input a valid file name
		do {
			fileName = JOptionPane.showInputDialog(null,
					"Save as: (Please enter the file name without extension)",
					"newGame");
			if (fileName == null) {
				return;
			}

			if (!isValidName(fileName)) {
				Messages.invalidNameWarning(fileName);
			}
		} while (!isValidName(fileName));

		// create the directory games/ if not exist.
		new File("games/").mkdirs();

		String fileNameXml = fileName + ".xml";

		// the file that is to be saved
		File toSave = new File(GAMES_PATH + fileNameXml);

		if (toSave.exists()) {
			if (Messages.fileExistWarning(fileName) == Messages.OVERWRITE) {
				save(marshaller, game, new File(GAMES_PATH + fileNameXml));
			}
		} else {
			save(marshaller, game, new File(GAMES_PATH + fileNameXml));
		}
	}

	// return true if the given name is a valid Linux file name
	private static boolean isValidName(String fileName) {
		return !fileName.contains("/") && !fileName.isEmpty()
				&& fileName != null && fileName.length() <= 255;
	}

}
