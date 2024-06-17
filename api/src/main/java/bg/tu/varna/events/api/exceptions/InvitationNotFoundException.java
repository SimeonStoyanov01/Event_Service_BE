package bg.tu.varna.events.api.exceptions;

public class InvitationNotFoundException extends RuntimeException{

	public InvitationNotFoundException() {
		super("Invitation not found");
	}
}
