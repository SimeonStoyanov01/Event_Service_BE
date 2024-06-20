package bg.tu.varna.events.api.exceptions;

public class SubscriptionCanceledException extends RuntimeException {

	public SubscriptionCanceledException() {
		super("Subscription has already been canceled");
	}
}
