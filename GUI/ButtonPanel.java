package GUI;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

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
	//private StrategyInterpreter buttonInterpreter;
	/**
	 * The constructor stores the button interpreter to a field
	 */
	public ButtonPanel(){ //TODO make it take a StrategyInterpreter
		//buttonInterpreter = b;

		//make buttons layout top to bottom
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(boxLayout);

		inventory = new JButton("Inventory");
		inventory.setMnemonic(KeyEvent.VK_I);
		inventory.setToolTipText("Display your inventory");
		inventory.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				//notify interpreter
			}
		});

		team = new JButton("Team");
		team.setMnemonic(KeyEvent.VK_T);
		team.setToolTipText("Display your team");
		team.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				//notify interpreter
			}
		});

		exchange = new JButton("Exchange");
		exchange.setMnemonic(KeyEvent.VK_E);
		exchange.setToolTipText("Exchange an item");
		exchange.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				//notify interpreter
			}
		});

		add(inventory);
		add(team);
		add(exchange);
}
}
