package bg.tu.varna.events.api.operations.user.refresh;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequest implements ProcessorRequest{

	@NotBlank(message = "Refresh token is required")
	private String refreshToken;

}
