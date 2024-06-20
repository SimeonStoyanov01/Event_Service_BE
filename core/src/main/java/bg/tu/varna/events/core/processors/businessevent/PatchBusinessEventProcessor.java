package bg.tu.varna.events.core.processors.businessevent;

import bg.tu.varna.events.api.exceptions.EventNotFoundException;
import bg.tu.varna.events.api.model.EventModel;
import bg.tu.varna.events.api.operations.businessevent.patch.PatchEventOperation;
import bg.tu.varna.events.api.operations.businessevent.patch.PatchEventRequest;
import bg.tu.varna.events.api.operations.businessevent.patch.PatchEventResponse;
import bg.tu.varna.events.core.utils.PartialUpdateUtils;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatchBusinessEventProcessor implements PatchEventOperation {

	private final EventRepository eventRepository;
	private final ConversionService conversionService;
	private final ValidationUtils validationUtils;
	private final PartialUpdateUtils partialUpdateUtils;
	private static final Set<String> EXCLUDED_FIELDS = Set.of("eventId");

	@Override
	public PatchEventResponse process(PatchEventRequest request) {
		Event event = eventRepository.findById(UUID.fromString(request.getEventId()))
				.orElseThrow(EventNotFoundException::new);
		validationUtils.validateUserBusinessOrganization(validationUtils.getCurrentAuthenticatedUser(),event.getOrganization());
		partialUpdateUtils.updateFields(event,request,EXCLUDED_FIELDS);

		Event savedEvent = eventRepository.save(event);

		EventModel eventModel = conversionService.convert(savedEvent, EventModel.class);

		return PatchEventResponse
				.builder()
				.eventModel(eventModel)
				.build();
	}
}