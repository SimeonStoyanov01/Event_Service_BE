package bg.tu.varna.events.persistence.entities;

import bg.tu.varna.events.persistence.enums.InvitationStatus;
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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private InvitationStatus invitationStatus;

	@ManyToOne
	@JoinColumn(name = "personal_event_id", nullable = false)
	private PersonalEvent personalEvent;

	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menu;
}