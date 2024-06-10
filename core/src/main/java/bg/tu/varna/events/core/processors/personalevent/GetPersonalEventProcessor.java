package bg.tu.varna.events.core.processors.personalevent;

import bg.tu.varna.events.api.exceptions.PersonalEventNotFoundException;
import bg.tu.varna.events.api.model.PersonalEventModel;
import bg.tu.varna.events.api.operations.personalevent.get.GetPersonalEventOperation;
import bg.tu.varna.events.api.operations.personalevent.get.GetPersonalEventRequest;
import bg.tu.varna.events.api.operations.personalevent.get.GetPersonalEventResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.PersonalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetPersonalEventProcessor implements GetPersonalEventOperation {
	private final PersonalEventRepository personalEventRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;

	@Override
	public GetPersonalEventResponse process(GetPersonalEventRequest request) {
		PersonalEvent personalEvent = personalEventRepository
				.findById(UUID.fromString(request.getPersonalEventId()))
				.orElseThrow(PersonalEventNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserPersonalEvent(user,personalEvent);
		PersonalEventModel personalEventModel = conversionService.convert(personalEvent,PersonalEventModel.class);
		return GetPersonalEventResponse
				.builder()
				.eventModel(personalEventModel)
				.build();
	}
}
