package bg.tu.varna.events.api.model;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
	private UUID userId;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String role;
}
