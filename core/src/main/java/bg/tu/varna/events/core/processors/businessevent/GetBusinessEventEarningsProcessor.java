package bg.tu.varna.events.core.processors.businessevent;

import bg.tu.varna.events.api.exceptions.EventNotFoundException;
import bg.tu.varna.events.api.operations.businessevent.getearnings.GetEventEarningsOperation;
import bg.tu.varna.events.api.operations.businessevent.getearnings.GetEventEarningsRequest;
import bg.tu.varna.events.api.operations.businessevent.getearnings.GetEventEarningsResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.ReservationStatus;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import bg.tu.varna.events.persistence.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetBusinessEventEarningsProcessor implements GetEventEarningsOperation {
	private final EventRepository eventRepository;
	private final ReservationRepository reservationRepository;
	private final ValidationUtils validationUtils;

	@Override
	public GetEventEarningsResponse process(GetEventEarningsRequest request) {
		Event event = eventRepository.findById(UUID.fromString(request.getEventId()))
				.orElseThrow(EventNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserBusinessOrganization(user,event.getOrganization());

		BigDecimal estimatedEarnings = reservationRepository.findAllByEventAndReservationStatusNot(event, ReservationStatus.CANCELED)
				.stream()
				.map(reservation -> event.getTicketPrice().multiply(BigDecimal.valueOf(reservation.getNumberOfPeople())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal actualEarnings = reservationRepository.findAllByEventAndReservationStatusNotAndIsPaid(event, ReservationStatus.CANCELED, true)
				.stream()
				.map(reservation -> event.getTicketPrice().multiply(BigDecimal.valueOf(reservation.getNumberOfPeople())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return GetEventEarningsResponse
				.builder()
				.eventName(event.getEventName())
				.actualEarnings(actualEarnings)
				.estimatedEarnings(estimatedEarnings)
				.build();
	}
}
