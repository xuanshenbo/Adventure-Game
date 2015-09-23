package interpreter;

/**
 * Used to implement the Observer design patters when dealing with user events
 */
public interface Observer {
	/**
	 * Notifies the Model of the user event, or the View of a Game instruction
	 * @param text
	 */
	public void notify(String text);
}
