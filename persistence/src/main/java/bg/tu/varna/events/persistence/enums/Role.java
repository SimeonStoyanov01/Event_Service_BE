package bg.tu.varna.events.persistence.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.tu.varna.events.persistence.enums.Permissions.*;

@Getter
@RequiredArgsConstructor
public enum Role {
	PERSONAL(
			Set.of(
					USER_CREATE,
					USER_UPDATE,
					USER_DELETE,
					USER_READ,
					PERSONAL_EVENT_CREATE,
					PERSONAL_EVENT_DELETE,
					PERSONAL_EVENT_UPDATE,
					PERSONAL_EVENT_READ,
					PERSONAL_EVENT_READ_ALL,
					SUBSCRIPTION_CREATE,
					SUBSCRIPTION_READ,
					SUBSCRIPTION_READMY,
					SUBSCRIPTION_CANCEL,
					RESERVATION_CREATE,
					RESERVATION_READ,
					RESERVATION_READMY,
					RESERVATION_CANCEL,
					RESERVATION_UPDATE,
					INVITATION_DELETE,
					INVITATION_READ,
					INVITATION_READMY,
					INVITATION_CREATE,
					MENU_READ,
					MENU_CREATE,
					MENU_DELETE,
					MENU_READMY

			)),
	BUSINESS(
			Set.of(
					BUSINESS_USER_CREATE,
					BUSINESS_USER_UPDATE,
					BUSINESS_USER_DELETE,
					BUSINESS_EVENT_CREATE,
					BUSINESS_EVENT_READ_BYUSER,
					BUSINESS_EVENT_READ_RESERVATIONS,
					BUSINESS_EVENT_DELETE,
					BUSINESS_EVENT_UPDATE,
					SUBSCRIPTION_READ,
					RESERVATION_READ,
					RESERVATION_DELETE,
					ORGANIZATION_READ_SUBSCRIPTIONS,
					ORGANIZATION_READ_EMPLOYEES
			)),
	BUSINESS_ADMIN(
			Set.of(
					BUSINESS_ADMIN_READ,
					BUSINESS_ADMIN_CREATE,
					BUSINESS_ADMIN_UPDATE,
					BUSINESS_ADMIN_DELETE,
					BUSINESS_USER_UPDATE,
					BUSINESS_USER_DELETE,
					BUSINESS_USER_READ,
					BUSINESS_USER_CREATE,
					BUSINESS_EVENT_CREATE,
					BUSINESS_EVENT_READ_BYUSER,
					BUSINESS_EVENT_READ_RESERVATIONS,
					BUSINESS_EVENT_DELETE,
					BUSINESS_EVENT_UPDATE,
					SUBSCRIPTION_READ,
					RESERVATION_READ,
					RESERVATION_DELETE,
					ORGANIZATION_READ_SUBSCRIPTIONS,
					ORGANIZATION_READ_EMPLOYEES,
					ORGANIZATION_CREATE,
					ORGANIZATION_DELETE,
					ORGANIZATION_UPDATE
			)),
	ADMIN(
			Set.of(
					ADMIN_READ,
					ADMIN_UPDATE,
					ADMIN_DELETE,
					ADMIN_CREATE,
					BUSINESS_ADMIN_READ,
					BUSINESS_ADMIN_CREATE,
					BUSINESS_ADMIN_UPDATE,
					BUSINESS_ADMIN_DELETE,
					BUSINESS_USER_READ,
					BUSINESS_USER_CREATE,
					BUSINESS_USER_UPDATE,
					BUSINESS_USER_DELETE,
					BUSINESS_EVENT_CREATE,
					BUSINESS_EVENT_READ_BYUSER,
					BUSINESS_EVENT_READ_RESERVATIONS,
					BUSINESS_EVENT_DELETE,
					BUSINESS_EVENT_UPDATE,
					BUSINESS_EVENT_SUSPEND,
					RESERVATION_READ,
					RESERVATION_CREATE,
					RESERVATION_DELETE,
					RESERVATION_READMY,
					RESERVATION_CANCEL,
					RESERVATION_UPDATE,
					ORGANIZATION_READ_SUBSCRIPTIONS,
					ORGANIZATION_READ_EMPLOYEES,
					ORGANIZATION_CREATE,
					ORGANIZATION_DELETE,
					ORGANIZATION_UPDATE,
					ORGANIZATION_SUSPEND,
					USER_READ,
					USER_CREATE,
					USER_UPDATE,
					USER_DELETE,
					PERSONAL_EVENT_CREATE,
					PERSONAL_EVENT_DELETE,
					PERSONAL_EVENT_UPDATE,
					PERSONAL_EVENT_READ,
					PERSONAL_EVENT_READ_ALL,
					SUBSCRIPTION_READ,
					SUBSCRIPTION_CREATE,
					SUBSCRIPTION_DELETE,
					SUBSCRIPTION_CANCEL,
					SUBSCRIPTION_READMY,
					INVITATION_DELETE,
					INVITATION_READ,
					INVITATION_READMY,
					INVITATION_CREATE,
					MENU_READ,
					MENU_CREATE,
					MENU_DELETE,
					MENU_READMY

			));

	private final Set<Permissions> permissions;

	public List<SimpleGrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = getPermissions()
				.stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}

}