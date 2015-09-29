package interpreter;


public class InitialStrategy implements StrategyInterpreter.Strategy{
		private StrategyInterpreter s;

		@Override
		public void notify(String text) {
			if(text.equals("client")){
				//let Felix know that the client option has been selected
			}
			else if(text.equals("clientserver")){
				//let Felix know that the client/server option has been selected
			}

		}

		@Override
		public void setInterpreter(StrategyInterpreter i) {
			// TODO Auto-generated method stub
		}
	}