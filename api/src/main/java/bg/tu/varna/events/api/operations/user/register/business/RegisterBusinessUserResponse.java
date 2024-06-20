package bg.tu.varna.events.api.operations.user.register.business;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBusinessUserResponse implements ProcessorResponse {
	private String organizationId;

	private String organizationName;

	private String userId;

	private String email;
}

