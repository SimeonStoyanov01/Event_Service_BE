package bg.tu.varna.events.api.operations.user.register.personal;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserResponse implements ProcessorResponse {
	private String userId;

	private String email;

}
