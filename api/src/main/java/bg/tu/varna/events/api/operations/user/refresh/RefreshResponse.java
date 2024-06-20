package bg.tu.varna.events.api.operations.user.refresh;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshResponse implements ProcessorResponse {
	private String jwtToken;
	private String refreshToken;
}

