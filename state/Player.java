/**
 * The holds the state of the Players in the game.
 */

package state;

public class Player {

	private Position position; // Position of the player in the world
	private final int id; // unique identifier of the player

	public Player(Position p, int id){
		this.position  = p;
		this.id = id;;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position newPosition) {
		position = newPosition;
		
	}

	public int getId() {
		return id;
	}

	public String toString(){
		return Integer.toString(id);
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
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
