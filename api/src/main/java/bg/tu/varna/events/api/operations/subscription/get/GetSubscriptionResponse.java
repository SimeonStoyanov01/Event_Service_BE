package bg.tu.varna.events.api.operations.subscription.get;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.SubscriptionModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSubscriptionResponse implements ProcessorResponse {
	SubscriptionModel subscriptionModel;
}

