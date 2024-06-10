package bg.tu.varna.events.core.processors.organization;

import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.operations.organization.delete.DeleteOrganizationOperation;
import bg.tu.varna.events.api.operations.organization.delete.DeleteOrganizationRequest;
import bg.tu.varna.events.api.operations.organization.delete.DeleteOrganizationResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteOrganizationProcessor implements DeleteOrganizationOperation {

	private final OrganizationRepository organizationRepository;
	private final ValidationUtils validationUtils;

	@Override
	public DeleteOrganizationResponse process(DeleteOrganizationRequest request) {
		Organization organization = organizationRepository.findById(UUID.fromString(request.getOrganizationId()))
				.orElseThrow(OrganizationNotFoundException::new);

		validationUtils.validateUserBusinessOrganization(validationUtils.getCurrentAuthenticatedUser(),organization);

		organizationRepository.delete(organization);

		return DeleteOrganizationResponse.builder()
				.message("Organization deleted successfully")
				.build();
	}
}