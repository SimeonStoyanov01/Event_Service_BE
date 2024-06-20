package bg.tu.varna.events.api.model;

import bg.tu.varna.events.persistence.enums.InvitationStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationModel {
	private UUID invitationId;
	private String inviteeEmail;
	private InvitationStatus invitationStatus;
	private UUID personalEventId;
	private String menuName;
}
