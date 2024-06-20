package bg.tu.varna.events.api.exceptions;

public class TicketSoldOutException extends RuntimeException {

	public TicketSoldOutException() {
		super("Not enough tickets remaining");
	}
}
