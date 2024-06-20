package bg.tu.varna.events.api.exceptions;

public class SubscriptionNotFoundException extends RuntimeException{

	public SubscriptionNotFoundException() {
		super("Subscription not found");
	}
}
