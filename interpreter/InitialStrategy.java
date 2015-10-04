package interpreter;

import java.io.Writer;

import main.Initialisation;
import main.Main;


public class InitialStrategy implements StrategyInterpreter.Strategy{
	private Initialisation s;

	@Override
	public void notify(String text) {
		if(text.equals("start")){
			System.out.println("START");
			s.displayMainGameFrame();
		}
		else{
			if(text.equals("client")){
				//let Felix know that the client option has been selected
			}
			else if(text.equals("clientserver")){
				Main.serverClient();
			}
		}

	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.s = (Initialisation) i;
	}

}