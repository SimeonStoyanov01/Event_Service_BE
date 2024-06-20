package bg.tu.varna.events.core.processors.subscription;

import bg.tu.varna.events.api.exceptions.SubscriptionCanceledException;
import bg.tu.varna.events.api.exceptions.SubscriptionNotFoundException;
import bg.tu.varna.events.api.model.SubscriptionModel;
import bg.tu.varna.events.api.operations.subscription.cancel.CancelSubscriptionOperation;
import bg.tu.varna.events.api.operations.subscription.cancel.CancelSubscriptionRequest;
import bg.tu.varna.events.api.operations.subscription.cancel.CancelSubscriptionResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Subscription;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.SubscriptionStatus;
import bg.tu.varna.events.persistence.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelSubscriptionProcessor implements CancelSubscriptionOperation {
	private final SubscriptionRepository subscriptionRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;
	@Override
	public CancelSubscriptionResponse process(CancelSubscriptionRequest request) {
		Subscription subscription = subscriptionRepository.findById(UUID.fromString(request.getSubscriptionId()))
				.orElseThrow(SubscriptionNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserSubscription(user,subscription);

		if (subscription.getSubscriptionStatus().equals(SubscriptionStatus.CANCELED))
			throw new SubscriptionCanceledException();

		subscription.setSubscriptionStatus(SubscriptionStatus.CANCELED);
		Subscription savedSubscription = subscriptionRepository.save(subscription);

		SubscriptionModel subscriptionModel = conversionService.convert(savedSubscription, SubscriptionModel.class);
		return CancelSubscriptionResponse
				.builder()
				.subscriptionModel(subscriptionModel)
				.build();
	}
}
