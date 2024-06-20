package bg.tu.varna.events.core.processors.reservation;

import bg.tu.varna.events.api.exceptions.ReservationNotFoundException;
import bg.tu.varna.events.api.exceptions.ReservationPaidException;
import bg.tu.varna.events.api.exceptions.ReservationSuspendedException;
import bg.tu.varna.events.api.model.ReservationModel;
import bg.tu.varna.events.api.operations.reservation.cancel.CancelReservationOperation;
import bg.tu.varna.events.api.operations.reservation.cancel.CancelReservationRequest;
import bg.tu.varna.events.api.operations.reservation.cancel.CancelReservationResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.Reservation;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.ReservationStatus;
import bg.tu.varna.events.persistence.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelReservationProcessor implements CancelReservationOperation {
	private final ReservationRepository reservationRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;

	@Override
	public CancelReservationResponse process(CancelReservationRequest request) {
		Reservation reservation = reservationRepository.findById(UUID.fromString(request.getReservationId()))
				.orElseThrow(ReservationNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserReservation(user,reservation);
		if(reservation.getReservationStatus()== ReservationStatus.CANCELED)
			throw new ReservationSuspendedException();

		if(reservation.getIsPaid())
			throw new ReservationPaidException();

		reservation.setReservationStatus(ReservationStatus.CANCELED);

		updateTicketCapacity(reservation);

		Reservation savedReservation = reservationRepository.save(reservation);

		ReservationModel reservationModel = conversionService.convert(savedReservation, ReservationModel.class);

		return CancelReservationResponse.builder()
				.reservationModel(reservationModel)
				.build();	}

	private static void updateTicketCapacity(Reservation reservation) {
		Event event = reservation.getEvent();
		event.setCapacity(event.getCapacity() + reservation.getNumberOfPeople());
	}
}
