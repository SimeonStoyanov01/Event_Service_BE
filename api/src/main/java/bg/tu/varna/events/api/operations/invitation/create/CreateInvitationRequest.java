package bg.tu.varna.events.api.operations.invitation.create;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInvitationRequest implements ProcessorRequest {

	@NotBlank(message = "Personal event id is required")
	private String personalEventId;

	private List<
			@NotBlank(message = "List must not be empty")
			@Email(message = "Invalid email format.", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
					String> inviteeEmail;
}