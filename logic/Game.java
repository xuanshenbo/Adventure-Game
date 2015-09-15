package logic;

import state.Area;
import state.Area.AreaType;

public class Game {

	public void clockTick() {
		// TODO Auto-generated method stub
	}
	
	public static void main(String[] args){
		Generator g = new Generator(10, 2, 1, 5);
		Area a = new Area(10, 20, AreaType.OUTSIDE, null);
		a.generateWorld(g);	
		a.printArea();
	}

}
