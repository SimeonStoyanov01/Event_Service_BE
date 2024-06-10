package bg.tu.varna.events.core.processors.businessevent;

import bg.tu.varna.events.api.exceptions.EmptyEventsListException;
import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.model.EventModel;
import bg.tu.varna.events.api.operations.businessevent.getbyorganization.GetAllEventsByOrgOperation;
import bg.tu.varna.events.api.operations.businessevent.getbyorganization.GetAllEventsByOrgRequest;
import bg.tu.varna.events.api.operations.businessevent.getbyorganization.GetAllEventsByOrgResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.enums.EventStatus;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetAllBusinessEventsByOrgProcessor implements GetAllEventsByOrgOperation {

	private final EventRepository eventRepository;
	private final ConversionService conversionService;
	private final OrganizationRepository organizationRepository;
	private final ValidationUtils validationUtils;

	@Override
	public GetAllEventsByOrgResponse process(GetAllEventsByOrgRequest request) {
		Organization organization = organizationRepository
				.findOrganizationByOrganizationId(UUID.fromString(request.getOrganizationId()))
				.orElseThrow(OrganizationNotFoundException::new);
		validationUtils.validateAccessToSuspendedOrganization(organization);
		List<EventModel> eventModels = (request.getIncludeSuspended()
				? eventRepository.findAllByOrganizationOrganizationId(UUID.fromString(request.getOrganizationId()))
				: eventRepository.findAllByOrganizationOrganizationIdAndStatusNot(UUID.fromString(request.getOrganizationId()),
						EventStatus.SUSPENDED))
				.stream()
				.map(event -> conversionService.convert(event, EventModel.class))
				.toList();
		if (eventModels.isEmpty()) {
			throw new EmptyEventsListException();
		}

		return GetAllEventsByOrgResponse
				.builder()
				.eventModels(eventModels)
				.build();
	}
}
