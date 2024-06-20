package bg.tu.varna.events.api.exceptions;

public class EmptyEventsListException extends RuntimeException {

	public EmptyEventsListException() {
		super("No events are meeting the criteria");
	}
}
