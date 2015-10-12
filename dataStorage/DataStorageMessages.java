package dataStorage;

import java.io.File;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Provide GUI messages for user when using saving functions.
 *
 * @author Shenbo Xuan 300259386
 *
 */
public class DataStorageMessages {

	/**
	 * Represents user's decision to overwrite a file
	 */
	public static final int OVERWRITE = 1;

	/**
	 * Represents user's decision to not to overwrite a file
	 */
	public static final int SKIP = 0;

	/**
	 * Pop up a dialog that tells the user the file name is not a valid Unix
	 * file name
	 *
	 * @param fileName
	 */
	public static void invalidNameWarning(String fileName) {
		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel("File name: " + fileName
				+ " is not a valid name.");
		panel.add(label1);
		JOptionPane.showOptionDialog(null, panel, "Invalid file name.",
				JOptionPane.NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[0]);
	}

	/**
	 * Pop up a dialog that tells the user that the given file name exists in
	 * the directory
	 *
	 * @param fileName
	 * @return
	 */
	public static int fileExistWarning(String fileName) {
		String[] options = { "YES", "No", "Cancel" };
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel("File " + fileName + ".xml exists.");
		JLabel label2 = new JLabel("Do you wish to overwrite it?");
		panel.add(label1);
		panel.add(label2);
		int selectedOption = JOptionPane.showOptionDialog(null, panel,
				"File exist.", JOptionPane.NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

		return selectedOption == 0 ? OVERWRITE : SKIP;
	}

	/**
	 * Pop up a dialog that tells the user that the game has been saved
	 * successfully
	 *
	 * @param file
	 */
	public static void saveSuccessNotice(File file) {
		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel label = new JLabel("The game is successfully saved to "
				+ file.getPath());
		panel.add(label);
		JOptionPane.showOptionDialog(null, panel, "Saved Successfully.",
				JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				options, options[0]);
	}
}
