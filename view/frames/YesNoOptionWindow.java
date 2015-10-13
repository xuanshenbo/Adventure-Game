package view.frames;

import interpreter.Translator.Command;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.styledComponents.HappinessButton;
import view.styledComponents.HappinessLabel;

/**
 * This creates an option to the user of confirming, or
 * cancelling. The buttons and labels conform to the game theme
 * @author flanagdonn
 *
 */
public class YesNoOptionWindow extends JFrame{

	private HappinessButton yes;
	private HappinessButton no;
	private JPanel buttons;
	private JPanel message;

	private String msg = "";

	private Command state;

	private WelcomeFrame welcomeFrame;

	private GameFrame gameFrame;

	/**
	 * This creates a window connected to a GameFrame
	 * @param cmd The command from the frame
	 * @param gFrame The GameFrame connected to this window.
	 * @param title The title of the frame
	 */
	public YesNoOptionWindow(Command cmd, GameFrame gFrame, String title){
		super(title);

		state = cmd;

		gameFrame = gFrame;

		setUpWindow();

		displayWindow();

	}

	/**
	 * This creates a window connected to a WelcomeFrame
	 * @param cmd The command from the frame
	 * @param wFrame The WelcomeFrame connected to this window.
	 * @param title The title of the frame
	 */
	public YesNoOptionWindow(Command cmd, WelcomeFrame wFrame, String title){
		super(title);

		state = cmd;

		welcomeFrame = wFrame;

		setUpWindow();

		displayWindow();

	}

	private void displayWindow() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private void setUpWindow() {
		setLayout(new BorderLayout());

		addYesNoButtons();

		addMessageLabel();

		add(message, BorderLayout.NORTH);

		add(buttons, BorderLayout.CENTER);

	}

	private void addMessageLabel() {

		if(state.equals(Command.EXIT)){
			msg = "Are you sure you want to exit?";
		}

		HappinessLabel messageToDisplay = new HappinessLabel(msg);

		message = new JPanel();
		message.add(messageToDisplay);
	}

	private void addYesNoButtons() {

		buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));

		yes = new HappinessButton("OK");
		no = new HappinessButton("Cancel");

		ActionListener yesnoListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == yes){
					if(welcomeFrame != null){
						welcomeFrame.yesSelected(state, true);
					}
					else{
						gameFrame.exitSelected(state, true);
					}
				}
				else{
					if(welcomeFrame != null){
						welcomeFrame.yesSelected(state, false);
					}
					else{
						gameFrame.exitSelected(state, false);
					}
				}

				setVisible(false);

			}
		};

		yes.addActionListener(yesnoListener);
		no.addActionListener(yesnoListener);

		buttons.add(Box.createRigidArea(new Dimension(GameFrame.BUTTON_PADDING_HORIZONTAL,0)));
		buttons.add(yes);

		//pad between buttons
		int buttonpadding = centerButtonsOnPanel(yes, no);
		buttons.add(Box.createRigidArea(new Dimension(GameFrame.BUTTON_PADDING_HORIZONTAL,0)));
		buttons.add(no);

	}

	private int centerButtonsOnPanel(HappinessButton... buttons) {
		int size = 0;

		for(HappinessButton b: buttons){
			//add the width of this button to the total size
			size += b.getPreferredSize().width;
		}

		int panelWidth = this.getPreferredSize().width;

		int padding = (panelWidth - size) /4;

		return padding;

	}

}
