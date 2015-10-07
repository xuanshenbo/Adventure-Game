/*package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Initialisation;
import main.InitialisationState;
import main.Main;

*//**
 * A subclass of JDialog which welcomes a new player and invites them to choose an avatar
 *//*
public class WelcomePanelAltDONT_USE_ME extends JPanel implements ActionListener {

	private JFrame parentFrame;

	private int heading1Size = 50;
	private int heading2Size = 30;
	private GridBagConstraints buttonPanelConstraints;
	private GridBagConstraints sliderPanelConstraints;

	//the image to be displayed on the opening welcome panel
	private Image welcomeImage;

	private InputPanel iPanel;

	private Initialisation initialisation;

	private Dimension imageSize = new Dimension(850, 400);

	//static so as to be used in parent constructor
	private static String welcome = "Welcome to the Happiness Game!";

	//this will display different buttons depending on what the user needs to choose
	private ButtonPanel bPanel;

	private JPanel sliderPanel;

	//the state decides what to display
	private InitialisationState state;

	private String instructions = "If you wish to start a new game, please click OK, to choose an Avatar!";

	//how many trees the user wants
	private int density = 50;

	//0, 1 or 2 for easy, medium or hard
	private int difficultyLevel = 1;

	*//**
	 * Creates a dialog with a message, and different behaviour depending on the state
	 * @param gameFrame The parent frame of the dialog
	 * @param title The title of the Dialog to be passed to super constructor
	 * @param msg Message to display
	 * @param i The state of the Game
	 *//*
	public WelcomePanel(Initialisation i) {

		this.initialisation = i;

		this.state = InitialisationState.SHOW_CLIENT_SERVER_OPTION;

		setLayout(new GridBagLayout());

		this.parentFrame = i.getFrame();

		GridBagConstraints gc=new GridBagConstraints();
		gc.fill=GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;

		//The message is put in a panel, in case new messages will be added later
		JPanel messagePane = new JPanel();
		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.PAGE_AXIS));

		//display welcome message in appropriate size.
		JLabel welcomeMessage = new JLabel(welcome);
		welcomeMessage.setFont(new Font("Serif", Font.BOLD, heading1Size));
		welcomeMessage.setForeground(Color.pink);

		messagePane.add(welcomeMessage);

		//add(messagePane, BorderLayout.PAGE_START);
		add(messagePane, gc);

		addWelcomeImage();

		sliderPanelConstraints = new GridBagConstraints();
		sliderPanelConstraints.gridx = 0;
		sliderPanelConstraints.gridx = 10;
		sliderPanelConstraints.gridheight = 50;


		buttonPanelConstraints=new GridBagConstraints();
		buttonPanelConstraints.gridx = 0;
		buttonPanelConstraints.gridy = 10;
//		buttonPanelConstraints.gridwidth = 200;
//		buttonPanelConstraints.gridheight = 50;

		// button panel needs to store "this" to call the display next methods, and send it Initialisation too? Or Initialisation
		// has access to buttonInterpreter?
		bPanel = new ButtonPanel(this, state, initialisation);
		add(bPanel, buttonPanelConstraints);

		//display the welcome panel
		setVisible(true);
	}


	 * Add an image to the screen


	private void addWelcomeImage() {
		GridBagConstraints gc=new GridBagConstraints();
		gc.fill=GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 5;

		welcomeImage = ImageLoader.loadImage("cupcake.jpg");
		welcomeImage = welcomeImage.getScaledInstance(imageSize.width, imageSize.height, -1);

		ImageIcon icon = new ImageIcon(welcomeImage);
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		//add(thumb, BorderLayout.CENTER);
		add(thumb, gc);

	}

	public void transitionToNewState(InitialisationState state){
		bPanel.setVisible(false);
		if(state.equals(InitialisationState.SHOW_LOAD_OR_NEW_OPTION)){
			displayLoadNew();
		}
		else if(state.equals(InitialisationState.CONNECT_TO_SERVER)){
			displayConnect();
		}
		else if(state.equals(InitialisationState.LOAD_PLAYER_OR_CREATE_NEW_PLAYER)){
			displayLoadCreatePlayerOptions();
		}
		else if(state.equals(InitialisationState.LOAD_GAME)){
			loadSavedGame();
		}
		else if(state.equals(InitialisationState.CHOOSE_SLIDER_OPTIONS)){
			displaySliderOptions();
		}
		else if(state.equals(InitialisationState.LOAD_SAVED_PLAYER)){
			displayAvatarOptions(true);

		}
		else if(state.equals(InitialisationState.CREATE_NEW_PLAYER)){
			displayAvatarOptions(false);

		}
		else if(state.equals(InitialisationState.START_GAME)){
			try {
				initialisation.notify("start");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		revalidate(); //resize panel to make room for any newly added panels
		repaint();

	}

	private void displayLoadCreatePlayerOptions() {
		remove(bPanel);
		bPanel = new ButtonPanel(this, InitialisationState.LOAD_PLAYER_OR_CREATE_NEW_PLAYER, initialisation);
		add(bPanel, buttonPanelConstraints);

	}

	private void loadSavedGame() {
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();

		int returnVal = fc.showOpenDialog(WelcomePanel.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			System.out.println(file);
			try {
				initialisation.notify("open "+file);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void displaySliderOptions() {
		remove(bPanel); //move this to transition method?

		//initialise the slider panel with a vertical box layout
		sliderPanel = new JPanel();
		sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.PAGE_AXIS));

		//add sliders
		final JSlider GameObjectDensity = new JSlider(JSlider.HORIZONTAL, 0, Initialisation.maxTrees, Initialisation.maxTrees/2);
		final JSlider difficulty = new JSlider(JSlider.HORIZONTAL, 0, 2, 1);

		ChangeListener sliderListener = new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
//				System.out.println("density: "+density+" diff: "+difficultyLevel);
//				System.out.println("densityslider: "+GameObjectDensity.getValue()+" diffSlider: "+difficulty.getValue());
				if (!source.getValueIsAdjusting()) {
					if(source == GameObjectDensity){
						density = GameObjectDensity.getValue();
					}
					else if(source == difficulty){
						difficultyLevel = difficulty.getValue();
					}



					//if both values chosen, move to the next stage: choosing an avatar
					if(density != -1 && difficultyLevel != -1){
						WelcomePanel.this.remove(sliderPanel);
						transitionToNewState(InitialisationState.LOAD_PLAYER_OR_CREATE_NEW_PLAYER);
					}
				}

			}
		};

		JButton confirm = new JButton("OK");
		confirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				remove(sliderPanel);
				transitionToNewState(InitialisationState.LOAD_PLAYER_OR_CREATE_NEW_PLAYER);
			}

		});

		GameObjectDensity.addChangeListener(sliderListener);
		difficulty.addChangeListener(sliderListener);


		sliderPanel.add(new JLabel("Choose the density of the game wrt the number of objects from 0% to 100%"));
		sliderPanel.add(GameObjectDensity);

		sliderPanel.add(new JLabel("Choose the difficulty"));
		sliderPanel.add(difficulty);

		sliderPanel.add(confirm);

		add(sliderPanel, sliderPanelConstraints);

	}


	 * Displays option dialog to get user input on which server to connect to

	private void displayConnect() {
		iPanel = new InputPanel(initialisation, "connect");
		add(iPanel, buttonPanelConstraints);

		revalidate();
	}


	 * Displays option dialog to get user to decide to load a game, or start a new game
	 * TODO why is this panel not being displayed?

	private void displayLoadNew() {
		remove(bPanel);
		bPanel = new ButtonPanel(this, InitialisationState.SHOW_LOAD_OR_NEW_OPTION, initialisation);
		add(bPanel, buttonPanelConstraints);
	}


	 * Displays option dialog to get user to choose Avatar

	private void displayAvatarOptions(boolean b) {
		final boolean loadingSavedPlayer = b;
		final String loadSavedMessage = "Select the avatar associated with your Player";
		final String createNewMessage = "Choose an avatar for your Player";

		final InitialisationState create = InitialisationState.CREATE_NEW_PLAYER;
		final InitialisationState load = InitialisationState.LOAD_SAVED_PLAYER;


		JButton chooseAvatar = new JButton("Choose my Avatar");
		chooseAvatar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Dialog avatarDialog = new Dialog("Available Avatars", loadingSavedPlayer ? loadSavedMessage : createNewMessage,
						loadingSavedPlayer ? load : create, initialisation, WelcomePanel.this);

			}
		});

		remove(bPanel); //remove the previous buttons
		ButtonPanel.makeButtonPretty(chooseAvatar);
		add(chooseAvatar, buttonPanelConstraints);

	}
	*//**
	 * Called when the user clicks "Choose Avatar" on the dialog
	 *//*
	public void actionPerformed(ActionEvent e) {

	}

	*//**	 *
	 * @param c TextField to store in a field
	 *//*
	public void setTextField(JTextField c){

	}
}*/