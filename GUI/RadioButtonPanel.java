package GUI;

import interpreter.StrategyInterpreter;

import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RadioButtonPanel extends JPanel {

	private GameFrame containerFrame;
	private Set<JRadioButton> buttonOptions = new HashSet<JRadioButton>();

	private StrategyInterpreter radioInterpreter;

	//this constructor not currently used
	public RadioButtonPanel(GameFrame container, String type){
		containerFrame = container;

	}

	/**
	 * This constructor show the options for the inventory: what items, and drop, move to bag, or use.
	 * @param inventoryContents An array list of string descriptions of items in the inventory to display
	 */
	public RadioButtonPanel(ArrayList<String> inventoryContents, StrategyInterpreter radioInterp) {
		this.radioInterpreter = radioInterp;

		Map<String, JLabel> jlabels = new HashMap<String, JLabel>();

		/*
		 * Create JLabels for each item in the inventory
		 */
		if(inventoryContents!=null){
			for(String i: inventoryContents){
				JLabel item = new JLabel();

				String name = "";

				//capitalise the first letter of the item description
				if(i != null){
					name = i.substring(0, 1).toUpperCase() + i.substring(1, i.length() -1);

					//attach a picture of the item to the jlabel
					Image image= ImageLoader.loadImage(i+".png");
					ImageIcon icon = new ImageIcon(image);
					item.setIcon(icon);

				}
				else{
					//if item is null, this is an empty slot in the inventory
					name = "Empty";

					Image image= ImageLoader.loadImage("emptyslot.png"); //load a blank white square
					ImageIcon icon = new ImageIcon(image);
					item.setIcon(icon);
				}

				//put this name -> jlabel pair in the map
				jlabels.put(name, item);
			}
		}

		ButtonGroup inventoryGroup = new ButtonGroup();

		for(int i = 0; i<inventoryContents.size(); i++){
			final JRadioButton item = new JRadioButton(inventoryContents.get(i));

			final int index = i;	//has to be final to be used in anon class below

			//add radio button to button group
			inventoryGroup.add(item);

			ItemListener radioListener = new ItemListener(){
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==1) { //then checked
						if(e.getSource() == item){
							try {
								//notify that an item has been selected, and at which slot
								radioInterpreter.notify("selected "+index);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}


					}
				}
			};

			item.addItemListener(radioListener);

			//add the jlabel image associated with this item
			add(jlabels.get(inventoryContents.get(i)));

			//add this button with its description to the panel
			add(item);


		}

	}


}
