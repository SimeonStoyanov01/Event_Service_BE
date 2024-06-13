package bg.tu.varna.events.api.exceptions;

public class EmptyReservationsListException extends RuntimeException {

	public EmptyReservationsListException() {
		super("No reservations made");
	}
}
