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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime eventDateTime;

	@Column(name = "event_location", nullable = false)
	private String eventLocation;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "personalEvent")
	private List<Invitation> invitations;
}
