package bg.tu.varna.events.core.processors.businessevent;

import bg.tu.varna.events.api.exceptions.EmptyEventsListException;
import bg.tu.varna.events.api.model.EventModel;
import bg.tu.varna.events.api.operations.businessevent.getall.GetAllEventsOperation;
import bg.tu.varna.events.api.operations.businessevent.getall.GetAllEventsRequest;
import bg.tu.varna.events.api.operations.businessevent.getall.GetAllEventsResponse;
import bg.tu.varna.events.persistence.entities.EventStatus;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllBusinessEventsProcessor implements GetAllEventsOperation {

	private final EventRepository eventRepository;
	private final ConversionService conversionService;

	@Override
	public GetAllEventsResponse process(GetAllEventsRequest request) {
		List<EventModel> eventModels = (request.getIncludeSuspended()
				? eventRepository.findAll()
				: eventRepository.findByStatusNot(EventStatus.SUSPENDED))
				.stream()
				.map(event -> conversionService.convert(event, EventModel.class))
				.toList();
		if (eventModels.isEmpty()) {
			throw new EmptyEventsListException();
		}
		return GetAllEventsResponse
				.builder()
				.eventModels(eventModels)
				.build();
	}
}
