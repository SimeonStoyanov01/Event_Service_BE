package bg.tu.varna.events.api.operations.invitation.answer;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.InvitationModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerInvitationResponse implements ProcessorResponse {
	private InvitationModel invitationModel;
}

