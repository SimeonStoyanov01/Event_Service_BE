package bg.tu.varna.events.api.exceptions;

public class OrganizationSuspendedException extends RuntimeException {

	public OrganizationSuspendedException() {
		super("Organization is suspended: action not allowed");
	}
}
