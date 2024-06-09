package bg.tu.varna.events.persistence.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permissions {

	ADMIN_READ("admin:read"),
	ADMIN_UPDATE("admin:update"),
	ADMIN_CREATE("admin:create"),
	ADMIN_DELETE("admin:delete"),

	BUSINESS_ADMIN_READ("business_admin:read"),
	BUSINESS_ADMIN_UPDATE("business_admin:update"),
	BUSINESS_ADMIN_CREATE("business_admin:create"),
	BUSINESS_ADMIN_DELETE("business_admin:delete"),


	BUSINESS_USER_READ("business_user:read"),
	BUSINESS_USER_CREATE("business_user:create"),
	BUSINESS_USER_UPDATE("business_user:update"),
	BUSINESS_USER_DELETE("business_user:delete"),

	USER_READ("user:read"),
	USER_CREATE("user:create"),
	USER_UPDATE("user:update"),
	USER_DELETE("user:delete"),

	ORGANIZATION_READ("organization:create"),
	ORGANIZATION_CREATE("organization:create"),
	ORGANIZATION_UPDATE("organization:update"),
	ORGANIZATION_DELETE("organization:delete"),

	BUSINESS_EVENT_BYUSER_READ("business_event_byuser:read"),
	BUSINESS_EVENT_CREATE("business_event:create"),
	BUSINESS_EVENT_UPDATE("business_event:update"),
	BUSINESS_EVENT_DELETE("business_event:delete"),
	BUSINESS_EVENT_SUSPEND("business_event:suspend"),


	PERSONAL_EVENT_CREATE("personal_event:create"),
	PERSONAL_EVENT_UPDATE("personal_event:update"),
	PERSONAL_EVENT_DELETE("personal_event:delete"),
	PERSONAL_EVENT_READ("personal_event:read"),

	RESERVATION_READ("reservation:read"),
	RESERVATION_CREATE("reservation:create"),
	RESERVATION_DELETE("reservation:delete"),

	SUBSCRIPTION_READ("subscription:read"),
	SUBSCRIPTION_CREATE("subscription:create"),
	SUBSCRIPTION_DELETE("subscription:delete");


	private final String permission;
}