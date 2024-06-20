package bg.tu.varna.events.api.operations.subscription.getmy;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.SubscriptionModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMySubscriptionsResponse implements ProcessorResponse {

	private List<SubscriptionModel> subscriptionModels;
}

