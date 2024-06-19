package bg.tu.varna.events.persistence.entities;

import bg.tu.varna.events.persistence.enums.ReservationStatus;
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
@Table(name = "reservations")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID reservationId;

	@Column(name = "reservation_date_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime reservationDateTime;

	@Column(name = "reservation_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime reservationTime;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;

	@Column(nullable = false)
	private Boolean paid;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ReservationStatus reservationStatus;

	@Column(nullable = false)
	private Integer numberOfPeople;

}