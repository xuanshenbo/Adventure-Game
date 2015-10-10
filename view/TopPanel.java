package view;

import interpreter.StrategyInterpreter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import renderer.testRenderer;

public class TopPanel extends JPanel{

	private PlayerProfilePanel playerProf;
	//	private GameInfoPanel gameInfo;
	private GameFrame gameframe;

	public TopPanel(GameFrame g){
		this.gameframe = g;

		//int xPadding = (gameframe.getMapWidth() - new GameInfoPanel(gameframe).getWidth() - new PlayerProfilePanel().getWidth())/2;
		//int xPadding = (size.width - new GameInfoPanel(gameframe).getWidth() - new PlayerProfilePanel().getWidth())/2;

		//System.out.println("xPadding: "+xPadding); //-125
		setLayout(new FlowLayout(FlowLayout.CENTER, 150, 10));

		//add game info panel to the left
		//		gameInfo = new GameInfoPanel(gameframe);
		//		add(gameInfo);

		//add player info panel to the right
		playerProf = new PlayerProfilePanel(gameframe.getPlayer());
		add(playerProf); //add to the right

		setBackground(GameFrame.col2);


	}

}