package bg.tu.varna.events.core.user;

import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.exceptions.PasswordsDoNotMatchException;
import bg.tu.varna.events.api.exceptions.UserExistsException;
import bg.tu.varna.events.api.operations.user.register.business.RegisterBusinessUserOperation;
import bg.tu.varna.events.api.operations.user.register.business.RegisterBusinessUserRequest;
import bg.tu.varna.events.api.operations.user.register.business.RegisterBusinessUserResponse;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.entities.Role;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import bg.tu.varna.events.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterBusinessUserProcessor implements RegisterBusinessUserOperation {

	private final UserRepository userRepository;
	private final OrganizationRepository organizationRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public RegisterBusinessUserResponse process(RegisterBusinessUserRequest request) {
		Organization organization = organizationRepository
				.findOrganizationByOrganizationId(UUID.fromString(request.getOrganizationId()))
				.orElseThrow(OrganizationNotFoundException::new);


		userRepository.findUserByEmail(request.getEmail())
				.ifPresent(user -> {
					throw new UserExistsException();
				});

		if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
			throw new PasswordsDoNotMatchException();
		}

		User user = saveUser(request, organization);

		return RegisterBusinessUserResponse
				.builder()
				.organizationId(String.valueOf(user.getOrganization().getOrganizationId()))
				.organizationName(user.getOrganization().getOrganizationName())
				.userId(String.valueOf(user.getUserId()))
				.email(user.getEmail())
				.build();
	}

	private User saveUser(RegisterBusinessUserRequest request, Organization organization) {
		User user = User
				.builder()
				.email(request.getEmail())
				.username(request.getEmail().split("@")[0])
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.role(Role.BUSINESS)
				.password(passwordEncoder.encode(request.getPassword()))
				.organization(organization)
				.build();
		userRepository.save(user);
		return user;
	}

}
