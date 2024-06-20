package bg.tu.varna.events.core.processors.organization;

import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.model.OrganizationModel;
import bg.tu.varna.events.api.operations.organization.get.GetOrganizationOperation;
import bg.tu.varna.events.api.operations.organization.get.GetOrganizationRequest;
import bg.tu.varna.events.api.operations.organization.get.GetOrganizationResponse;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class GetOrganizationProcessor implements GetOrganizationOperation {

	private final OrganizationRepository organizationRepository;
	private final ConversionService conversionService;

	@Override
	public GetOrganizationResponse process(GetOrganizationRequest request) {
		Organization organization = organizationRepository
				.findById(UUID.fromString(request.getOrganizationId()))
				.orElseThrow(OrganizationNotFoundException::new);
		//		User currentAuthenticatedUser = validationUtils.getCurrentAuthenticatedUser();
		//
		//		if(event.getStatus()== EventStatus.SUSPENDED && currentAuthenticatedUser.getRole() != Role.ADMIN)
		//			throw new UnauthorizedActionException();//TODO decide if you will implement it here
		OrganizationModel organizationModel = conversionService.convert(organization, OrganizationModel.class);
		return GetOrganizationResponse
				.builder()
				.organizationModel(organizationModel)
				.build();
	}
}
