package bg.tu.varna.events.api.operations.invitation.create;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.InvitationModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInvitationResponse implements ProcessorResponse {
	private List<InvitationModel> invitationModel;
}

