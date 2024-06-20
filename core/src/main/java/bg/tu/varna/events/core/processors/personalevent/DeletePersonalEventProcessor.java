package bg.tu.varna.events.core.processors.personalevent;

import bg.tu.varna.events.api.exceptions.PersonalEventNotFoundException;
import bg.tu.varna.events.api.operations.personalevent.delete.DeletePersonalEventOperation;
import bg.tu.varna.events.api.operations.personalevent.delete.DeletePersonalEventRequest;
import bg.tu.varna.events.api.operations.personalevent.delete.DeletePersonalEventResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.PersonalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletePersonalEventProcessor implements DeletePersonalEventOperation {
	private final PersonalEventRepository personalEventRepository;
	private final ValidationUtils validationUtils;
	@Override
	public DeletePersonalEventResponse process(DeletePersonalEventRequest request) {
		User authenticatedUser = validationUtils.getCurrentAuthenticatedUser();
		PersonalEvent personalEvent = personalEventRepository.findById(UUID.fromString(request.getPersonalEventId()))
				.orElseThrow(PersonalEventNotFoundException::new);
		validationUtils.validateUserPersonalEvent(authenticatedUser,personalEvent);
		personalEventRepository.delete(personalEvent);
		return DeletePersonalEventResponse.builder().message("Event deleted successfully").build();	}
}
