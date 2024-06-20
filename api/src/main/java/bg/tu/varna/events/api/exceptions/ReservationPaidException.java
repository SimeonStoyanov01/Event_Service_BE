package bg.tu.varna.events.api.exceptions;

public class ReservationPaidException extends RuntimeException {

	public ReservationPaidException() {
		super("Reservation is paid so it cannot be updated");
	}
}
