package view;

import static java.awt.event.KeyEvent.VK_A;
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

	private Action saveAction;
	private Action saveAsAction;
	private Action loadAction;
	private Action exitAction;

	private StrategyInterpreter menuInterpreter;

	/**
	 * Creates a simple Menubar with an Exit option and adds an ActionListener
	 * which exits the program
	 */
	public MenuBar(StrategyInterpreter interp) {

		saveAction = new SaveAction();
		saveAsAction = new SaveAsAction();
		loadAction = new LoadAction();
		exitAction = new ExitAction();

		this.menuInterpreter = interp;

		// create a File menu
		JMenu menu = new JMenu("File");

		// add an Exit option to the file menu
		final JMenuItem exit = new JMenuItem(exitAction);

		// add Save option to the file menu
		final JMenuItem save = new JMenuItem(saveAction);

		// add Save as option to the file menu
		final JMenuItem saveAs = new JMenuItem(saveAsAction);

		// add Load option to the file menu
		final JMenuItem load = new JMenuItem(loadAction);

		menu.setMnemonic('F');

		menu.add(save);
		menu.add(saveAs);
		menu.add(load);
		menu.addSeparator();
		menu.add(exit);

		add(menu);
	}

	// action on exit
	private void doExit() {
		String ObjButtons[] = { "Yes", "No" };
		int PromptResult = JOptionPane.showOptionDialog(null,
				"Are you sure you want to exit?", "Adventure Game",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				ObjButtons, ObjButtons[1]);
		if (PromptResult == JOptionPane.YES_OPTION) {
			try {
				menuInterpreter.notify("exit");
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

	// aciton on save
	private void doSave() {
		try {
			menuInterpreter.notify("save");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// action on saveAs
	private void doSaveAs() {
		try {
			menuInterpreter.notify("saveAs");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// action on laod
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

		@Override
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

		@Override
		public void actionPerformed(ActionEvent evt) {
			doSave();
		}
	}

	private class SaveAsAction extends AbstractAction {
		public SaveAsAction() {
			super("Save as");
			putValue(MNEMONIC_KEY, VK_A);
			putValue(ACCELERATOR_KEY, getKeyStroke("ctrl A"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			doSaveAs();
		}
	}

	private class LoadAction extends AbstractAction {
		public LoadAction() {
			super("Load");
			putValue(MNEMONIC_KEY, VK_L);
			putValue(ACCELERATOR_KEY, getKeyStroke("ctrl L"));
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			doLoad();
		}
	}

}

