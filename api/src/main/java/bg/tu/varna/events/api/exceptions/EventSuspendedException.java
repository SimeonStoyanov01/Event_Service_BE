package bg.tu.varna.events.api.exceptions;

public class EventSuspendedException extends RuntimeException {

	public EventSuspendedException() {
		super("Event already suspended");
	}
}
