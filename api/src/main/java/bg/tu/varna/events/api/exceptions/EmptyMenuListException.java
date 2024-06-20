package bg.tu.varna.events.api.exceptions;

public class EmptyMenuListException extends RuntimeException {

	public EmptyMenuListException() {
		super("No menus available for this event");
	}
}
