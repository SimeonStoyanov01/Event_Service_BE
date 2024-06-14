package bg.tu.varna.events.api.model;

import bg.tu.varna.events.persistence.enums.SubscriptionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime subscriptionTime;
	private SubscriptionStatus subscriptionStatus;
}