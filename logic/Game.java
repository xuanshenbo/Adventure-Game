package logic;

import state.Area;
import state.Area.AreaType;

public class Game {

	public void clockTick() {
		// TODO Auto-generated method stub
	}
	
	public static void main(String[] args){
		Generator g = new Generator(10,2);
		Area a = new Area(20, 20, AreaType.OUTSIDE);
		a.generateWorld(g);	
		a.printArea();
	}

}
