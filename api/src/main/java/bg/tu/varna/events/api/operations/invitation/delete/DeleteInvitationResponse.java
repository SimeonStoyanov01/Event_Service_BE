package bg.tu.varna.events.api.operations.invitation.delete;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteInvitationResponse implements ProcessorResponse {
	private String message;
}

