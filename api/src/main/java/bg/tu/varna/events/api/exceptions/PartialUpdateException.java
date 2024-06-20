package bg.tu.varna.events.api.exceptions;

public class PartialUpdateException extends RuntimeException {

	public PartialUpdateException() {
		super("Update failed");
	}
}
