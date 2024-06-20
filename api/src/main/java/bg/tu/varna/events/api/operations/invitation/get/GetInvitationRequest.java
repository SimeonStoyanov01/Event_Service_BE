package bg.tu.varna.events.api.operations.invitation.get;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInvitationRequest implements ProcessorRequest{

	@NotBlank(message = "Invitation id is required")
	private String invitationId;

}