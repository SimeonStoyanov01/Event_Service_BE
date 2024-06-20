package bg.tu.varna.events.core.processors.businessevent;

import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.exceptions.OrganizationSuspendedException;
import bg.tu.varna.events.api.model.EventModel;
import bg.tu.varna.events.api.operations.businessevent.create.CreateEventOperation;
import bg.tu.varna.events.api.operations.businessevent.create.CreateEventRequest;
import bg.tu.varna.events.api.operations.businessevent.create.CreateEventResponse;
import bg.tu.varna.events.api.operations.mailer.MailerOperation;
import bg.tu.varna.events.api.operations.mailer.MailerRequest;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.EventStatus;
import bg.tu.varna.events.persistence.enums.OrganizationStatus;
import bg.tu.varna.events.persistence.enums.SubscriptionStatus;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import bg.tu.varna.events.persistence.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateBusinessEventProcessor implements CreateEventOperation {

	private final EventRepository eventRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final OrganizationRepository organizationRepository;
	private final MailerOperation mailerOperation;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;

	@Value("${client.url}")
	private String clientUrl;
	@Override
	public CreateEventResponse process(CreateEventRequest request) {
		Organization organization = organizationRepository.findById(request.getOrganizationId())
				.orElseThrow(OrganizationNotFoundException::new);

		if(organization.getOrganizationStatus() == OrganizationStatus.SUSPENDED)
			throw new OrganizationSuspendedException();

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserBusinessOrganization(user, organization);

		Event event = saveEvent(request, organization, user);

		notifySubscribers(event,organization);

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

	private void notifySubscribers(Event event, Organization organization) {
		List<String> subscribers = subscriptionRepository.findAllByOrganizationAndSubscriptionStatus(
				organization, SubscriptionStatus.ACTIVE)
				.stream()
				.map(subscription -> subscription.getUser().getEmail())
				.toList();

		for (String s : subscribers) {
			sendEmail(s, event, organization);
		}
	}

	private void sendEmail(String email, Event event, Organization organization) {
		Map<String, Object> templateModel = new HashMap<>();
		templateModel.put("eventName", event.getEventName());
		templateModel.put("eventDate", event.getEventDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		templateModel.put("eventLocation", event.getOrganization().getOrganizationAddress());
		templateModel.put("organization", organization.getOrganizationName().toUpperCase());
		templateModel.put("eventDescription", event.getEventDescription());
		templateModel.put("unsubscribeLink",
				clientUrl + "/subscriptions/cancel?organizationId=" + organization.getOrganizationId() + "&userId=" + email); // Assuming email is unique and can be used as user identifier

		// Send email
		MailerRequest mailerRequest = MailerRequest.builder()
				.to(email)
				.subject("New event")
				.templateName("eventNewsletterTemplate")
				.templateModel(templateModel)
				.build();

		mailerOperation.process(mailerRequest);
	}
}
