package bg.tu.varna.events.core.processors.subscription;

import bg.tu.varna.events.api.exceptions.EmptySubscriptionsListException;
import bg.tu.varna.events.api.model.SubscriptionModel;
import bg.tu.varna.events.api.operations.subscription.getmy.GetMySubscriptionsOperation;
import bg.tu.varna.events.api.operations.subscription.getmy.GetMySubscriptionsRequest;
import bg.tu.varna.events.api.operations.subscription.getmy.GetMySubscriptionsResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMySubscriptionsProcessor implements GetMySubscriptionsOperation {

	private final SubscriptionRepository subscriptionRepository;
	private final ConversionService conversionService;
	private final ValidationUtils validationUtils;
	@Override
	public GetMySubscriptionsResponse process(GetMySubscriptionsRequest request) {
		User user = validationUtils.getCurrentAuthenticatedUser();

		List<SubscriptionModel> subscriptionModels = subscriptionRepository.findAllByUser(user)
				.stream()
				.map(subscription -> conversionService.convert(subscription, SubscriptionModel.class))
				.toList();

		if(subscriptionModels.isEmpty())
			throw new EmptySubscriptionsListException();

		return GetMySubscriptionsResponse
				.builder()
				.subscriptionModels(subscriptionModels)
				.build();
	}
}
