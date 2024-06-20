package bg.tu.varna.events.api.exceptions;

public class ReservationActiveException extends RuntimeException {

	public ReservationActiveException() {
		super("Reservation is still active");
	}
}
