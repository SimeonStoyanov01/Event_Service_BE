package bg.tu.varna.events.api.exceptions;

public class UnauthorizedActionException extends RuntimeException {

	public UnauthorizedActionException() {
		super("Unauthorized action");
	}
}
