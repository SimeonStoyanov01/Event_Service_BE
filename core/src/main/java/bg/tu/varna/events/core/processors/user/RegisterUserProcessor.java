package bg.tu.varna.events.core.processors.user;

import bg.tu.varna.events.api.exceptions.PasswordsDoNotMatchException;
import bg.tu.varna.events.api.exceptions.UserExistsException;
import bg.tu.varna.events.api.operations.user.register.personal.RegisterUserOperation;
import bg.tu.varna.events.api.operations.user.register.personal.RegisterUserRequest;
import bg.tu.varna.events.api.operations.user.register.personal.RegisterUserResponse;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.Role;
import bg.tu.varna.events.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegisterUserProcessor implements RegisterUserOperation {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public RegisterUserResponse process(RegisterUserRequest request) {
		userRepository.findUserByEmail(request.getEmail())
				.ifPresent(user -> {
					throw new UserExistsException();
				});

		if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
			throw new PasswordsDoNotMatchException();
		}

		Role role = userRepository.count() == 0 ? Role.ADMIN : Role.PERSONAL;

		User user = saveUser(request, role);

		return RegisterUserResponse.builder()
				.userId(String.valueOf(user.getUserId()))
				.email(user.getEmail())
				.build();
	}

	private User saveUser(RegisterUserRequest request, Role role) {
		User user = User
				.builder()
				.email(request.getEmail())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.username(request.getEmail().split("@")[0])
				.role(role)
				.password(passwordEncoder.encode(request.getPassword()))
				.build();
		userRepository.save(user);
		return user;
	}
}
