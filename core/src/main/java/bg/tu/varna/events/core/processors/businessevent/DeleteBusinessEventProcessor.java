package bg.tu.varna.events.core.processors.businessevent;

import bg.tu.varna.events.api.exceptions.EventNotFoundException;
import bg.tu.varna.events.api.operations.businessevent.delete.DeleteEventOperation;
import bg.tu.varna.events.api.operations.businessevent.delete.DeleteEventRequest;
import bg.tu.varna.events.api.operations.businessevent.delete.DeleteEventResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class DeleteBusinessEventProcessor implements DeleteEventOperation {

	private final EventRepository eventRepository;
	private final ValidationUtils validationUtils;

	@Override
	public DeleteEventResponse process(DeleteEventRequest request) {
		User authenticatedUser = validationUtils.getCurrentAuthenticatedUser();
		Event event = eventRepository.findById(UUID.fromString(request.getEventId()))
				.orElseThrow(EventNotFoundException::new);
		validationUtils.validateUserBusinessOrganization(authenticatedUser,event.getOrganization());
		eventRepository.delete(event);
		return DeleteEventResponse.builder().message("Event deleted successfully").build();
	}

}
