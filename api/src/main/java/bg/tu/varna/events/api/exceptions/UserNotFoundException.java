package bg.tu.varna.events.api.exceptions;

public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException() {
		super("User not found");
	}
}
