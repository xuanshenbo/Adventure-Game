package view;

import interpreter.StrategyInterpreter;

import java.awt.Dimension;
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


	private Dimension imageSize = new Dimension(50, 50);

	private Dialog containerDialog;

	//this constructor not currently used
	public RadioButtonPanel(GameFrame container, String type){
		containerFrame = container;

	}

	/**
	 * This constructor show the options for the inventory: what items, and drop, move to bag, or use.
	 * @param inventoryContents An array list of string descriptions of items in the inventory to display
	 * @param radioInterp The strategyinterpreter which interprets radio button action events
	 */
	public RadioButtonPanel(ArrayList<String> inventoryContents, StrategyInterpreter radioInterp, Dialog d) {


		this.containerDialog = d;

		this.radioInterpreter = radioInterp;

		Map<String, JLabel> jlabels = new HashMap<String, JLabel>();

		int countEmptySlots = 0;

		/*
		 * Create JLabels for each item in the inventory
		 */
		if(inventoryContents==null){

			throw new IllegalArgumentException("The inventory/container shouldn't be null");
		}

		ButtonGroup inventoryGroup = new ButtonGroup();

		for(int i = 0; i<inventoryContents.size(); i++){
			String name = inventoryContents.get(i);

			final JRadioButton item;

			final JLabel imageLabel = new JLabel();

			if(!name.equals("emptyslot")){

				//capitalise the first letter of the item description
				name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());

				//attach a picture of the item to the jlabel
				Image image= ImageLoader.loadImage(inventoryContents.get(i)+".png").getScaledInstance(imageSize.width, imageSize.height, -1);
				ImageIcon icon = new ImageIcon(image);
				imageLabel.setIcon(icon);

				item = new JRadioButton(inventoryContents.get(i));

			}
			else{
				item = new JRadioButton("Empty");

				//attach a picture of a blank square
				Image image= ImageLoader.loadImage("emptyslot.png").getScaledInstance(imageSize.width, imageSize.height, -1);
				ImageIcon icon = new ImageIcon(image);
				imageLabel.setIcon(icon);
			}

			//has to be final to be used in anon class below
			final int index = i;

			//add radio button to button group
			inventoryGroup.add(item);

			ItemListener radioListener = new ItemListener(){
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==1) { //then checked
						if(e.getSource() == item){
							try {
								//notify that an item has been selected, and at which slot
								radioInterpreter.notify("selected "+index);

								//tell the dialog to display item options
								containerDialog.displayItemOptions();

							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}


					}
				}
			};

			item.addItemListener(radioListener);

			//add this button with its description to the panel
			add(item);
			//add the image label beside the button
			add(imageLabel);


		}

	}


}
