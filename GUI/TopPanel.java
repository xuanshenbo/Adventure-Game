package GUI;

import interpreter.StrategyInterpreter;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import renderer.testRenderer;

public class TopPanel extends JPanel{

	private PlayerProfilePanel playerProf;
	private GameInfoPanel gameInfo;
	private GameFrame gameframe;

	public TopPanel(GameFrame g){
		this.gameframe = g;

		int xPadding = (gameframe.getMapWidth() - new GameInfoPanel().getWidth() - new PlayerProfilePanel().getWidth())/2;
		System.out.println(xPadding);
		setLayout(new FlowLayout(FlowLayout.CENTER, 150, 10));

		//add game info panel to the left
		gameInfo = new GameInfoPanel();
		add(gameInfo);

		//add player info panel to the right
		playerProf = new PlayerProfilePanel(gameframe.getPlayer());
		add(playerProf); //add to the right

	}

	public Set<JPanel> getPanels() {
		Set panels = new HashSet<JPanel>();
		panels.add(gameInfo);
		panels.add(playerProf);
		return panels;
	}
}