package GUI;

import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_L;
import static javax.swing.KeyStroke.getKeyStroke;

import interpreter.StrategyInterpreter;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * A simple Menubar with an Exit option
 */
public class MenuBar extends JMenuBar {

	private Action exitAction;
	private Action saveAction;
	private Action loadAction;

	private StrategyInterpreter menuInterpreter;

	/**
	 * Creates a simple Menubar with an Exit option and adds an ActionListener
	 * which exits the program
	 */
	public MenuBar(StrategyInterpreter interp) {

		exitAction = new ExitAction();
		saveAction = new SaveAction();
		loadAction = new LoadAction();

		this.menuInterpreter = interp;

		// create a File menu
		JMenu menu = new JMenu("File");

		// add an Exit option to the file menu
		final JMenuItem exit = new JMenuItem(exitAction);

		// add Save option to the file menu
		final JMenuItem save = new JMenuItem(saveAction);

		// add Load option to the file menu
		final JMenuItem load = new JMenuItem(loadAction);

		menu.setMnemonic('F');

		menu.add(exit);
		menu.addSeparator();
		menu.add(save);
		menu.addSeparator();
		menu.add(load);

		add(menu);
	}

	private void doExit() {
		String ObjButtons[] = { "Yes", "No" };
		int PromptResult = JOptionPane.showOptionDialog(null,
				"Are you sure you want to exit?", "Adventure Game",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				ObjButtons, ObjButtons[1]);
		if (PromptResult == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	private void doSave() {
		try {
			System.out.println(menuInterpreter);
			menuInterpreter.notify("save");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doLoad() {
		// Create a file chooser
		final JFileChooser fc = new JFileChooser();

		int returnVal = fc.showOpenDialog(MenuBar.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// notify interpreter to open file
		}
	}

	private class ExitAction extends AbstractAction {
		public ExitAction() {
			super("Exit");
			putValue(MNEMONIC_KEY, VK_E);
			putValue(ACCELERATOR_KEY, getKeyStroke("ctrl Q"));
		}

		public void actionPerformed(ActionEvent evt) {
			doExit();
		}
	}

	private class SaveAction extends AbstractAction {
		public SaveAction() {
			super("Save");
			putValue(MNEMONIC_KEY, VK_S);
			putValue(ACCELERATOR_KEY, getKeyStroke("ctrl S"));
		}

		public void actionPerformed(ActionEvent evt) {
			doSave();
		}
	}

	private class LoadAction extends AbstractAction {
		public LoadAction() {
			super("Load");
			putValue(MNEMONIC_KEY, VK_L);
			putValue(ACCELERATOR_KEY, getKeyStroke("ctrl L"));
		}

		public void actionPerformed(ActionEvent evt) {
			doLoad();
		}
	}

}

