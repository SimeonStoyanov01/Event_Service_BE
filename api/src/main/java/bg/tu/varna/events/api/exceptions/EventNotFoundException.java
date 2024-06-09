package bg.tu.varna.events.api.exceptions;

public class EventNotFoundException extends RuntimeException{

	public EventNotFoundException() {
		super("Event not found");
	}
}
