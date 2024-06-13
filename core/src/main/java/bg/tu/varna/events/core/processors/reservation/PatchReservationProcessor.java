package bg.tu.varna.events.core.processors.reservation;

import bg.tu.varna.events.api.exceptions.ReservationNotFoundException;
import bg.tu.varna.events.api.exceptions.ReservationSuspendedException;
import bg.tu.varna.events.api.exceptions.TicketSoldOutException;
import bg.tu.varna.events.api.model.ReservationModel;
import bg.tu.varna.events.api.operations.reservation.patch.PatchReservationOperation;
import bg.tu.varna.events.api.operations.reservation.patch.PatchReservationRequest;
import bg.tu.varna.events.api.operations.reservation.patch.PatchReservationResponse;
import bg.tu.varna.events.core.utils.PartialUpdateUtils;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.Reservation;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.ReservationStatus;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import bg.tu.varna.events.persistence.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatchReservationProcessor implements PatchReservationOperation {

	private final ReservationRepository reservationRepository;
	private final EventRepository eventRepository;
	private final ValidationUtils validationUtils;
	private final PartialUpdateUtils partialUpdateUtils;
	private final ConversionService conversionService;

	private static final Set<String> EXCLUDED_FIELDS = Set.of("reservationId");

	@Override
	public PatchReservationResponse process(PatchReservationRequest request) {
		Reservation reservation = reservationRepository.findById(UUID.fromString(request.getReservationId()))
				.orElseThrow(ReservationNotFoundException::new);

		if(!reservation.getReservationStatus().equals(ReservationStatus.ACTIVE))
			throw new ReservationSuspendedException();

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserReservation(user, reservation);

		Event event = reservation.getEvent();
		updateEventCapacity(request, reservation, event);

		partialUpdateUtils.updateFields(reservation, request, EXCLUDED_FIELDS);
		Reservation savedReservation = reservationRepository.save(reservation);
		ReservationModel reservationModel = conversionService.convert(savedReservation, ReservationModel.class);

		return PatchReservationResponse
				.builder()
				.reservationModel(reservationModel)
				.build();
	}

	private void updateEventCapacity(PatchReservationRequest request, Reservation reservation, Event event) {
		Integer updatedNumberOfTickets = request.getNumberOfPeople();
		Integer oldNumberOfTickets = reservation.getNumberOfPeople();
		Integer change = updatedNumberOfTickets - oldNumberOfTickets;
		Integer capacity = event.getCapacity();

		if (change > 0) {
			if (capacity < change)
				throw new TicketSoldOutException();
			event.setCapacity(capacity - change);
		}
		if (change < 0) {
			event.setCapacity(capacity + Math.abs(change));
		}
		eventRepository.save(event);
	}
}
