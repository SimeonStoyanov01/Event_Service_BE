package bg.tu.varna.events.api.operations.user.login;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements ProcessorResponse {
	private String jwtToken;
	private String refreshToken;
}

