package bg.tu.varna.events.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "personal_events")
public class PersonalEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "personal_event_id")
	private UUID personalEventId;

	@Column(name = "event_name", nullable = false)
	private String eventName;

	@Column(name = "event_description")
	private String eventDescription;

	@Column(name = "event_date_time", nullable = false)
	private LocalDateTime eventDateTime;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "personalEvent")
	private Set<Invitation> invitations;
}
