package bg.tu.varna.events.api.operations.invitation.delete;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteInvitationRequest implements ProcessorRequest{

	@NotBlank(message = "Invitation id is required")
	private String invitationId;

}