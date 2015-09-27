package GUI;


import interpreter.StrategyInterpreter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Border;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;

/**
 * A panel to store the button options
 * @author flanagdonn
 *
 */
public class ButtonPanel extends JPanel {


	private int height = 100;
	private int width = 300;
	private JButton inventory;
	private JButton team;
	private JButton exchange;
	private GameFrame containerFrame;
	private StrategyInterpreter buttonInterpreter;
	/**
	 * The constructor stores the button interpreter to a field
	 * @param container
	 * @param boxLayout2
	 */
	public ButtonPanel(GameFrame container, StrategyInterpreter b, String state){
		buttonInterpreter = b;
		containerFrame = container;
		//make buttons layout top to bottom
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(boxLayout);

		if(state.equals("main")){
			if(containerFrame!=null){
				CreateMainButtons();
			}
			else{
				throw new IllegalArgumentException("The GameFrame hasn't been stored by the ButtonPanel");
			}
		}

		else if(state.equals("serverClient")){
			createServerClientButtons();
		}


	}
	private void createServerClientButtons() {
		// TODO Auto-generated method stub

	}
	private void CreateMainButtons() {
		inventory = new JButton("Inventory");
		inventory.setMnemonic(KeyEvent.VK_I);
		inventory.setToolTipText("Display your inventory");
		inventory.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				containerFrame.addInventoryDialog();
			}
		});


		team = new JButton("Team");
		team.setMnemonic(KeyEvent.VK_T);
		team.setToolTipText("Display your team");
		team.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(containerFrame,
						"You have NO team",
						"Sorry :(",
						JOptionPane.WARNING_MESSAGE);
			}
		});


		exchange = new JButton("Exchange");
		exchange.setMnemonic(KeyEvent.VK_E);
		exchange.setToolTipText("Exchange an item");
		exchange.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(containerFrame,
						"You have NO items to display",
						"Idiot!",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		//makePretty(inventory, team, exchange);

		add(Box.createRigidArea(new Dimension(containerFrame.buttonPaddingHorizontal,0))); //pad between buttons
		add(inventory);
		add(Box.createRigidArea(new Dimension(containerFrame.buttonPaddingHorizontal,0))); //pad between buttons
		add(team);
		add(Box.createRigidArea(new Dimension(containerFrame.buttonPaddingHorizontal,0))); //pad between buttons
		add(exchange);

	}

}
