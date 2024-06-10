package bg.tu.varna.events.core.processors.organization;

import bg.tu.varna.events.api.exceptions.EmptySubscriptionsListException;
import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.model.SubscriptionModel;
import bg.tu.varna.events.api.operations.organization.getsubscriptions.GetOrganizationSubscriptionsOperation;
import bg.tu.varna.events.api.operations.organization.getsubscriptions.GetOrganizationSubscriptionsRequest;
import bg.tu.varna.events.api.operations.organization.getsubscriptions.GetOrganizationSubscriptionsResponse;
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
public class GetOrganizationSubscriptionsProcessor implements GetOrganizationSubscriptionsOperation {

	private final OrganizationRepository organizationRepository;
	private final ConversionService conversionService;
	private final ValidationUtils validationUtils;

	@Override
	public GetOrganizationSubscriptionsResponse process(GetOrganizationSubscriptionsRequest request) {
		Organization organization = organizationRepository.findById(UUID.fromString(request.getOrganizationId()))
				.orElseThrow(OrganizationNotFoundException::new);

		validationUtils.validateUserBusinessOrganization(validationUtils.getCurrentAuthenticatedUser(),organization);

		List<SubscriptionModel> subscriptionModels = organization.getSubscriptions().stream()
				.map(subscription -> conversionService.convert(subscription, SubscriptionModel.class))
				.toList();
		if (subscriptionModels.isEmpty()) {
			throw new EmptySubscriptionsListException();
		}

		return GetOrganizationSubscriptionsResponse.builder()
				.subscriptionModels(subscriptionModels)
				.build();
	}
}