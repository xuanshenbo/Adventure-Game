package dataStorage;

import java.io.File;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import dataStorage.pointers.*;
import model.state.GameState;
import model.tiles.*;

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

		JAXBContext context = JAXBContext.newInstance(new Class[] {
				GameState.class, AreaPointer.class, BuildingAnchorTile.class,
				BuildingTile.class, CaveAnchorTile.class,
				CaveEntranceTile.class, CaveTile.class, ChestTile.class,
				DoorTile.class, GroundTile.class, PortalTile.class,
				TreeTile.class, PositionPointer.class });

		Unmarshaller um = context.createUnmarshaller();
		
		GameState gs = (GameState) um.unmarshal(file);

		GameState gameState = new GameState(gs.getWorld(), gs.getPlayerList());
		gameState.setTime(gs.getTime());
		gameState.setDay(gs.getDay());
		gameState.getWorld().setGameState(gameState);

		return gameState;

	}

	// get a file from user
	private static File getFile() {
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
