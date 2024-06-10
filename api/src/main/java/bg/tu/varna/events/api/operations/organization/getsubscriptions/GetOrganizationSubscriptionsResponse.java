package bg.tu.varna.events.api.operations.organization.getsubscriptions;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.SubscriptionModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationSubscriptionsResponse implements ProcessorResponse {

	private List<SubscriptionModel> subscriptionModels;
}

