package bg.tu.varna.events.api.operations.invitation.get;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.InvitationModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInvitationResponse implements ProcessorResponse {
	private InvitationModel invitationModel;
}

