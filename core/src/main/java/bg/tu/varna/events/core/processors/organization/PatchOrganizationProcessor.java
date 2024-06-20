package bg.tu.varna.events.core.processors.organization;

import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.model.OrganizationModel;
import bg.tu.varna.events.api.operations.organization.patch.PatchOrganizationOperation;
import bg.tu.varna.events.api.operations.organization.patch.PatchOrganizationRequest;
import bg.tu.varna.events.api.operations.organization.patch.PatchOrganizationResponse;
import bg.tu.varna.events.core.utils.PartialUpdateUtils;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatchOrganizationProcessor implements PatchOrganizationOperation {

	private final OrganizationRepository organizationRepository;
	private final ConversionService conversionService;
	private final ValidationUtils validationUtils;
	private final PartialUpdateUtils partialUpdateUtils;

	private static final Set<String> EXCLUDED_FIELDS = Set.of("organizationId", "organizationStatus");

	@Override
	public PatchOrganizationResponse process(PatchOrganizationRequest request) {
		Organization organization = organizationRepository.findById(UUID.fromString(request.getOrganizationId()))
				.orElseThrow(OrganizationNotFoundException::new);
		validationUtils.validateUserBusinessOrganization(validationUtils.getCurrentAuthenticatedUser(),organization);

		partialUpdateUtils.updateFields(organization, request, EXCLUDED_FIELDS);

		Organization savedOrganization = organizationRepository.save(organization);
		OrganizationModel organizationModel = conversionService.convert(savedOrganization, OrganizationModel.class);

		return PatchOrganizationResponse.builder()
				.organizationModel(organizationModel)
				.build();
	}
}