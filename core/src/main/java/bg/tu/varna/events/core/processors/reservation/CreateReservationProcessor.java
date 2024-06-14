package bg.tu.varna.events.core.processors.reservation;

import bg.tu.varna.events.api.exceptions.EventNotFoundException;
import bg.tu.varna.events.api.exceptions.EventSuspendedException;
import bg.tu.varna.events.api.exceptions.OrganizationSuspendedException;
import bg.tu.varna.events.api.exceptions.TicketSoldOutException;
import bg.tu.varna.events.api.model.ReservationModel;
import bg.tu.varna.events.api.operations.reservation.create.CreateReservationOperation;
import bg.tu.varna.events.api.operations.reservation.create.CreateReservationRequest;
import bg.tu.varna.events.api.operations.reservation.create.CreateReservationResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.Reservation;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.EventStatus;
import bg.tu.varna.events.persistence.enums.OrganizationStatus;
import bg.tu.varna.events.persistence.enums.ReservationStatus;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import bg.tu.varna.events.persistence.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateReservationProcessor implements CreateReservationOperation {

	private final EventRepository eventRepository;
	private final ReservationRepository reservationRepository;
	private final ValidationUtils 	validationUtils;
	private final ConversionService conversionService;

	@Override
	public CreateReservationResponse process(CreateReservationRequest request) {
		Event event = eventRepository.findById(UUID.fromString(request.getEventId()))
				.orElseThrow(EventNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();

		if(event.getOrganization().getOrganizationStatus().equals(OrganizationStatus.SUSPENDED))
			throw new OrganizationSuspendedException();

		if(event.getStatus().equals(EventStatus.SUSPENDED) || event.getStatus().equals(EventStatus.EXPIRED))
			throw new EventSuspendedException();

		Reservation reservation = saveReservation(request, event, user);
		updateEventCapacity(request, event);

		ReservationModel reservationModel = conversionService.convert(reservation, ReservationModel.class);

		return CreateReservationResponse.builder()
				.reservationModel(reservationModel)
				.build();
	}

	private void updateEventCapacity(CreateReservationRequest request, Event event) {
		if(event.getCapacity() < request.getNumberOfPeople()) {
			throw new TicketSoldOutException();
		}
		event.setCapacity(event.getCapacity() - request.getNumberOfPeople());
		eventRepository.save(event);
	}

	private Reservation saveReservation(CreateReservationRequest request,Event event, User user) {
		Reservation reservation = Reservation.builder()
				.event(event)
				.numberOfPeople(request.getNumberOfPeople())
				.user(user)
				.reservationTime(LocalDateTime.now())
				.reservationDateTime(event.getEventDateTime())
				.reservationStatus(ReservationStatus.ACTIVE)
				.build();

		reservationRepository.save(reservation);
		return reservation;
	}
}
