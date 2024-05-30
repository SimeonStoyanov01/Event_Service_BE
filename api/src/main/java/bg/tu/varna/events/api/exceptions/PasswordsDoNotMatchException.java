package bg.tu.varna.events.api.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException {
	public PasswordsDoNotMatchException() {
		super("Confirmation password should be the same");
	}
}
