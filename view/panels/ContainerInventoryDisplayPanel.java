package view.panels;

import interpreter.StrategyInterpreter;
import interpreter.Translator.Command;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.frames.Dialog;
import view.styledComponents.HappinessRadioButton;
import view.utilities.ImageLoader;

/**
 * This panel displays the inventory or general container contents to the user
 * and presents the user's options for interaction with the items.
 * @author flanagdonn
 *
 */
public class ContainerInventoryDisplayPanel extends JPanel {


	private StrategyInterpreter buttonInterpreter;

	private Dimension imageSize = new Dimension(50, 50);

	private Dialog containerDialog;

	//only display the drop/use/move options on the first selection of a radio button option
	private boolean firstButtonSelected = false;

	private Command state;

	/**
	 * This constructor show the options for the inventory: what items, and drop, move to bag, or use.
	 * @param contents An array list of string descriptions of items in the inventory to display
	 * @param state
	 * @param radioInterp The strategyinterpreter which interprets radio button action events
	 */
	public ContainerInventoryDisplayPanel(ArrayList<String> contents, StrategyInterpreter buttonInterp, Dialog d, Command st) {
		System.out.println(st);

		this.state = st;

		this.firstButtonSelected = false;

		this.containerDialog = d;

		this.buttonInterpreter = buttonInterp;

		/*
		 * Create HappinessLabels for each item in the inventory
		 */
		if(contents==null){

			throw new IllegalArgumentException("The inventory/container shouldn't be null");
		}

		ButtonGroup inventoryGroup = new ButtonGroup();

		for(int i = 0; i<contents.size(); i++){
			String name = contents.get(i);

			final HappinessRadioButton item;

			final JLabel imageLabel = new JLabel();

			if(!name.equals("emptyslot")){

				//capitalise the first letter of the item description
				name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());

				//attach a picture of the item to the HappinessLabel
				Image image= ImageLoader.loadImage(contents.get(i)+".png").getScaledInstance(imageSize.width, imageSize.height, -1);
				ImageIcon icon = new ImageIcon(image);
				imageLabel.setIcon(icon);

				item = new HappinessRadioButton(name);

			}
			else{
				item = new HappinessRadioButton("Empty");

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
								buttonInterpreter.notify("selected "+index);

								//tell the dialog to display item options, but only if a choice hasn't yet been made
								//ie don't keep adding options everytime a new option chosen
								if(!firstButtonSelected){
									//need to display different button choices depending on whether this is a container
									//or an inventory
									if(state != null && state.equals(Command.DISPLAY_INVENTORY)){
										containerDialog.displayItemOptions(true);
									}
									else if(state != null && state.equals(Command.DISPLAY_CONTAINER)){
										containerDialog.displayItemOptions(false);
									}
									firstButtonSelected = true;
								}

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
