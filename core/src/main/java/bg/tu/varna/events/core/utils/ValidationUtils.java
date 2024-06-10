package bg.tu.varna.events.core.utils;

import bg.tu.varna.events.api.exceptions.UnauthorizedActionException;
import bg.tu.varna.events.api.exceptions.UserNotFoundException;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import bg.tu.varna.events.persistence.entities.Role;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationUtils {
	private final UserRepository userRepository;

	public User getCurrentAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return userRepository.findUserByEmail(email)
				.orElseThrow(UserNotFoundException::new);
	}

	public void validateUserBusinessOrganization(User user, Organization organization) {
		if (user.getRole() == Role.BUSINESS || user.getRole() == Role.BUSINESS_ADMIN) {
			if (!organization.getOrganizationId().equals(user.getOrganization().getOrganizationId())) {
				throw new UnauthorizedActionException();
			}
		}
	}

	public void validateUserPersonalEvent(User user, PersonalEvent personalEvent) {
		if (user.getRole() == Role.PERSONAL) {
			if (!personalEvent.getUser().getUserId().equals(user.getUserId())) {
				throw new UnauthorizedActionException();
			}
		}
	}
}
