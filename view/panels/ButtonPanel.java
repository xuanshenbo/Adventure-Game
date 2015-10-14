package view.panels;

import interpreter.StrategyInterpreter;
import interpreter.Translator.Command;
import interpreter.Translator.InitialisationCommand;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import view.frames.Dialog;
import view.frames.GameFrame;
import view.frames.MessageWindow;
import view.styledComponents.HappinessButton;
import main.Initialisation;

/**
 * A panel to store the button options
 *
 * @author flanagdonn
 */
public class ButtonPanel extends JPanel {

	private HappinessButton inventory;
	private HappinessButton team;
	private GameFrame gameFrame;

	private HappinessButton loadGame = new HappinessButton("Load saved game");
	private HappinessButton newGame = new HappinessButton("Start new game");

	private HappinessButton loadPlayer = new HappinessButton("Load saved player");
	private HappinessButton createPlayer = new HappinessButton("Create new player");

	private HappinessButton client = new HappinessButton("Client");
	private HappinessButton serverclient = new HappinessButton("Server + Client");

	private StrategyInterpreter initialisation;

	private WelcomePanel welcomePanel;
	private StrategyInterpreter buttonInterpreter;
	private Dialog dialog;


	/**
	 * The constructor stores the button interpreter to a field
	 * @param container The container for this panel
	 * @param interpreter The interpreter for communicating user actions
	 * @param state The state of the game
	 */
	public ButtonPanel(GameFrame container, StrategyInterpreter interpreter,
			Command state) {
		buttonInterpreter = interpreter;
		gameFrame = container;

		// make buttons layout top to bottom
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(boxLayout);

		if (state.equals(Command.MAIN)) {
			if (gameFrame != null) {
				CreateMainButtons();
			} else {
				throw new IllegalArgumentException(
						"The GameFrame hasn't been stored by the ButtonPanel");
			}
		}

	}

	/**
	 * Displays the sequence of button choices after and including playing as a
	 * client or playing as a client + server
	 *
	 * @param i
	 *            An initialisation object, which implements StrategyInterpreter
	 * @param welcomeDialog
	 *            The Dialog which needs to be informed of any choice that is
	 *            made
	 * @param state
	 *            Which buttons are to be displayed?
	 */
	public ButtonPanel(WelcomePanel welcomeDialog,
			InitialisationCommand state, Initialisation i) {

		setOpaque(false);

		this.welcomePanel = welcomeDialog;

		this.initialisation = i;

		// display options vertically
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(boxLayout);

		// display server or server+client buttons
		if (state
				.equals(InitialisationCommand.SHOW_CLIENT_SERVER_OPTION)) {
			createServerClientButtons();
		}

		// display option to load a game or start a new game
		else if (state
				.equals(InitialisationCommand.SHOW_LOAD_OR_NEW_OPTION)) {
			addLoadNewButtons();
		}

		// display option to either load a player or create a new player
		else if (state
				.equals(InitialisationCommand.LOAD_PLAYER_OR_CREATE_NEW_PLAYER)) {
			addLoadCreatePlayerButtons();
		}

	}

	/**
	 * This constructor is used to display the options for
	 * interacting with a container or inventory
	 * @param state The state of the game
	 * @param buttonInterp The interpreter to notify of user actions
	 * @param d The dialog on which this panel lies.
	 */
	public ButtonPanel(Command state,
			StrategyInterpreter buttonInterp, Dialog d, GameFrame g) {
		this.gameFrame = g;

		this.dialog = d;

		this.buttonInterpreter = buttonInterp;

		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(boxLayout);

		if (state.equals(Command.DISPLAY_INVENTORY_ITEM_OPTIONS)) {
			displayInventoryItemOptions();
		}

		else if (state
				.equals(Command.DISPLAY_CONTAINER_ITEM_OPTIONS)) {
			displayContainerItemOptions();
		}

	}

	// should only appear when something has been selected
	private void displayContainerItemOptions() {

		final HappinessButton move = new HappinessButton("Move to Inventory");
		move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == move) {
					try {
						buttonInterpreter.notify(Command.MOVE_ITEM_TO_INVENTORY
								.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					dialog.dispose();
				}
			}
		});

		add(move);
	}

	private void displayInventoryItemOptions() {
		final HappinessButton drop = new HappinessButton("Drop");
		final HappinessButton use = new HappinessButton("Use");
		final HappinessButton moveToBag = new HappinessButton("Move item");

		ActionListener itemActionListener = new ActionListener() {
			private boolean movePressed = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = "";
				if (e.getSource() == drop) {
					msg = Command.DROP.toString();
					dialog.dispose(); // no longer display inventory

				} else if (e.getSource() == use) {
					msg = Command.USE.toString();
					dialog.dispose(); // no longer display inventory

				} else if (e.getSource() == moveToBag) {
					msg = Command.MOVE_ITEM.toString();
					/*
					 * we know something is selected at this point, because
					 *  the drop/use/move options only appear after something is selected
					 *  only display dialog if this is the first time move
					 *  item has been selected
					 */

					if (!movePressed) {

						//make the dialog invisible, so can display message window
						dialog.setVisible(false);

						//Create and display a message window
						MessageWindow window = new MessageWindow("Now select where to move the item", dialog, gameFrame);
						//change button text
						moveToBag.setText("Move to this slot");
						movePressed = true;

					} else {

						//change button text
						moveToBag.setText("Move item to bag");
						movePressed = false;
					}
				}

				//send the message to the interpreter
				try {
					buttonInterpreter.notify(msg);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		};

		drop.addActionListener(itemActionListener);
		moveToBag.addActionListener(itemActionListener);
		use.addActionListener(itemActionListener);

		add(drop);
		add(use);
		add(moveToBag);
	}

	private void addLoadCreatePlayerButtons() {
		ActionListener loadcreate = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == loadPlayer) {
					try {
						initialisation
						.notify(InitialisationCommand.LOAD_SAVED_PLAYER
								.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else if (e.getSource() == createPlayer) {
					try {
						initialisation
						.notify(InitialisationCommand.CREATE_NEW_PLAYER
								.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		};

		loadPlayer.addActionListener(loadcreate);
		createPlayer.addActionListener(loadcreate);

		removeAllButtons();

		/*
		 * Add the buttons with padding
		 */
		add(Box.createRigidArea(new Dimension(GameFrame.BUTTON_PADDING_VERTICAL, 0)));
		add(loadPlayer);
		add(Box.createRigidArea(new Dimension(GameFrame.BUTTON_PADDING_VERTICAL,0)));
		add(createPlayer);

		setVisible(true);

		repaint();

	}

	private int centerButtonsOnPanel(HappinessButton... buttons) {
		int size = 0;

		for (HappinessButton b : buttons) {
			// add the width of this button to the total size
			size += b.getPreferredSize().width;
		}

		int panelWidth = welcomePanel.getPreferredSize().width;

		int padding = (panelWidth - size) / 4;

		return padding;

	}

	private void addLoadNewButtons() {

		ActionListener loadnewListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = "";
				if (e.getSource() == loadGame) {
					msg = InitialisationCommand.LOAD_GAME.toString();


				} else if (e.getSource() == newGame) {
					msg = InitialisationCommand.SELECTED_NEW_GAME.toString();
				}

				try {
					initialisation.notify(msg);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		};

		loadGame.addActionListener(loadnewListener);
		newGame.addActionListener(loadnewListener);

		removeAllButtons();

		int extraPadding = centerButtonsOnPanel(loadGame, newGame);

		add(Box.createRigidArea(new Dimension(extraPadding, 0)));
		add(loadGame);
		add(Box.createRigidArea(new Dimension(extraPadding, 0)));
		add(newGame);

		setVisible(true);

		repaint();

	}

	/*
	 * Adds two buttons to the panel: A button to play as a Client, and another
	 * to play as Server/Client.
	 */
	private void createServerClientButtons() {

		client.setMnemonic(KeyEvent.VK_C);
		client.setToolTipText("Play as a client");

		serverclient.setMnemonic(KeyEvent.VK_S);
		serverclient.setToolTipText("Play as a server + client");

		ActionListener serverclientListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = "";
				if (e.getSource() == client) {
					msg = InitialisationCommand.SELECTED_CLIENT
							.toString();
				} else if (e.getSource() == serverclient) {
					msg = InitialisationCommand.SELECTED_CLIENT_AND_SERVER
							.toString();
				}

				try {
					initialisation.notify(msg);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};

		client.addActionListener(serverclientListener);
		serverclient.addActionListener(serverclientListener);

		int extraPadding = centerButtonsOnPanel(client, serverclient);

		removeAllButtons();

		/*
		 * Add buttons with padding
		 */
		add(Box.createRigidArea(new Dimension(extraPadding, 0)));
		add(client);
		add(Box.createRigidArea(new Dimension(extraPadding, 0)));
		add(serverclient);

	}

	private void removeAllButtons() {
		remove(client);
		remove(serverclient);
		remove(loadGame);
		remove(newGame);
	}

	private void CreateMainButtons() {
		inventory = new HappinessButton("Inventory");
		inventory.setMnemonic(KeyEvent.VK_I);
		inventory.setToolTipText("Display your inventory");
		inventory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buttonInterpreter
					.notify(Command.DISPLAY_INVENTORY
							.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		team = new HappinessButton("Team");
		team.setMnemonic(KeyEvent.VK_T);
		team.setToolTipText("Display your team");
		team.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String teamMember1 = gameFrame.getTeamMember1();
				String teamMember2 = gameFrame.getTeamMember2();

				MessageWindow notifyUser = new MessageWindow("Your team includes " + teamMember1 + " and "
						+ teamMember2, null, gameFrame);

			}
		});

		/*
		 * Add buttons with padding
		 */
		add(Box.createRigidArea(new Dimension(
				GameFrame.BUTTON_PADDING_HORIZONTAL, 0)));
		add(inventory);
		add(Box.createRigidArea(new Dimension(
				GameFrame.BUTTON_PADDING_HORIZONTAL, 0)));
		add(team);

		revalidate();

	}



}
