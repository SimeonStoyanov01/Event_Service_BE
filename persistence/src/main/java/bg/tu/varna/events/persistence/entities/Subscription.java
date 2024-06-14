package bg.tu.varna.events.persistence.entities;

import bg.tu.varna.events.persistence.enums.SubscriptionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

	@Column(name = "reservation_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime reservationTime;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SubscriptionStatus subscriptionStatus;

	@ManyToOne
	@JoinColumn(name = "organization_id", nullable = false)
	private Organization organization;
}