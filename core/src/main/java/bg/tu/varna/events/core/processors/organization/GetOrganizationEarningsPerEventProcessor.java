package bg.tu.varna.events.core.processors.organization;

import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.operations.businessevent.getearnings.GetEventEarningsOperation;
import bg.tu.varna.events.api.operations.businessevent.getearnings.GetEventEarningsRequest;
import bg.tu.varna.events.api.operations.businessevent.getearnings.GetEventEarningsResponse;
import bg.tu.varna.events.api.operations.organization.getearnings.GetOrganizationEarningsOperation;
import bg.tu.varna.events.api.operations.organization.getearnings.GetOrganizationEarningsRequest;
import bg.tu.varna.events.api.operations.organization.getearnings.GetOrganizationEarningsResponse;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetOrganizationEarningsPerEventProcessor implements GetOrganizationEarningsOperation {

	private final OrganizationRepository organizationRepository;
	private final EventRepository eventRepository;
	private final GetEventEarningsOperation getEventEarningsOperation;
//	private final ValidationUtils validationUtils;

	@Override
	public GetOrganizationEarningsResponse process(GetOrganizationEarningsRequest request) {
		Organization organization = organizationRepository.findById(UUID.fromString(request.getOrganizationId()))
				.orElseThrow(OrganizationNotFoundException::new);

//		User user = validationUtils.getCurrentAuthenticatedUser();
//		validationUtils.validateUserBusinessOrganization(user, organization);

		List<GetEventEarningsResponse> events = eventRepository.findAllByOrganizationOrganizationId(organization.getOrganizationId())
				.stream()
				.map(event -> getEventEarningsOperation.process(
						GetEventEarningsRequest
								.builder()
								.eventId(String.valueOf(event.getEventId()))
								.build()))
				.toList();

		return GetOrganizationEarningsResponse
				.builder()
				.organizationEarningsPerEvent(events)
				.build();
	}
}
