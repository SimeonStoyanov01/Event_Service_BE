package bg.tu.varna.events.api.exceptions;

public class EmptyInvitationsListException extends RuntimeException {

	public EmptyInvitationsListException() {
		super("No invitations are meeting the criteria");
	}
}
