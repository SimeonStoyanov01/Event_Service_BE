package bg.tu.varna.events.core.processors.personalevent;

import bg.tu.varna.events.api.model.PersonalEventModel;
import bg.tu.varna.events.api.operations.personalevent.create.CreatePersonalEventOperation;
import bg.tu.varna.events.api.operations.personalevent.create.CreatePersonalEventRequest;
import bg.tu.varna.events.api.operations.personalevent.create.CreatePersonalEventResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.PersonalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePersonalEventProcessor implements CreatePersonalEventOperation {

	private final PersonalEventRepository personalEventRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;

	@Override
	public CreatePersonalEventResponse process(CreatePersonalEventRequest request) {

		User user = validationUtils.getCurrentAuthenticatedUser();
		PersonalEvent personalEvent = savedPersonalEvent(request, user);
		PersonalEventModel personalEventModel = conversionService.convert(personalEvent, PersonalEventModel.class);
		return CreatePersonalEventResponse
				.builder()
				.eventModel(personalEventModel)
				.build();
	}

	private PersonalEvent savedPersonalEvent(CreatePersonalEventRequest request, User user) {
		PersonalEvent personalEvent = PersonalEvent
				.builder()
				.eventName(request.getEventName())
				.eventLocation(request.getEventLocation())
				.eventDescription(request.getEventDescription())
				.eventDateTime(request.getEventDateTime())
				.user(user)
				.build();
		return  personalEventRepository.save(personalEvent);
	}
}
