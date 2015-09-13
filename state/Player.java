/**
 * The holds the state of the Players in the game.
 */

package state;

public class Player {

	private Position position; // Position of the player in the world
	private final ID id; // unique identifier of the player

	public Player(Position p, ID id){
		this.position  = p;
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Position getPosition() {
		return position;
	}
}
