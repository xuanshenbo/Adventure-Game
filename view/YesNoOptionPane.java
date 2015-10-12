package view;

import interpreter.Translator.Command;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * TODO refactor the two constructors
 * @author flanagdonn
 *
 */
public class YesNoOptionPane extends JFrame{

	private JButton yes;
	private JButton no;
	private JPanel buttons;
	private JPanel message;

	private String msg = "";

	private Command state;

	private WelcomeFrame welcomeFrame;

	private GameFrame gameFrame;

	public YesNoOptionPane(Command cmd, WelcomeFrame wFrame, String title){
		super(title);

		state = cmd;

		welcomeFrame = wFrame;

		setLayout(new BorderLayout());

		buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));

		yes = new JButton("OK");
		no = new JButton("Cancel");

		ActionListener yesnoListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == yes){
					welcomeFrame.yesSelected(state, true);
				}
				else{
					welcomeFrame.yesSelected(state, false);
				}

				setVisible(false);

			}
		};

		yes.addActionListener(yesnoListener);
		no.addActionListener(yesnoListener);

		buttons.add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingHorizontal,0)));
		buttons.add(yes);
		//pad between buttons
		int buttonpadding = centerButtonsOnPanel(yes, no);
		buttons.add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingHorizontal,0)));
		buttons.add(no);

		ButtonPanel.makeButtonsPretty(yes, no);

		if(state.equals(Command.EXIT)){
			msg = "Are you sure you want to exit?";
		}

		JLabel messageToDisplay = new JLabel(msg);

		ButtonPanel.makeLabelPretty(messageToDisplay);

		message = new JPanel();
		message.add(messageToDisplay);
		//message.revalidate();

		add(message, BorderLayout.NORTH);

		add(buttons, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public YesNoOptionPane(Command cmd, GameFrame gFrame, String title){
		super(title);

		state = cmd;

		gameFrame = gFrame;

		setLayout(new BorderLayout());

		buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));

		yes = new JButton("OK");
		no = new JButton("Cancel");

		ActionListener yesnoListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == yes){
					gameFrame.yesSelected(state, true);
				}
				else{
					gameFrame.yesSelected(state, false);
				}

				setVisible(false);

			}
		};

		yes.addActionListener(yesnoListener);
		no.addActionListener(yesnoListener);

		buttons.add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingHorizontal,0)));
		buttons.add(yes);
		//pad between buttons
		int buttonpadding = centerButtonsOnPanel(yes, no);
		buttons.add(Box.createRigidArea(new Dimension(GameFrame.buttonPaddingHorizontal,0)));
		buttons.add(no);

		ButtonPanel.makeButtonsPretty(yes, no);

		if(state.equals(Command.EXIT)){
			msg = "Are you sure you want to exit?";
		}

		JLabel messageToDisplay = new JLabel(msg);

		ButtonPanel.makeLabelPretty(messageToDisplay);

		message = new JPanel();
		message.add(messageToDisplay);
		//message.revalidate();

		add(message, BorderLayout.NORTH);

		add(buttons, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private int centerButtonsOnPanel(JButton... buttons) {
		int size = 0;

		for(JButton b: buttons){
			//add the width of this button to the total size
			size += b.getPreferredSize().width;
		}

		int panelWidth = this.getPreferredSize().width;

		int padding = (panelWidth - size) /4;

		return padding;

	}

}
