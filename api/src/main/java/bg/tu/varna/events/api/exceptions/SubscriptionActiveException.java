package bg.tu.varna.events.api.exceptions;

public class SubscriptionActiveException extends RuntimeException {

	public SubscriptionActiveException() {
		super("An active subscription is already made");
	}
}
