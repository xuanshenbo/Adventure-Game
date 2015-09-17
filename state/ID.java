/**
 * This class holds the unique identifiers of the entities in the
 * game and is used for hashcode and equals methods.
 */

package state;

public class ID {

	private int id;

	public ID(int id){
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ID other = (ID) obj;
		if (id != other.id)
			return false;
		return true;
	}
	 
	public String toString(){
		return Integer.toString(id);
	}

}
