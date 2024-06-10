package bg.tu.varna.events.core.mappers;

import bg.tu.varna.events.api.model.SubscriptionModel;
import bg.tu.varna.events.persistence.entities.Subscription;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper implements Converter<Subscription, SubscriptionModel> {

	@Override
	public SubscriptionModel convert(Subscription source) {
		return SubscriptionModel.builder()
				.subscriptionId(source.getSubscriptionId())
				.userId(source.getUser().getUserId())
				.organizationId(source.getOrganization().getOrganizationId())
				.build();
	}
}