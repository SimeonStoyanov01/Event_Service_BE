package bg.tu.varna.events.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID eventId;

	@Column(nullable = false)
	private String eventName;

	@Column(nullable = false)
	private String eventDescription;

	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime eventDateTime;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private Integer capacity;

	@ManyToOne
	@JoinColumn(name = "organization_id")
	private Organization organization;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
	private List<Reservation> reservations;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EventStatus status;
}
