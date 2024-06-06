package bg.tu.varna.events.persistence.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.tu.varna.events.persistence.entities.Permissions.*;

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
					BUSINESS_EVENT_READ,
					ORGANIZATION_READ,
					SUBSCRIPTION_CREATE,
					SUBSCRIPTION_READ,
					SUBSCRIPTION_DELETE,
					RESERVATION_CREATE,
					RESERVATION_DELETE,
					RESERVATION_READ
			)),
	BUSINESS(
			Set.of(
					BUSINESS_USER_UPDATE,
					BUSINESS_USER_DELETE,
					BUSINESS_EVENT_CREATE,
					BUSINESS_EVENT_READ,
					BUSINESS_EVENT_DELETE,
					BUSINESS_EVENT_UPDATE,
					SUBSCRIPTION_READ,
					RESERVATION_READ,
					RESERVATION_CREATE,
					RESERVATION_DELETE,
					ORGANIZATION_READ
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
					BUSINESS_EVENT_READ,
					BUSINESS_EVENT_DELETE,
					BUSINESS_EVENT_UPDATE,
					SUBSCRIPTION_READ,
					RESERVATION_READ,
					RESERVATION_CREATE,
					RESERVATION_DELETE,
					ORGANIZATION_READ,
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
					BUSINESS_EVENT_READ,
					BUSINESS_EVENT_DELETE,
					BUSINESS_EVENT_UPDATE,
					SUBSCRIPTION_READ,
					RESERVATION_READ,
					RESERVATION_CREATE,
					RESERVATION_DELETE,
					ORGANIZATION_READ,
					ORGANIZATION_CREATE,
					ORGANIZATION_DELETE,
					ORGANIZATION_UPDATE,
					USER_READ,
					USER_CREATE,
					USER_UPDATE,
					USER_DELETE,
					PERSONAL_EVENT_CREATE,
					PERSONAL_EVENT_DELETE,
					PERSONAL_EVENT_UPDATE,
					PERSONAL_EVENT_READ,
					SUBSCRIPTION_CREATE,
					SUBSCRIPTION_DELETE

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