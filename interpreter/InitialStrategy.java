package interpreter;

import java.io.Writer;

import main.Initialisation;
import main.Main;


public class InitialStrategy implements StrategyInterpreter.Strategy{
	private Initialisation s;

	@Override
	public void notify(String text) {
		if(text.equals("start")){
			s.displayMainGameFrame(s.getClient(),s.getGame());
		}
		else{
			if(text.equals("client")){
				//let Felix know that the client option has been selected
			}
			else if(text.equals("clientserver")){
				Main.serverClient();
				Main.displayMainGameFrame(s.getClient());
				Main.closeWelcome();
			}
		}

	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.s = (Initialisation) i;
	}

}