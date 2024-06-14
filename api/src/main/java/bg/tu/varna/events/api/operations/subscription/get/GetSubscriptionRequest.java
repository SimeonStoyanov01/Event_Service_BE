package bg.tu.varna.events.api.operations.subscription.get;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSubscriptionRequest implements ProcessorRequest{

	@NotBlank(message = "Subscription id is required")
	private String subscriptionId;
}