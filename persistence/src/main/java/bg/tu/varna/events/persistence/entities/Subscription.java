package bg.tu.varna.events.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "subscriptions")
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID subscriptionId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "organization_id", nullable = false)
	private Organization organization;
}