package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import interpreter.Translator.Command;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MessageWindow extends JFrame {

	private String msg = "";

	private Command state;

	private JLabel messageLabel;

	private JButton ok = new JButton("OK");

	private Dialog dialog;

	public MessageWindow(String msg, String title, Dialog d) {
		super(title);

		dialog = d;

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		messageLabel = new JLabel(msg);
		ButtonPanel.makeLabelPretty(messageLabel);

		add(messageLabel, BorderLayout.NORTH);

		addOKButton();

	}

	//add a confirm button
		private void addOKButton() {
			ok = new JButton("OK");
			ButtonPanel.makeButtonsPretty(ok);
			ok.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					//make the dialog visible again, so that you can select where to move the item
					dialog.setVisible(true);
					setVisible(false);
				}

			});

			ok.setMnemonic(KeyEvent.VK_ENTER);	//TODO fix this
			add(ok, BorderLayout.CENTER);
		}

}
