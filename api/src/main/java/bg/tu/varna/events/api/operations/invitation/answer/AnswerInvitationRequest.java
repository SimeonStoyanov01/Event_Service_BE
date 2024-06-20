package bg.tu.varna.events.api.operations.invitation.answer;

import bg.tu.varna.events.api.base.ProcessorRequest;
import bg.tu.varna.events.persistence.enums.InvitationStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerInvitationRequest implements ProcessorRequest{

	@NotBlank(message = "Invitation id is required")
	private String invitationId;

	@NotBlank(message = "Invitee email is required")
	private String inviteeEmail;

	@NotBlank(message = "Please, provide an answer")
	private InvitationStatus invitationStatus;

	private String menuName;

}