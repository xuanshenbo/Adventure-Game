package items;

import state.Container;

public class Bag extends Item implements Container {

	public Bag(String description) {
		super(description, "", 'B');
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUseDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
