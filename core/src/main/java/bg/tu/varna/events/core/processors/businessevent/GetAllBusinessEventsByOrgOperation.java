package bg.tu.varna.events.core.processors.businessevent;

import bg.tu.varna.events.api.exceptions.EmptyEventsListException;
import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.model.EventModel;
import bg.tu.varna.events.api.operations.businessevent.getbyorganization.GetAllEventsByOrgOperation;
import bg.tu.varna.events.api.operations.businessevent.getbyorganization.GetAllEventsByOrgRequest;
import bg.tu.varna.events.api.operations.businessevent.getbyorganization.GetAllEventsByOrgResponse;
import bg.tu.varna.events.persistence.entities.EventStatus;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetAllBusinessEventsByOrgOperation implements GetAllEventsByOrgOperation {

	private final EventRepository eventRepository;
	private final ConversionService conversionService;
	private final OrganizationRepository organizationRepository;

	@Override
	public GetAllEventsByOrgResponse process(GetAllEventsByOrgRequest request) {
		organizationRepository
				.findOrganizationByOrganizationId(UUID.fromString(request.getOrganizationId()))
				.orElseThrow(OrganizationNotFoundException::new);
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
