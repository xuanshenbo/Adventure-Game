package GUI;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * A simple Menubar with an Exit option
 */
public class MenuBar extends JMenuBar {
	private GameFrame gameFrame;

	/**
	 * Creates a simple Menubar with an Exit option and adds an ActionListener which exits the program
	 */
	public MenuBar(){
		//create a File menu
		JMenu menu = new JMenu("File");

		//Create a file chooser
		final JFileChooser fc = new JFileChooser();

		//add an Exit option to the file menu
		final JMenuItem exit = new JMenuItem("Exit");

		//add Save option to the file menu
		final JMenuItem save = new JMenuItem("Save");

		//add Load option to the file menu
		final JMenuItem load = new JMenuItem("Load");


		//add a menu listener
		ActionListener menuListener = new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				//prompt the user if they choose exit, to ensure they want to exit the program
				if(e.getSource()==exit){
					String ObjButtons[] = {"Yes","No"};
					int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Adventure Game",
							JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
					if(PromptResult==JOptionPane.YES_OPTION){
						System.exit(0);
					}
				}

				//TODO implement this (Shenbo)
				else if(e.getSource()==load){
					int returnVal = fc.showOpenDialog(MenuBar.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						//notify interpreter to open file
					} else {
						//do nothing
					}
				}

				//TODO implement this (Shenbo)
				else if(e.getSource()==save){
					int returnVal = fc.showSaveDialog(MenuBar.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						//notify interpreter to save file
					} else {
						//do nothing
					}

				}
			}
		};

		exit.addActionListener(menuListener);
		save.addActionListener(menuListener);
		load.addActionListener(menuListener);

		menu.add(exit);
		//menu.add(save); this choice is now taken care of at the start
		//menu.add(load);

		add(menu);
	}

}

