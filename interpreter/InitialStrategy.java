package interpreter;

import java.io.Writer;
import java.util.Scanner;

import main.Initialisation;
import main.Main;


public class InitialStrategy implements StrategyInterpreter.Strategy{
	private Initialisation s;

	@Override
	public void notify(String text) {
		if(text.equals("start")){
			System.out.println("START");
			s.displayMainGameFrame(s.getClient(),s.getGame());
		}
		else if(text.startsWith("open")){
			Scanner sc = new Scanner(text);
			String command = sc.next();
			String filename = sc.next();
			//notify game passing it the filename

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