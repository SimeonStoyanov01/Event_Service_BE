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
@Table(name = "invitations")
public class Invitation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID invitationId;

	private String inviteeEmail;

	private String status;

	@ManyToOne
	@JoinColumn(name = "personal_event_id", nullable = false)
	private PersonalEvent personalEvent;
}