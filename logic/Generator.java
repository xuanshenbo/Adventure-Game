/**
 * This holds the variables that are needed to create a new area.
 * The arguements that are passed in on creation are:
 * int trees: this is the ratio of trees in the world.
 */
package logic;

public class Generator {

	private int trees;

	public Generator(int trees){
		this.trees = trees;
	}

	public int treeRatio(){
		return trees;
	}

}
