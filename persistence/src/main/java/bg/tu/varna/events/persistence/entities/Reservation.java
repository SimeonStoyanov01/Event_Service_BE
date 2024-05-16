package bg.tu.varna.events.persistence.entities;

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
@Table(name = "reservations")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID reservationId;

	@Column(name = "reservation_date_time")
	private LocalDateTime reservationDateTime;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;
}