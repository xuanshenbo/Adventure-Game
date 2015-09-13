package GUI;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		//gameFrame = bFrame;
		JMenu menu = new JMenu("File");
		JMenuItem exit = new JMenuItem("Exit");
		ActionListener exitListener = new ActionListener(){

			//prompt the user if they choose exit, to ensure they want to exit the program
			public void actionPerformed(ActionEvent e) {
				String ObjButtons[] = {"Yes","No"};
				int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Adventure Game",
						JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
				if(PromptResult==JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		};
		exit.addActionListener(exitListener);
		menu.add(exit);
		add(menu);
	}

}

