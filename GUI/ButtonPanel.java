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

	private WelcomeDialog welcomeDialog;
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


		if(state.equals("main")){
			if(containerFrame!=null){
				BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);	//display main game-play buttons horizontally
				setLayout(boxLayout);
				CreateMainButtons();
			}
			else{
				throw new IllegalArgumentException("The GameFrame hasn't been stored by the ButtonPanel");
			}
		}
	}

	/**
	 * Displays the button choices for playing as a client or playing as a client + server
	 * @param i An initialisation object, which implements StrategyInterpreter
	 * @param welcomeDialog The Dialog which needs to be informed of any choice that is made
	 * @param state Which buttons are to be displayed?
	 */
	public ButtonPanel(WelcomeDialog welcomeDialog, String state) { //should take an Initialisation object too.
		//TODO Initialisation object extends StrategyInterpreter?

		this.welcomeDialog = welcomeDialog;

		//display server or server+client buttons
		if(state.equals("serverClient")){
			BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS); //display client server buttons vertically
			setLayout(boxLayout);
			createServerClientButtons();
		}

		//display option to load a game or start a new game
		else if(state.equals("loadnew")){

		}
	}

	/**
	 * Adds two buttons to the panel: A button to play as a Client, and
	 * another to play as Server/Client.
	 */
	private void createServerClientButtons() {
		final JButton client = new JButton("Client");
		client.setMnemonic(KeyEvent.VK_C);
		client.setToolTipText("Play as a client");


		JButton serverclient = new JButton("Server + Client");
		serverclient.setMnemonic(KeyEvent.VK_S);
		serverclient.setToolTipText("Play as a client");


		ActionListener serverclientListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==client){
					buttonInterpreter.notify("client");
					welcomeDialog.displayNext("connect");	//now display the option for which server to connect to
				}
				else{
					buttonInterpreter.notify("clientserver");
					welcomeDialog.displayNext("loadNew"); //now display the options of loading a game, or starting a new one
				}

			}

		};

		client.addActionListener(serverclientListener);
		serverclient.addActionListener(serverclientListener);

		add(Box.createRigidArea(new Dimension(containerFrame.buttonPaddingVertical,0))); //pad between buttons
		add(client);
		add(Box.createRigidArea(new Dimension(containerFrame.buttonPaddingVertical,0))); //pad between buttons
		add(serverclient);

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
