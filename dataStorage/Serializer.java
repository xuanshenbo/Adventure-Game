package dataStorage;

import java.io.File;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import dataStorage.pointers.AreaPointer;
import model.state.GameState;

/**
 * Provide static save methods for the game. It uses the binding method to save
 * the state of the game.
 *
 * @author Shenbo Xuan 300259386
 *
 */
public class Serializer {

	private static final String GAMES_PATH = "games/";

	private static final int OVERWRITE = 1;
	private static final int SKIP = 0;

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

	private static void save(Marshaller marshaller, GameState game, File file)
			throws JAXBException {
		marshaller.marshal(game, file);
	}

	private static void saveAs(Marshaller marshaller, GameState game)
			throws JAXBException {
		String fileName = null;
		do {
			fileName = JOptionPane.showInputDialog(null,
					"Save as: (Please enter the file name without extension)");
			if (fileName == null) {
				return;
			}

			if (!isValidName(fileName)) {
				invalidNameWarning(fileName);
			}
		} while (!isValidName(fileName));

		// create the directory games/ if not exist.
		new File("games/").mkdirs();

		String fileNameXml = fileName + ".xml";

		File toSave = new File(GAMES_PATH + fileNameXml);

		if (toSave.exists()) {
			if (fileExistWarning(fileName) == OVERWRITE) {
				save(marshaller, game, new File(GAMES_PATH + fileNameXml));
			} else {
				return;
			}
		}

		save(marshaller, game, new File(GAMES_PATH + fileNameXml));
	}

	private static void invalidNameWarning(String fileName) {
		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel("File name: " + fileName
				+ " is not a valid name.");
		panel.add(label1);
		JOptionPane.showOptionDialog(null, panel, "Invalid file name.",
				JOptionPane.NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[0]);
	}

	private static boolean isValidName(String fileName) {
		// Linux valid file names should not contain '/' or '\0' and should be
		// less than 255 chars long
		return !fileName.contains("/") && !fileName.isEmpty()
				&& fileName != null && fileName.length() <= 255;
	}

	private static int fileExistWarning(String fileName) {
		String[] options = { "YES", "No", "Cancel" };
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel("File " + fileName + " exist.");
		JLabel label2 = new JLabel("Do you wish to overwrite it?");
		panel.add(label1);
		panel.add(label2);
		int selectedOption = JOptionPane.showOptionDialog(null, panel,
				"File exist.", JOptionPane.NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

		return selectedOption == 0 ? OVERWRITE : SKIP;
	}
}
