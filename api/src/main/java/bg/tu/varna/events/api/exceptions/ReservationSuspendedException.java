package bg.tu.varna.events.api.exceptions;

public class ReservationSuspendedException extends RuntimeException {

	public ReservationSuspendedException() {
		super("Reservation is already canceled");
	}
}
