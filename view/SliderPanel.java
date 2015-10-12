package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Initialisation;

/**
 * This JPanel is used to display the Sliders and JLabels used in the WelcomePanel.
 * @author flanagdonn
 *
 */
public class SliderPanel extends JPanel {

	private Dimension sliderPaddingVertical= new Dimension(0, 20);

	private WelcomePanel welcome;

	//The four sliders for changing game parameters
	private JSlider gameObjectDensity;
	private JSlider difficulty;
	private JSlider height;
	private JSlider width;

	//OK button used when user finished selecting
	private JButton confirm;

	//labels to go with the sliders, indicating what the sliders are for.
	private JLabel densityLabel;
	private JLabel difficultyLabel;
	private JLabel gameHeightLabel;
	private JLabel gameWidthLabel;

	//The font to be used on the jlabels
	private Font labelFont = new Font("Serif", Font.BOLD, 12);

	/**
	 * The constructor sets up all the sliders and labels and adds them to the panel
	 * @param welcomePanel The welcome panel which this panel will be added to.
	 */
	public SliderPanel(WelcomePanel welcomePanel){
		this.welcome = welcomePanel;

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		//create the sliders with appropriate padding etc
		setUpSliders();

		//set up the change listener for the sliders
		ChangeListener sliderListener = new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					if(source == gameObjectDensity){
						welcome.setDensity(gameObjectDensity.getValue());
					}
					else if(source == difficulty){
						welcome.setDifficultyLevel(difficulty.getValue());
					}
					else if(source == height){
						welcome.setGameHeight(height.getValue());
					}
					else if(source == width){
						welcome.setGameWidth(width.getValue());
					}
				}

			}
		};

		gameObjectDensity.addChangeListener(sliderListener);
		difficulty.addChangeListener(sliderListener);
		width.addChangeListener(sliderListener);
		height.addChangeListener(sliderListener);

		//add a button for when the user is finished adjusting levels
		createConfirmButton();
		//add jlabels to indicate what each slider is for
		addJLabelsForSliders();

		//add each label and corresponding slider to the panel
		add(densityLabel);
		add(gameObjectDensity);

		add(difficultyLabel);
		add(difficulty);

		add(gameHeightLabel);
		add(height);

		add(gameWidthLabel);
		add(width);

		//add the confirm button to the panel
		add(confirm);

	}

	/*
	 * Create labels for the sliders, and create vertical space to make them more readable
	 */
	private void addJLabelsForSliders() {
		densityLabel = new JLabel("Choose the density of the game ie the number of objects from 0% to 100%");
		densityLabel.add(Box.createRigidArea(sliderPaddingVertical));
		densityLabel.setFont(labelFont);
		densityLabel.setForeground(GameFrame.BUTTON_FONT_COLOR);

		difficultyLabel = new JLabel("Choose the difficulty");
		difficultyLabel.add(Box.createRigidArea(sliderPaddingVertical));
		difficultyLabel.setFont(labelFont);
		difficultyLabel.setForeground(GameFrame.BUTTON_FONT_COLOR);

		gameHeightLabel = new JLabel("Choose the Game height");
		gameHeightLabel.add(Box.createRigidArea(sliderPaddingVertical));
		gameHeightLabel.setFont(labelFont);
		gameHeightLabel.setForeground(GameFrame.BUTTON_FONT_COLOR);

		gameWidthLabel = new JLabel("Choose the Game width");
		gameWidthLabel.add(Box.createRigidArea(sliderPaddingVertical));
		gameWidthLabel.setFont(labelFont);
		gameWidthLabel.setForeground(GameFrame.BUTTON_FONT_COLOR);


	}

	private void createConfirmButton() {
		confirm = new JButton("OK");
		confirm.add(Box.createRigidArea(new Dimension(welcome.getPreferredSize().width,20))); //centre confirm the button
		ButtonPanel.makeButtonsPretty(confirm);

		confirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				welcome.notifyParameters();
				welcome.removeSliderPanel();
			}

		});
	}

	private void setUpSliders() {
		/*
		 * Create sliders and add vertical space for readability
		 */
		int tickSpacingDensity = 5, tickSpacingDifficulty = 1, tickSpacingWidth = 5, tickSpacingHeight = 5;

		//density of game world
		gameObjectDensity = new JSlider(JSlider.HORIZONTAL, 0, Initialisation.maxTrees, Initialisation.maxTrees/2);
		gameObjectDensity.add(Box.createRigidArea(sliderPaddingVertical));
		gameObjectDensity.setPaintTicks(true);
		gameObjectDensity.setMajorTickSpacing(tickSpacingDensity);

		//difficulty: easy, medium or difficult
		difficulty = new JSlider(JSlider.HORIZONTAL, 0, 2, 1);
		difficulty.add(Box.createRigidArea(sliderPaddingVertical));
		difficulty.setPaintTicks(true);
		difficulty.setMajorTickSpacing(tickSpacingDifficulty);

		//height of game
		height = new JSlider(JSlider.HORIZONTAL, 10, 200, 50);
		height.add(Box.createRigidArea(sliderPaddingVertical));
		height.setPaintTicks(true);
		height.setMajorTickSpacing(tickSpacingHeight);

		//width of game
		width = new JSlider(JSlider.HORIZONTAL, 10, 200, 50);
		width.add(Box.createRigidArea(sliderPaddingVertical));
		width.setPaintTicks(true);
		width.setMajorTickSpacing(tickSpacingWidth);



	}
}