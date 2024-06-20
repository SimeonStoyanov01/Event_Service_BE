package bg.tu.varna.events.core.processors.subscription;

import bg.tu.varna.events.api.exceptions.SubscriptionNotFoundException;
import bg.tu.varna.events.api.model.SubscriptionModel;
import bg.tu.varna.events.api.operations.subscription.get.GetSubscriptionOperation;
import bg.tu.varna.events.api.operations.subscription.get.GetSubscriptionRequest;
import bg.tu.varna.events.api.operations.subscription.get.GetSubscriptionResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Subscription;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetSubscriptionProcessor implements GetSubscriptionOperation {
	private final SubscriptionRepository subscriptionRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;

	@Override
	public GetSubscriptionResponse process(GetSubscriptionRequest request) {
		Subscription subscription = subscriptionRepository.findById(UUID.fromString(request.getSubscriptionId()))
				.orElseThrow(SubscriptionNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserSubscription(user,subscription);
		validationUtils.validateUserBusinessOrganization(user,subscription.getOrganization());
		SubscriptionModel subscriptionModel = conversionService.convert(subscription, SubscriptionModel.class);
		return GetSubscriptionResponse
				.builder()
				.subscriptionModel(subscriptionModel)
				.build();
	}
}
