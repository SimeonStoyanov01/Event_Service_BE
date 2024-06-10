package bg.tu.varna.events.core.processors.businessevent;

import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.exceptions.OrganizationSuspendedException;
import bg.tu.varna.events.api.model.EventModel;
import bg.tu.varna.events.api.operations.businessevent.create.CreateEventOperation;
import bg.tu.varna.events.api.operations.businessevent.create.CreateEventRequest;
import bg.tu.varna.events.api.operations.businessevent.create.CreateEventResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.EventStatus;
import bg.tu.varna.events.persistence.enums.OrganizationStatus;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBusinessEventProcessor implements CreateEventOperation {

	private final EventRepository eventRepository;
	private final OrganizationRepository organizationRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;

	@Override
	public CreateEventResponse process(CreateEventRequest request) {
		Organization organization = organizationRepository.findById(request.getOrganizationId())
				.orElseThrow(OrganizationNotFoundException::new);

		if(organization.getOrganizationStatus() == OrganizationStatus.SUSPENDED)
			throw new OrganizationSuspendedException();

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserBusinessOrganization(user, organization);
		Event event = saveEvent(request, organization, user);
		EventModel eventModel = conversionService.convert(event, EventModel.class);
		return CreateEventResponse
				.builder()
				.eventModel(eventModel)
				.build();
	}

	private Event saveEvent(CreateEventRequest request, Organization organization, User user) {
		Event event = Event.builder()
				.eventName(request.getEventName())
				.eventDescription(request.getEventDescription())
				.eventDateTime(request.getEventDateTime())
				.capacity(request.getCapacity())
				.organization(organization)
				.user(user)
				.status(EventStatus.ACTIVE)
				.build();

		eventRepository.save(event);
		return event;
	}
}
