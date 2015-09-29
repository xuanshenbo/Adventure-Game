package interpreter;

import Main.Initialisation;


public class InitialStrategy implements StrategyInterpreter.Strategy{
		private Initialisation s;

		@Override
		public void notify(String text) {
			if(text.equals("start")){
				s.displayMainGameFrame();
			}
			else{
				if(text.equals("client")){
					//let Felix know that the client option has been selected
				}
				else if(text.equals("clientserver")){
					//let Felix know that the client/server option has been selected
				}
			}

		}

		@Override
		public void setInterpreter(StrategyInterpreter i) {
			this.s = (Initialisation) i;
		}
	}