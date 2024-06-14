package bg.tu.varna.events.api.operations.subscription.cancel;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.SubscriptionModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelSubscriptionResponse implements ProcessorResponse {
	private SubscriptionModel subscriptionModel;
}

