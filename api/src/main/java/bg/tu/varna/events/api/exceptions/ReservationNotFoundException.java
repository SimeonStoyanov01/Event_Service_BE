package bg.tu.varna.events.api.exceptions;

public class ReservationNotFoundException extends RuntimeException{

	public ReservationNotFoundException() {
		super("Reservation not found");
	}
}
