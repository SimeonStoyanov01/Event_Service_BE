package bg.tu.varna.events.core.utils;

import bg.tu.varna.events.api.exceptions.OrganizationSuspendedException;
import bg.tu.varna.events.api.exceptions.UnauthorizedActionException;
import bg.tu.varna.events.api.exceptions.UserNotFoundException;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import bg.tu.varna.events.persistence.entities.Reservation;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.OrganizationStatus;
import bg.tu.varna.events.persistence.enums.Role;
import bg.tu.varna.events.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility class for validating user actions and roles.
 */
@Component
@RequiredArgsConstructor
public class ValidationUtils {

	private final UserRepository userRepository;

	/**
	 * Retrieves the currently authenticated user from the security context.
	 *
	 * @return the authenticated User
	 * @throws UserNotFoundException if no user is found with the current authentication
	 */
	public User getCurrentAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return userRepository.findUserByEmail(email)
				.orElseThrow(UserNotFoundException::new);
	}

	/**
	 * Validates if the given user belongs to the specified business organization.
	 * Throws UnauthorizedActionException if the user does not belong to the organization.
	 *
	 * @param user         the User to validate
	 * @param organization the Organization to check against
	 * @throws UnauthorizedActionException if the user does not belong to the organization
	 */
	public void validateUserBusinessOrganization(User user, Organization organization) {
		if (user.getRole() == Role.BUSINESS || user.getRole() == Role.BUSINESS_ADMIN) {
			if (!organization.getOrganizationId().equals(user.getOrganization().getOrganizationId())) {
				throw new UnauthorizedActionException();
			}
		}
	}

	/**
	 * Validates if the given user is the owner of the specified personal event.
	 * Throws UnauthorizedActionException if the user does not own the event.
	 *
	 * @param user         the User to validate
	 * @param personalEvent the PersonalEvent to check against
	 * @throws UnauthorizedActionException if the user does not own the event
	 */
	public void validateUserPersonalEvent(User user, PersonalEvent personalEvent) {
		if (user.getRole() == Role.PERSONAL) {
			if (!personalEvent.getUser().getUserId().equals(user.getUserId())) {
				throw new UnauthorizedActionException();
			}
		}
	}
	/**
	 * Validates if the given user is the owner of the specified reservation for an event.
	 * Throws UnauthorizedActionException if the user does not own the event.
	 *
	 * @param user         the User to validate
	 * @param reservation the Reservation to check against
	 * @throws UnauthorizedActionException if the user does not own the event
	 */
	public void validateUserReservation(User user, Reservation reservation) {
		if (user.getRole() == Role.PERSONAL) {
			if (!reservation.getUser().getUserId().equals(user.getUserId())) {
				throw new UnauthorizedActionException();
			}
		}
	}

	/**
	 * Validates access to a suspended organization.
	 * - Throws OrganizationSuspendedException if the organization is suspended and the user is unauthenticated or a regular user.
	 * - Allows access for business users belonging to the organization.
	 *
	 * @param organization the Organization to validate access to
	 * @throws OrganizationSuspendedException if access is denied due to suspension
	 */
	public void validateAccessToSuspendedOrganization(Organization organization) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() == "anonymousUser" || !authentication.isAuthenticated()) {
			if (organization.getOrganizationStatus() == OrganizationStatus.SUSPENDED) {
				throw new OrganizationSuspendedException();
			}
		} else if(authentication.isAuthenticated()){
			User user = getCurrentAuthenticatedUser();
			if ((organization.getOrganizationStatus() == OrganizationStatus.SUSPENDED) && (user.getRole() == Role.PERSONAL)) {
				throw new OrganizationSuspendedException();
			}
			if(organization.getOrganizationStatus() == OrganizationStatus.SUSPENDED){
				validateUserBusinessOrganization(user,organization);
			}
		}
	}

}
