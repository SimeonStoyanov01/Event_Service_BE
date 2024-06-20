package bg.tu.varna.events.api.operations.user.login;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest implements ProcessorRequest{

	@Email(message = "Invalid email format.", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
	@NotBlank(message = "Email is required.")
	private String email;

	@NotBlank(message = "Password is required.")
	private String password;

}
