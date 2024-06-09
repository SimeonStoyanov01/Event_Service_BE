package bg.tu.varna.events.core.processors.businessevent;

import bg.tu.varna.events.api.exceptions.EventNotFoundException;
import bg.tu.varna.events.api.exceptions.EventSuspendedException;
import bg.tu.varna.events.api.model.EventModel;
import bg.tu.varna.events.api.operations.businessevent.suspend.SuspendEventOperation;
import bg.tu.varna.events.api.operations.businessevent.suspend.SuspendEventRequest;
import bg.tu.varna.events.api.operations.businessevent.suspend.SuspendEventResponse;
import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.EventStatus;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SuspendBusinessEventProcessor implements SuspendEventOperation {

	private final EventRepository eventRepository;
	private final ConversionService conversionService;

	@Override
	public SuspendEventResponse process(SuspendEventRequest request) {
		Event event = eventRepository.findById(UUID.fromString(request.getEventId()))
				.orElseThrow(EventNotFoundException::new);

		if(event.getStatus()==EventStatus.SUSPENDED)
			throw new EventSuspendedException();

		event.setStatus(EventStatus.SUSPENDED);
		eventRepository.save(event);

		EventModel eventModel = conversionService.convert(event, EventModel.class);

		return SuspendEventResponse
				.builder()
				.eventModel(eventModel)
				.build();	}
}
