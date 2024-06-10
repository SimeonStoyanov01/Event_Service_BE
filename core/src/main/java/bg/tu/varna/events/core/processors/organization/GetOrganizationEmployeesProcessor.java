package bg.tu.varna.events.core.processors.organization;


import bg.tu.varna.events.api.exceptions.EmptyEmployeeListException;
import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.model.UserModel;
import bg.tu.varna.events.api.operations.organization.getemployees.GetOrganizationEmployeesOperation;
import bg.tu.varna.events.api.operations.organization.getemployees.GetOrganizationEmployeesRequest;
import bg.tu.varna.events.api.operations.organization.getemployees.GetOrganizationEmployeesResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetOrganizationEmployeesProcessor implements GetOrganizationEmployeesOperation {

	private final OrganizationRepository organizationRepository;
	private final ConversionService conversionService;
	private final ValidationUtils validationUtils;

	@Override
	public GetOrganizationEmployeesResponse process(GetOrganizationEmployeesRequest request) {
		Organization organization = organizationRepository.findById(UUID.fromString(request.getOrganizationId()))
				.orElseThrow(OrganizationNotFoundException::new);

		validationUtils.validateUserBusinessOrganization(validationUtils.getCurrentAuthenticatedUser(),organization);

		List<UserModel> employeeModels = organization.getBusinessUsers().stream()
				.map(user -> conversionService.convert(user, UserModel.class))
				.toList();

		if (employeeModels.isEmpty()) {
			throw new EmptyEmployeeListException();
		}

		return GetOrganizationEmployeesResponse.builder()
				.employeeModels(employeeModels)
				.build();
	}
}