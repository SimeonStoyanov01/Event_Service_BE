package bg.tu.varna.events.api.operations.invitation.getmy;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMyInvitationsRequest implements ProcessorRequest{

	@NotBlank(message = "Personal event id is required")
	private String personalEventId;

}