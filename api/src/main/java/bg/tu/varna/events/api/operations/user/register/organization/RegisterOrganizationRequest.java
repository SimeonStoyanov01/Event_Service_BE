package bg.tu.varna.events.api.operations.user.register.organization;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterOrganizationRequest implements ProcessorRequest{

	@NotBlank(message = "Organization name is required")
	private String organizationName;

	@NotBlank(message = "Organization address is required")
	private String organizationAddress;

	@Email(message = "Invalid email format.", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
	@NotBlank(message = "Email is required.")
	private String email;

	@NotBlank(message = "Password is required.")
	private String password;

	@NotBlank(message = "Confirm password is required.")
	private String confirmPassword;

	@NotBlank(message = "First name is required")
	private String firstName;

	@NotBlank(message = "Last name is required")
	private String lastName;

	@NotBlank(message = "Phone number is required.")
	private String phoneNumber;

}
