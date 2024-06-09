package bg.tu.varna.events.core.processors.businessevent;

import bg.tu.varna.events.api.exceptions.EventNotFoundException;
import bg.tu.varna.events.api.model.EventModel;
import bg.tu.varna.events.api.operations.businessevent.get.GetEventOperation;
import bg.tu.varna.events.api.operations.businessevent.get.GetEventRequest;
import bg.tu.varna.events.api.operations.businessevent.get.GetEventResponse;
import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetBusinessEventProcessor implements GetEventOperation {

	private final EventRepository eventRepository;
	private final ConversionService conversionService;

	@Override
	public GetEventResponse process(GetEventRequest request) {
		Event event = eventRepository
				.findById(UUID.fromString(request.getEventId()))
				.orElseThrow(EventNotFoundException::new);
//		User currentAuthenticatedUser = validationUtils.getCurrentAuthenticatedUser();
//
//		if(event.getStatus()== EventStatus.SUSPENDED && currentAuthenticatedUser.getRole() != Role.ADMIN)
//			throw new UnauthorizedActionException();//TODO decide if you will implement it here
		EventModel eventModel = conversionService.convert(event, EventModel.class);
		return GetEventResponse
				.builder()
				.eventModel(eventModel)
				.build();
	}
}
