/*package GUI;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RadioButtonPanel extends JPanel {

	private GameFrame containerFrame;
	private Set<JRadioButton> buttonOptions = new HashSet<JRadioButton>();


	public RadioButtonPanel(GameFrame container, String type){
		containerFrame = container;
		if(type.equals("inventory")){
			inventoryRadio();
		}

	}

	*//**
	 * This creates a radio button for choosing weapons and add it to the panel.
	 *//*
	private void inventoryRadio() {

		//container
		JRadioButton item;
		for(String itm:containerFrame.getInventory()){
			item = new JRadioButton(itm);
			add(item);
		}

		ItemListener radioListener = new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==1) { //then checked
					if(e.getSource() == candlestick){
						radioInterpreter.notify("CANDLESTICK");
					}


				}
			}
		};

		candlestick.addItemListener(radioListener);



		ButtonGroup group = new ButtonGroup();
		group.add(candlestick);


		//add buttons individually to panel??
		add(candlestick);



	}
*/