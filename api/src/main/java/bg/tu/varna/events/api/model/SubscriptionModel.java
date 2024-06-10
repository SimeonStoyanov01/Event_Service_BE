package bg.tu.varna.events.api.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionModel {
	private UUID subscriptionId;
	private UUID userId;
	private UUID organizationId;
}