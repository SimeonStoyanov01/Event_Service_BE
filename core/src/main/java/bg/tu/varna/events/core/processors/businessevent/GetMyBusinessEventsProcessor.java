package bg.tu.varna.events.core.processors.businessevent;

import bg.tu.varna.events.api.exceptions.EmptyEventsListException;
import bg.tu.varna.events.api.model.EventModel;
import bg.tu.varna.events.api.operations.businessevent.getmy.GetMyEventsOperation;
import bg.tu.varna.events.api.operations.businessevent.getmy.GetMyEventsRequest;
import bg.tu.varna.events.api.operations.businessevent.getmy.GetMyEventsResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.EventStatus;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMyBusinessEventsProcessor implements GetMyEventsOperation {

	private final EventRepository eventRepository;
	private final ConversionService conversionService;
	private final ValidationUtils validationUtils;

	@Override
	public GetMyEventsResponse process(GetMyEventsRequest request) {
		List<EventModel> eventModels = (request.getIncludeSuspended()
				? eventRepository.findAllByUser(validationUtils.getCurrentAuthenticatedUser())
				: eventRepository.findAllByUserAndStatusNot(validationUtils.getCurrentAuthenticatedUser(), EventStatus.SUSPENDED))
				.stream()
				.map(event -> conversionService.convert(event, EventModel.class))
				.toList();
		if (eventModels.isEmpty()) {
			throw new EmptyEventsListException();
		}

		return GetMyEventsResponse
				.builder()
				.eventModels(eventModels)
				.build();
	}
}
