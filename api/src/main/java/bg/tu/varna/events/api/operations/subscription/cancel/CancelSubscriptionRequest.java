package bg.tu.varna.events.api.operations.subscription.cancel;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelSubscriptionRequest implements ProcessorRequest{

	@NotBlank(message = "Subscription id is required")
	private String subscriptionId;
}