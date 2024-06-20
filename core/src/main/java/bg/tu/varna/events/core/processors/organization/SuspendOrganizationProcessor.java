package bg.tu.varna.events.core.processors.organization;

import bg.tu.varna.events.api.exceptions.EventSuspendedException;
import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.model.OrganizationModel;
import bg.tu.varna.events.api.operations.organization.suspend.SuspendOrganizationOperation;
import bg.tu.varna.events.api.operations.organization.suspend.SuspendOrganizationRequest;
import bg.tu.varna.events.api.operations.organization.suspend.SuspendOrganizationResponse;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.enums.OrganizationStatus;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SuspendOrganizationProcessor implements SuspendOrganizationOperation {

	private final OrganizationRepository organizationRepository;
	private final ConversionService conversionService;

	@Override
	public SuspendOrganizationResponse process(SuspendOrganizationRequest request) {
		Organization organization = organizationRepository.findById(UUID.fromString(request.getOrganizationId()))
				.orElseThrow(OrganizationNotFoundException::new);
		if(organization.getOrganizationStatus()== OrganizationStatus.SUSPENDED)
			throw new EventSuspendedException();

		organization.setOrganizationStatus(OrganizationStatus.SUSPENDED);
		Organization savedOrganization = organizationRepository.save(organization);

		OrganizationModel organizationModel = conversionService.convert(savedOrganization, OrganizationModel.class);

		return SuspendOrganizationResponse.builder()
				.organizationModel(organizationModel)
				.build();
	}
}