package bg.tu.varna.events.core.processors.personalevent;

import bg.tu.varna.events.api.exceptions.PersonalEventNotFoundException;
import bg.tu.varna.events.api.model.PersonalEventModel;
import bg.tu.varna.events.api.operations.personalevent.patch.PatchPersonalEventOperation;
import bg.tu.varna.events.api.operations.personalevent.patch.PatchPersonalEventRequest;
import bg.tu.varna.events.api.operations.personalevent.patch.PatchPersonalEventResponse;
import bg.tu.varna.events.core.utils.PartialUpdateUtils;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import bg.tu.varna.events.persistence.repositories.PersonalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatchPersonalEventProcessor implements PatchPersonalEventOperation {
	private final PersonalEventRepository personalEventRepository;
	private final ValidationUtils validationUtils;
	private final PartialUpdateUtils partialUpdateUtils;
	private final ConversionService conversionService;

	private static final Set<String> EXCLUDED_FIELDS = Set.of("eventId");
	@Override
	public PatchPersonalEventResponse process(PatchPersonalEventRequest request) {
		PersonalEvent personalEvent = personalEventRepository.findById(UUID.fromString(request.getEventId()))
				.orElseThrow(PersonalEventNotFoundException::new);
		validationUtils.validateUserPersonalEvent(validationUtils.getCurrentAuthenticatedUser(), personalEvent);

		partialUpdateUtils.updateFields(personalEvent,request,EXCLUDED_FIELDS);

		PersonalEvent savedPersonalEvent = personalEventRepository.save(personalEvent);

		PersonalEventModel personalEventModel = conversionService.convert(savedPersonalEvent, PersonalEventModel.class);

		return PatchPersonalEventResponse.builder()
				.eventModel(personalEventModel)
				.build();
	}
}
