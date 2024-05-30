package bg.tu.varna.events.api.exceptions;

public class UserExistsException extends RuntimeException {
	public UserExistsException() {

		super("User already exists");
	}
}

