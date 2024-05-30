package bg.tu.varna.events.core;

import bg.tu.varna.events.api.exceptions.OrganizationAlreadyExistsException;
import bg.tu.varna.events.api.exceptions.PasswordsDoNotMatchException;
import bg.tu.varna.events.api.exceptions.UserExistsException;
import bg.tu.varna.events.api.operations.user.register.organization.RegisterOrganizationOperation;
import bg.tu.varna.events.api.operations.user.register.organization.RegisterOrganizationRequest;
import bg.tu.varna.events.api.operations.user.register.organization.RegisterOrganizationResponse;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.entities.Role;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import bg.tu.varna.events.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegisterOrganizationProcessor implements RegisterOrganizationOperation {

	private final UserRepository userRepository;
	private final OrganizationRepository organizationRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public RegisterOrganizationResponse process(RegisterOrganizationRequest request) {
		organizationRepository.findOrganizationByOrganizationName(request.getOrganizationName())
				.ifPresent(org -> {
					throw new OrganizationAlreadyExistsException();
				});

		Organization organization = Organization
				.builder()
				.organizationName(request.getOrganizationName())
				.organizationAddress(request.getOrganizationAddress())
				.build();

		organizationRepository.save(organization);

		userRepository.findUserByEmail(request.getEmail())
				.ifPresent(user -> {
					throw new UserExistsException();
				});

		if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
			throw new PasswordsDoNotMatchException();
		}

		User user = User
				.builder()
				.email(request.getEmail())
				.username(request.getEmail().split("@")[0])
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.role(Role.BUSINESS_ADMIN)
				.password(passwordEncoder.encode(request.getPassword()))
				.organization(organization)
				.build();
		userRepository.save(user);

		return RegisterOrganizationResponse
				.builder()
				.organizationId(String.valueOf(user.getOrganization().getOrganizationId()))
				.organizationName(user.getOrganization().getOrganizationName())
				.userId(String.valueOf(user.getUserId()))
				.email(user.getEmail())
				.build();
	}
}
