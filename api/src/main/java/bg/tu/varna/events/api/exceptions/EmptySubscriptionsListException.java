package bg.tu.varna.events.api.exceptions;

public class EmptySubscriptionsListException extends RuntimeException {

	public EmptySubscriptionsListException() {
		super("No subscriptions in the organization");
	}
}
