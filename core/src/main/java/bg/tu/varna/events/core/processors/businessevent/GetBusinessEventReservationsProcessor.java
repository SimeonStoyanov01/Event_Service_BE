package bg.tu.varna.events.core.processors.businessevent;

import bg.tu.varna.events.api.exceptions.EventNotFoundException;
import bg.tu.varna.events.api.model.ReservationModel;
import bg.tu.varna.events.api.operations.businessevent.getreservations.GetBusinessEventReservationsOperation;
import bg.tu.varna.events.api.operations.businessevent.getreservations.GetBusinessEventReservationsRequest;
import bg.tu.varna.events.api.operations.businessevent.getreservations.GetBusinessEventReservationsResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetBusinessEventReservationsProcessor implements GetBusinessEventReservationsOperation {
	private final EventRepository eventRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;

	@Override
	public GetBusinessEventReservationsResponse process(GetBusinessEventReservationsRequest request) {
		Event event = eventRepository.findById(UUID.fromString(request.getEventId()))
				.orElseThrow(EventNotFoundException::new);

		validationUtils.validateUserBusinessOrganization(validationUtils.getCurrentAuthenticatedUser(),event.getOrganization());

		List<ReservationModel> reservationModels = event.getReservations().stream()
				.map(reservation -> conversionService.convert(reservation, ReservationModel.class))
				.toList();

		return GetBusinessEventReservationsResponse
				.builder()
				.eventName(event.getEventName())
				.reservationModels(reservationModels)
				.build();
	}
}